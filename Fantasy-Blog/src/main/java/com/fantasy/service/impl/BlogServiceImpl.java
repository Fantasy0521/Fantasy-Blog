package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import com.fantasy.mapper.BlogMapper;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.vo.BlogInfo;
import com.fantasy.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    //统一设定一页的博客数量为5
    private int pageSize = 5;

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
        queryWrapper.orderByDesc(Blog::getIsTop,Blog::getCreateTime);

        //4 开始分页查询
        Page<Blog> blogPage = this.page(pageInfo, queryWrapper);

        //5 转为BlogInfo 返回页面数据
        Page<BlogInfo> blogInfoPage = new Page<>();
        //对BlogInfo进行处理
        extracted(blogPage, blogInfoPage);

        //6 封装为前端规定的PageResult
        // 此处getPages需要加一
        PageResult<BlogInfo> result = new PageResult<BlogInfo>((int)blogInfoPage.getPages()+1,blogInfoPage.getRecords());

        return result;
    }

    /**
     * 对BlogInfo进行处理
     * @param blogPage
     * @param blogInfoPage
     */
    private void extracted(Page<Blog> blogPage, Page<BlogInfo> blogInfoPage) {
        //对象拷贝
        BeanUtils.copyProperties(blogPage, blogInfoPage,"records");
        //对records进行处理
        List<Blog> records = blogPage.getRecords();
        List<BlogInfo> list = records.stream().map((item) -> {
            BlogInfo blogInfo = new BlogInfo();
            //对象拷贝
            BeanUtils.copyProperties(item,blogInfo);
            //进行扩展
            //文章是否为私有
            if (item.getIsPublished() != null) {
                blogInfo.setPrivacy(!item.getIsPublished());
            }
            //top是否置顶
            blogInfo.setTop(item.getIsTop());
            //文章创建时间 LocalDateTime转 Date
            LocalDateTime createTime = item.getCreateTime();
            Date date = Date.from(createTime.atZone( ZoneId.systemDefault()).toInstant());
            blogInfo.setCreateTime(date);
            //查询文章分类
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getId,item.getCategoryId());
            Category category = categoryService.getOne(queryWrapper);
            blogInfo.setCategory(category);
            //查询所属的标签
            //TODO 此处需要用到中间表Blog_Tag 多对多
            LambdaQueryWrapper<Tag> queryWrapper1 = new LambdaQueryWrapper<>();
//            queryWrapper.eq(Tag::getId,item.get)
            List<Tag> tags = new ArrayList<>();
            Tag tag = tagService.getById(1);
            tags.add(tag);
            blogInfo.setTags(tags);
            //查询
            return blogInfo;
        }).collect(Collectors.toList());
        blogInfoPage.setRecords(list);
    }


}
