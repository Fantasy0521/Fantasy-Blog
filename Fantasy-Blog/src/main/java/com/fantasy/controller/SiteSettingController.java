package com.fantasy.controller;


import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.BlogInfo;
import com.fantasy.service.IBlogService;
import com.fantasy.service.ICategoryService;
import com.fantasy.service.ISiteSettingService;
import com.fantasy.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@RestController
//@RequestMapping("/fantasy/siteSetting")
public class SiteSettingController {
    @Autowired
    ISiteSettingService siteSettingService;
    @Autowired
    IBlogService blogService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    ITagService tagService;

    //http://localhost:8090/site

    /**
     * 获取站点配置信息、最新推荐博客、分类列表、标签云、随机博客
     *
     * @return
     */
    @GetMapping("site")
    public Result site() {
        // 1 获取站点信息
        Map<String, Object> map = siteSettingService.getSiteInfo();
        //2 获取最新博客作为推荐
        PageResult<BlogInfo> blogsByPage = blogService.getAllBlogsByPage(1);
        List<BlogInfo> newBlogList = blogsByPage.getList();
        //3 获取分类列表以及标签云
        List<Category> categoryList = categoryService.list();
        List<Tag> tagList = tagService.list();
        //4 TODO 获取随机博客,这里就先使用最新博客了
        map.put("newBlogList", newBlogList);
        map.put("categoryList", categoryList);
        map.put("tagList", tagList);
        map.put("randomBlogList", newBlogList);
        return Result.ok("请求成功",map);
    }

}

