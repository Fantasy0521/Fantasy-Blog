package com.fantasy.controller.admin;

import com.fantasy.entity.Blog;
import com.fantasy.entity.Comment;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.PageComment;
import com.fantasy.service.IBlogService;
import com.fantasy.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论 后台管理
 */
@RestController
@RequestMapping("/admin")
public class CommentAdminController {

    @Resource
    private ICommentService commentService;

    @Autowired
    private IBlogService blogService;

    //1 查询评论 http://localhost:8090/admin/comments?pageNum=1&pageSize=10
    /**
     * 按页面和博客id分页查询评论List
     *
     * @param page     要查询的页面(博客文章or关于我...)
     * @param blogId   如果是博客文章页面 需要提供博客id
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("/comments")
    public Result comments(@RequestParam(required = false) Integer page,
                           @RequestParam(required = false) Long blogId,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<PageComment> commentsList = commentService.getCommentsList(page, blogId, pageNum, pageSize);
        PageInfo<PageComment> pageInfo = new PageInfo<>();
        pageInfo.setTotal(commentsList.getTotalPage());
        pageInfo.setList(commentsList.getList());
        return Result.ok("请求成功", pageInfo);
    }
    /**
     * 获取所有博客id和title 供评论分类的选择
     *
     * @return
     */
    @GetMapping("/blogIdAndTitle")
    public Result blogIdAndTitle() {
        List<Blog> blogs = blogService.list();
        return Result.ok("请求成功", blogs);
    }

    //4 删除评论 重点做一下http://localhost:8090/admin/comment?id=13
    @DeleteMapping("comment")
    public Result deleteComment(@RequestParam Long id){
        return commentService.deleteComment(id);
    }

}
