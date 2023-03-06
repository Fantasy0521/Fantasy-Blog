package com.fantasy.controller;


import com.fantasy.entity.About;
import com.fantasy.model.Result.Result;
import com.fantasy.service.IAboutService;
import com.fantasy.util.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@RestController
//@RequestMapping("/fantasy/about")
public class AboutController {

    @Autowired
    private IAboutService aboutService;

    //http://localhost:8090/about
    @GetMapping("about")
    public Result about(){
        Map<String, String> aboutInfoMap = aboutService.getAbout();
        return Result.ok("请求成功",aboutInfoMap);
    }

}

