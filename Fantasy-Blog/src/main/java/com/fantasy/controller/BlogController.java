package com.fantasy.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.BlogDetail;
import com.fantasy.model.vo.BlogInfo;
import com.fantasy.service.IBlogService;
import com.fantasy.service.impl.BlogServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 按置顶、创建时间排序 分页查询博客简要信息列表
     * @param pageNum 页码
     * @return
     */
    @GetMapping("/blogs")
    public Result getAllBlogs(@RequestParam(defaultValue = "1") Integer pageNum){
        PageResult<BlogInfo> allBlogsByPage = blogService.getAllBlogsByPage(pageNum);
        return Result.ok("获取所有博客信息成功!",allBlogsByPage);
    }

    @GetMapping("/blog")
    public Result getOneBlogsById(@RequestParam Long id){
        BlogDetail blogById = blogService.getBlogById(id);
        return Result.ok("获取博客详情信息成功",blogById);
    }

    /**
     * 根据分类名称分页查询公开的博客列表
     * http://localhost:8090/category?categoryName=%E5%8A%A8%E6%BC%AB
     * @param categoryName 分类名称
     * @param pageNum 页码
     * @return
     */
    @GetMapping("category")
    public Result category(@RequestParam String categoryName,
                           @RequestParam(defaultValue = "1") Integer pageNum) {
        PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByCategoryNameAndIsPublished(categoryName, pageNum);
        return Result.ok("请求成功", pageResult);
    }

}

