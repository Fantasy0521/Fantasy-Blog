package com.fantasy.controller.admin;

import com.fantasy.entity.Blog;
import com.fantasy.model.Result.Result;
import com.fantasy.model.dto.BlogDto;
import com.fantasy.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客文章后台管理端
 */
@RestController
@RequestMapping("/admin")
public class BlogAdminController {

    @Autowired
    private IBlogService blogService;

    //写文章 http://localhost:8090/admin/blog
    /**
     * 写文章 新增博客
     * @param blog
     * @return
     */
    @PostMapping("blog")
    public Result addBlog(@RequestBody BlogDto blog){
        return blogService.saveBlog(blog,"save");
    }

}
