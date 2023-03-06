package com.fantasy.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fantasy.entity.Friend;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.FriendInfo;
import com.fantasy.service.IFriendService;
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
//@RequestMapping("/fantasy/friend")
public class FriendController {

    @Autowired
    private IFriendService friendService;

    //http://localhost:8090/friends

    /**
     * 获取友链页面
     * @return
     */
    @GetMapping("friends")
    public Result friends(){
        FriendInfo friendInfo = friendService.getFriendInfo(true);
        List<Friend> friends = friendService.list(new LambdaQueryWrapper<Friend>().eq(Friend::getIsPublished,true));
        Map<String,Object> map = new HashMap<>();
        map.put("friendList", friends);
        map.put("friendInfo", friendInfo);
        return Result.ok("请求成功",map);
    }
}

