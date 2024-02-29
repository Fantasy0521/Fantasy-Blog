package com.fantasy.controller.admin;

import com.fantasy.entity.Blog;
import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import com.fantasy.entity.User;
import com.fantasy.model.Result.Result;
import com.fantasy.model.dto.BlogDto;
import com.fantasy.model.vo.LoginUser;
import com.fantasy.service.IBlogService;
import com.fantasy.service.ICategoryService;
import com.fantasy.service.ITagService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 博客文章后台管理端
 */
@RestController
@RequestMapping("/admin")
public class BlogAdminController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITagService tagService;

    @Value("${linuxImg.url}")
    private String url;

    //写文章 http://localhost:8090/admin/blog
    /**
     * 写文章 新增博客
     * @param blog
     * @return
     */
    @PostMapping("blog")
    @ApiOperation(value = "新增博客",notes = "2")
    public Result addBlog(@RequestBody BlogDto blog){
        return blogService.saveBlog(blog,"save");
    }

    /**
     * 获取博客文章列表 包括获取全部和搜索功能
     * 搜索时title , categoryID不为空
     * @param title      按标题模糊查询
     * @param categoryId 按分类id查询
     * @param pageNum    页码
     * @param pageSize   每页个数
     * @return
     */
    //查询 http://localhost:8090/admin/blogs?title=&pageNum=1&pageSize=10
    @GetMapping("blogs")
    @ApiOperation(value = "获取博客和分类信息",notes = "2")
    public Result blogs(@RequestParam(required = false) String title,
                        @RequestParam(required = false) Integer categoryId,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<BlogDto> pageInfo =  blogService.getAllorSearchBlogs(title,categoryId,pageNum,pageSize);
        List<Category> categories = categoryService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("blogs", pageInfo);
        map.put("categories", categories);
        return Result.ok("请求成功", map);
    }

    /**
     * 按id获取博客详情
     *
     * @param id 博客id
     * @return
     */
    @GetMapping("/blog")
    public Result getBlog(@RequestParam Long id) {
        BlogDto blog = blogService.getBlogDtoById(id);
        return Result.ok("获取成功", blog);
    }

    /**
     * 获取分类列表和标签列表
     *
     * @return
     */
    @GetMapping("/categoryAndTag")
    public Result categoryAndTag() {
        List<Category> categories = categoryService.list();
        List<Tag> tags = tagService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        map.put("tags", tags);
        return Result.ok("请求成功", map);
    }

    //http://localhost:8090/admin/blog
    /**
     * 更新博客
     * @param blogDto
     * @return
     */
    @PutMapping("blog")
    @ApiOperation(value = "更新博客",notes = "2")
    public Result updateBlog(@RequestBody BlogDto blogDto){
        return blogService.saveBlog(blogDto,"update");
    }


    /**
     * 删除博客文章、删除博客文章下的所有评论、同时维护 blog_tag 表
     *
     * @param id 文章id
     * @return
     */
    //http://localhost:8090/admin/blog?id=1
    @DeleteMapping("blog")
    @ApiOperation(value = "删除博客",notes = "2")
    public Result deleteBlog(@RequestParam Integer id){
        return blogService.deleteBlogById(id);
    }

    /**
     * 更新博客置顶状态
     *
     * @param id  博客id
     * @param top 是否置顶
     * @return
     */
    @PutMapping("/blog/top")
    public Result updateTop(@RequestParam Long id, @RequestParam Boolean top) {
        blogService.updateBlogTopById(id, top);
        return Result.ok("操作成功");
    }
    
    
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser user){
        if (user.getUsername().equals("Admin")) {
            if (user.getPassword().equals("admin")) {
                return Result.ok("登陆成功");
            }
        }
        return Result.error();
    }

    //文件上传 上传图片
    @PostMapping("upload")
    public Result upload(MultipartFile file) {
//        System.out.println(file);
        //获取文件原始名,使用原始名可能出现覆盖问题
        String originalFilename = file.getOriginalFilename();
        //获取后缀
//        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//
//        //这里采取随机生成一个文件名
//        //使用UUID重新生成文件名
//        String fileName = UUID.randomUUID().toString() + suffix;

        String basePath = url;

        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在,创建
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath + originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.ok("上传成功", originalFilename);

    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {

            String basePath = url;

            //输入流,通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流,通过输出流将文件同时写会浏览器,在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            //浏览器下载
//            response.setContentType("images/jepg");

            //浏览器直接预览不下载
            response.setHeader("Content-Type", "image/jpeg");
            response.setHeader("Content-Disposition", "inline; filename=" +  URLEncoder.encode(name, "UTF-8"));


            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    
}
