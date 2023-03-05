package com.fantasy.mapper;

import com.fantasy.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantasy.model.vo.PageComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);
}
