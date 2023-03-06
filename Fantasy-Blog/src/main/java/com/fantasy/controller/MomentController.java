package com.fantasy.controller;


import com.fantasy.entity.Moment;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.service.IMomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 *  动态
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@RestController
//@RequestMapping("/fantasy/moment")
public class MomentController {

    @Autowired
    private IMomentService momentService;

//    http://localhost:8090/moments?pageNum=1

    /**
     * 获取所有动态
     * @param pageNum
     * @return
     */
    @GetMapping("moments")
    public Result moments(@RequestParam(defaultValue = "1") Integer pageNum){
        PageResult<Moment> pageResult = momentService.getMomentsByPage(pageNum);
        return Result.ok(pageResult);
    }

}

