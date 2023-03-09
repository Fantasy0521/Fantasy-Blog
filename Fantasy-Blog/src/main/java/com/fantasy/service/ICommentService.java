package com.fantasy.service;

import com.fantasy.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.PageComment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface ICommentService extends IService<Comment> {
    PageResult<PageComment> getCommentsList(Integer page, Long blogId, Integer pageNum, Integer pageSize);

    void saveComment(Comment comment);

    Result deleteComment(Long id);

    int getCommentCount();
}
