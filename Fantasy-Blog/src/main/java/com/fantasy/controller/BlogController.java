package com.fantasy.controller;


import com.fantasy.entity.Blog;
import com.fantasy.model.Result.Result;
import com.fantasy.service.IBlogService;
import com.fantasy.service.impl.BlogServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@RestController
//@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;

    @GetMapping("/blogs")
    public Result getAllBlogs(){
        List<Blog> list = blogService.list();
        return Result.ok("获取所有博客信息成功!",list);
    }

}

