package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.fantasy.entity.BlogTag;
import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import com.fantasy.exception.BizException;
import com.fantasy.mapper.BlogMapper;
import com.fantasy.mapper.TagMapper;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.dto.BlogDto;
import com.fantasy.model.vo.ArchiveBlog;
import com.fantasy.model.vo.BlogDetail;
import com.fantasy.model.vo.BlogInfo;
import com.fantasy.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.StringUtils;
import com.fantasy.util.markdown.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    private CategoryServiceImpl categoryService;

    @Resource
    private TagServiceImpl tagService;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CommentServiceImpl commentService;

    @Resource
    private BlogTagServiceImpl blogTagService;

    //博客简介列表排序方式
    private static final String orderBy = "is_top desc, create_time desc";

    //统一设定一页的博客数量为5
    private int pageSize = 5;

    @Value("${download.url}")
    private String url;

    /**
     * 根据页码进行分页查询,需要按照置顶、创建时间排序 分页查询博客简要信息列表
     *
     * @param pageNum
     * @return
     */
    @Override
    public PageResult<BlogInfo> getAllBlogsByPage(Integer pageNum) {
        //1 构建分页对象
        Page<Blog> pageInfo = new Page<>(pageNum, pageSize);

        //2 创建LambdaQueryWrapper
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();

        //3 添加排序条件 is_top desc, create_time desc
        queryWrapper.orderByDesc(Blog::getIsTop, Blog::getCreateTime);

        //4 开始分页查询
        Page<Blog> blogPage = this.page(pageInfo, queryWrapper);

        //5 转为BlogInfo 返回页面数据
        Page<BlogInfo> blogInfoPage = new Page<>();
        //对BlogInfo进行处理
        extracted(blogPage, blogInfoPage);

        //6 封装为前端规定的PageResult
        // 此处getPages需要加一
        PageResult<BlogInfo> result = new PageResult<BlogInfo>((int) blogInfoPage.getPages() + 1, blogInfoPage.getRecords());

        return result;
    }

    /**
     * 对BlogInfo进行处理
     *
     * @param blogPage
     * @param blogInfoPage
     */
    private void extracted(Page<Blog> blogPage, Page<BlogInfo> blogInfoPage) {
        //1 对象拷贝
        BeanUtils.copyProperties(blogPage, blogInfoPage, "records");
        //对records进行处理
        List<Blog> records = blogPage.getRecords();
        List<BlogInfo> list = records.stream().map((item) -> {
            BlogInfo blogInfo = new BlogInfo();
            //对象拷贝
            BeanUtils.copyProperties(item, blogInfo);

            //2 对原本字段进行扩展
            //文章是否为私有
            if (item.getIsPublished() != null) {
                blogInfo.setPrivacy(!item.getIsPublished());
            }
            //top是否置顶
            blogInfo.setTop(item.getIsTop());
            //文章创建时间 LocalDateTime转 Date
            LocalDateTime createTime = item.getCreateTime();
            Date date = Date.from(createTime.atZone(ZoneId.systemDefault()).toInstant());
            blogInfo.setCreateTime(date);
            //使用MarkDown工具类处理md语法
            blogInfo.setDescription(MarkdownUtils.markdownToHtmlExtensions(blogInfo.getDescription()));

            //3 查询其他关联表数据
            //查询文章分类
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getId, item.getCategoryId());
            Category category = categoryService.getOne(queryWrapper);
            blogInfo.setCategory(category);
            //查询所属的标签
            // 此处需要用到中间表Blog_Tag 多对多
            List<Tag> tagListById = tagMapper.getTagListById(blogInfo.getId());
            blogInfo.setTags(tagListById);
            //查询
            return blogInfo;
        }).collect(Collectors.toList());
        blogInfoPage.setRecords(list);
    }


    /**
     * 根据id查询博客信息
     *
     * @param id
     * @return
     */
    @Override
    public BlogDetail getBlogById(Long id) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getId, id);
        Blog one = this.getOne(queryWrapper);
        BlogDetail blogDetail;
        if (one == null) {
            try {
                throw new BizException("查询不到该博客");
            } catch (BizException e) {
                throw new RuntimeException(e);
            }
        } else {
            //需要转为前端认识的BlogDetail
            blogDetail = blogToBlogDetail(one);
        }
        return blogDetail;
    }

    /**
     * 对Blog进行处理,将其转化为前端认识的BlogDetail
     *
     * @param blog
     * @return
     */
    private BlogDetail blogToBlogDetail(Blog blog) {
        //对博客正文进行MarkDown语法解析
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));

        BlogDetail blogDetail = new BlogDetail();
        //对象拷贝
        BeanUtils.copyProperties(blog, blogDetail);

        blogDetail.setAppreciation(blog.getIsAppreciation());
        blogDetail.setCommentEnabled(blog.getIsCommentEnabled());
        blogDetail.setTop(blog.getIsTop());

        //根据博客id查询所属分类
        Category category = categoryService.getOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getId, blog.getCategoryId()));
        blogDetail.setCategory(category);
        //根据博客id查询所属标签
        List<Tag> tagListById = tagMapper.getTagListById(blog.getId());
        blogDetail.setTags(tagListById);

        return blogDetail;
    }

    /**
     * 根据分类名称查询博客列表
     *
     * @param categoryName
     * @param pageNum
     * @return
     */
    @Override
    public PageResult<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BlogInfo> blogInfos = blogMapper.getBlogInfoListByCategoryNameAndIsPublished(categoryName);
        //需要对blog进行MarkDown处理
        for (BlogInfo blogInfo : blogInfos) {
            blogInfo.setDescription(MarkdownUtils.markdownToHtml(blogInfo.getDescription()));
        }
        PageInfo<BlogInfo> pageInfo = new PageInfo<>(blogInfos);
        PageResult<BlogInfo> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
        return pageResult;
    }


    /**
     * 按年月分组归档公开博客 统计公开博客总数
     *
     * @return
     */
    @Override
    public Map<String, Object> getArchiveBlogAndCountByIsPublished() {
        //1 获取所有博客列表
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Blog::getCreateTime);
        List<Blog> list = this.list(queryWrapper);
        //2 对所有博客进行处理, 转化为ArchiveBlog并统计
        Map<String, List<ArchiveBlog>> archiveBlogMap = new LinkedHashMap<>();
        for (Blog blog : list) {
            LocalDateTime createTime = blog.getCreateTime();
            int year = createTime.getYear();
            int month = createTime.getMonthValue();
            int day = createTime.getDayOfMonth();
            String yearAndMonth = year + "年" + month + "月";
            ArchiveBlog archiveBlog = new ArchiveBlog();
            BeanUtils.copyProperties(blog, archiveBlog);
            archiveBlog.setDay(day + "日");
            //存入archiveBlogMap
            List<ArchiveBlog> archiveBlogs = archiveBlogMap.get(yearAndMonth);
            if (archiveBlogs == null) {// 为空则说明map中没有这个年月的键,因此进行创建list
                archiveBlogs = new ArrayList<>();
            }
            //把archiveBlog加入map中,没有对应key则put为新增key value,有则为修改
            archiveBlogs.add(archiveBlog);
            archiveBlogMap.put(yearAndMonth, archiveBlogs);
        }
        //3 封装结果集
        Map<String, Object> map = new HashMap<>();
        map.put("blogMap", archiveBlogMap);
        map.put("count", list.size());
        return map;
    }

    /**
     * 根据关键字查询Blog
     *
     * @param query
     * @return
     */
    @Override
    public List<Blog> getSearchBlogs(String query) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Blog::getId, Blog::getTitle, Blog::getContent);
        queryWrapper.like(Blog::getTitle, query).or().like(Blog::getDescription, query).or().like(Blog::getContent, query);
        List<Blog> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 新增或修改博客,注意需要对Blog_Tag表进行维护
     *
     * @param blog
     * @param type save: 新增  其他为修改
     * @return
     */
    @Override
    @Transactional
    public Result saveBlog(BlogDto blog, String type) {
        //1 验证字段
        //验证普通字段
        if (StringUtils.isEmpty(blog.getTitle(), blog.getFirstPicture(), blog.getContent(), blog.getDescription())
                || blog.getWords() == null || blog.getWords() < 0) {
            return Result.error("参数有误");
        }

        //2 处理分类
        Object cate = blog.getCate();
        if (cate == null) {
            return Result.error("分类不能为空");
        }
        if (cate instanceof Integer) {//选择了已存在的分类
            Category c = categoryService.getById(((Integer) cate).longValue());
            blog.setCategoryId(c.getId());
            blog.setCategory(c);
        } else if (cate instanceof String) {//添加新分类
            //查询分类是否已存在]
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getName, cate);
            Category category = categoryService.getOne(queryWrapper);
            if (category != null) {
                return Result.error("不可添加已存在的分类");
            }
            Category c = new Category();
            c.setName((String) cate);
            categoryService.save(c);
            blog.setCategoryId(c.getId());
            blog.setCategory(c);
        } else {
            return Result.error("分类不正确");
        }

        //3 处理标签
        List<Object> tagList = blog.getTagList();
        List<Tag> tags = new ArrayList<>();
        for (Object t : tagList) {
            if (t instanceof Integer) {//选择了已存在的标签
                Tag tag = tagService.getById(((Integer) t).longValue());
                tags.add(tag);
            } else if (t instanceof String) {//添加新标签
                //查询标签是否已存在
                LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Tag::getName, t);
                Tag tag1 = tagService.getOne(queryWrapper);
                if (tag1 != null) {
                    return Result.error("不可添加已存在的标签");
                }
                Tag tag = new Tag();
                tag.setName((String) t);
                tagService.save(tag);
                tags.add(tag);
            } else {
                return Result.error("标签不正确");
            }
        }

        if (blog.getReadTime() == null || blog.getReadTime() < 0) {
            blog.setReadTime((int) Math.round(blog.getWords() / 200.0));//粗略计算阅读时长
        }
        if (blog.getViews() == null || blog.getViews() < 0) {
            blog.setViews(0);
        }
        //4 判断是新增还是修改 并且对Blog_Tag表进行维护
        blog.setUpdateTime(LocalDateTime.now());
        blog.setUserId(1L);//个人博客默认只有一个作者
        blogDtoToBlog(blog);
        blog.setTags(tags);
        // 图片上传处理
        String content = blog.getContent();
        blog.setContent(imageUploadHandler(content));
        if ("save".equals(type)) {
            blog.setCreateTime(LocalDateTime.now());
            this.save(blog);
            //关联博客和标签(维护 blog_tag 表)
            for (Tag t : tags) {
                blogTagService.save(new BlogTag(blog.getId(), t.getId()));
            }
            return Result.ok("添加成功");
        } else {
            this.updateById(blog);
            //关联博客和标签(维护 blog_tag 表)
            LambdaUpdateWrapper<BlogTag> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BlogTag::getBlogId, blog.getId());
            blogTagService.remove(updateWrapper);
            for (Tag t : tags) {
                blogTagService.save(new BlogTag(blog.getId(), t.getId()));
            }
            return Result.ok("更新成功");
        }
    }

    /**
     * 解析context中的![ 表示为图片，进行图片上传 ，保存文件链接替换context
     * ![debug.png](1)
     * 111
     * ![下载debug.jfif](1)
     *
     */
    public String imageUploadHandler(String context){
        String patternString = "!\\[(.*?)\\]\\(.*?\\)";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(context);

        StringBuffer modifiedString = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            // 执行你的修改逻辑，并将修改后的数据放入modifiedMatch变量中
            //替换为下载链接
            String modifiedMatch = "![" + match + "]" + "(" + url + match + ")";
            matcher.appendReplacement(modifiedString, modifiedMatch);
        }
        matcher.appendTail(modifiedString);

        System.out.println(modifiedString.toString());

        return modifiedString.toString();
    }

    public void findContextImage(){
        String string = "![debug.png](1)\n\n111\n![下载debug.jfif](1)";
        String patternString = "!\\[(.*?)\\]\\(.*?\\)";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String match = matcher.group(1);
            System.out.println(match);
        }
    }




    private void blogDtoToBlog(BlogDto blog) {
        blog.setIsPublished(blog.getPublished());
        blog.setIsRecommend(blog.getRecommend());
        blog.setIsAppreciation(blog.getAppreciation());
        blog.setIsCommentEnabled(blog.getCommentEnabled());
        blog.setIsTop(blog.getTop());
    }

    private BlogDto blogToBlogDto(Blog blog) {
        BlogDto blogDto = new BlogDto();
        BeanUtils.copyProperties(blog, blogDto);
        blogDto.setPublished(blogDto.getIsPublished());
        blogDto.setRecommend(blogDto.getIsRecommend());
        blogDto.setAppreciation(blogDto.getIsAppreciation());
        blogDto.setCommentEnabled(blogDto.getIsCommentEnabled());
        blogDto.setTop(blog.getIsTop());
        blogDto.setCategory(categoryService.getById(blogDto.getCategoryId()));
        return blogDto;
    }


    /**
     * 分页查找所有博客或者搜索博客
     *
     * @param title
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<BlogDto> getAllorSearchBlogs(String title, Integer categoryId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like(Blog::getTitle, title);
        }
        if (categoryId != null) {
            queryWrapper.eq(Blog::getCategoryId, categoryId);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = this.list(queryWrapper);
        List<BlogDto> blogDtos = new ArrayList<>();
        //需要返回Dto对象
        for (Blog blog : list) {
            BlogDto blogDto = blogToBlogDto(blog);
            blogDtos.add(blogDto);
        }
        return new PageInfo<>(blogDtos);
    }

    @Override
    public BlogDto getBlogDtoById(Long id) {
        return blogMapper.getBlogById(id);
    }

    /**
     * 根据id删除博客,同时删除该博客下的所有评论,并且需要维护Blog_Tag表
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result deleteBlogById(Integer id) {
        //1 根据id删除博客
        this.removeById(id);
        //2 删除该博客下的所有评论
        commentService.removeById(id);
        //3 维护Blog_tag表
        LambdaUpdateWrapper<BlogTag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BlogTag::getBlogId, id);
        blogTagService.remove(updateWrapper);
        return Result.ok("删除成功");
    }

    /**
     * 只修改is_top字段
     *
     * @param id
     * @param top
     */
    @Override
    public void updateBlogTopById(Long id, Boolean top) {
        LambdaUpdateWrapper<Blog> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Blog::getIsTop, top);
        updateWrapper.eq(Blog::getId, id);
        this.update(updateWrapper);
    }

    @Override
    public int getBlogCount() {
        return this.list().size();
    }
}
