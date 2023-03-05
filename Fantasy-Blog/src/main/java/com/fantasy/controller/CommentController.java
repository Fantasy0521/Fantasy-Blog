package com.fantasy.controller;


import com.fantasy.entity.Comment;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.PageComment;
import com.fantasy.service.ICategoryService;
import com.fantasy.service.ICommentService;
import com.fantasy.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
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
//@RequestMapping("/fantasy/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    // http://localhost:8090/comments?page=0&blogId=3&pageNum=1&pageSize=5
    /**
     * 根据页面分页查询评论列表
     * @param page 页面分类（0普通文章，1关于我...）
     * @param blogId 如果page==0，需要博客id参数 , 关于我页面不需要blogId
     * @param pageNum 页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("comments")
    public Result getComments(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "") Long blogId,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize){

        //1 查询所有评论
        //此处传入-1 代表为从根节点开始查询,即查询所有评论
        PageResult<PageComment> pageInfo =  commentService.getCommentsList(page,blogId,pageNum,pageSize);

        //2 查询该页面所有评论的数量
        Integer allComment = commentService.getTotalComment();

        //3 查询该页面公开评论的数量 TODO 这里先默认就是所有评论
        Integer openComment = allComment;

        //4 封装结果集
        Map<String, Object> map = new HashMap<>();
        map.put("allComment", allComment);
        map.put("closeComment", allComment - openComment);
        map.put("comments", pageInfo);
        return Result.ok("获取成功", map);
    }

    // http://localhost:8090/comment   POST
    /**
     * 提交新增评论
     * @param comment
     * @return
     */
    @PostMapping("/comment")
    public Result postComment(@RequestBody Comment comment){
        commentService.saveComment(comment);
        return Result.ok("评论成功");
    }

}

