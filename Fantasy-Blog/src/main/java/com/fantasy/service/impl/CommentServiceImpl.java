package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.fantasy.entity.BlogTag;
import com.fantasy.entity.Comment;
import com.fantasy.exception.BizException;
import com.fantasy.mapper.CommentMapper;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.PageComment;
import com.fantasy.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogServiceImpl blogService;

    //记录总评论数
    private int totalComment;

    public int getTotalComment(){
        return totalComment;
    }

    /**
     * 分页获取评论列表
     *
     * @param page
     * @param blogId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<PageComment> getCommentsList(Integer page, Long blogId, Integer pageNum, Integer pageSize) {
        //使用PageHelper 注意要从此处开始分页,已经查询出结果后再进行分页无效
        PageHelper.startPage(pageNum, pageSize);
        //1 获取所有评论列表
        List<PageComment> comments = getPageCommentListByPageAndParentCommentId(page, blogId, -1L);
        //此时已经把所有子评论都已经递归查出
        // 把所有子评论以及子评论的子评论统一放入ReplyComments 页面只做了两层显示 1父1子 即把孙子也当为子
        int totalChild = 0;
        for (PageComment c : comments) {
            List<PageComment> tmpComments = new ArrayList<>();
            getReplyComments(tmpComments, c.getReplyComments());
            Blog blog = blogService.getById(c.getBlogId());
            c.setBlog(blog);
            for (PageComment tmpComment : tmpComments) {
                blog = blogService.getById(tmpComment.getBlogId());
                tmpComment.setBlog(blog);
            }
            totalChild += tmpComments.size();
            c.setReplyComments(tmpComments);
        }
        //TODO 对评论的表情进行解析
        
        //2 进行分页处理
        // 使用PageHelper
        PageInfo<PageComment> pageInfo = new PageInfo<>(comments);
        //记录总评论数
        totalComment = comments.size() + totalChild;
        return new PageResult<>(pageInfo.getPages(), pageInfo.getList());
    }

    /**
     * 从某个节点出发,依次查询该节点下还有哪些子节点,此处使用递归
     * 当parentCommentId = -1 时为查询所有评论
     *
     * @param page
     * @param blogId
     * @param parentCommentId
     * @return
     */
    private List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId) {
        //1 以parentCommentId作为条件,查询此节点下的子节点
        List<PageComment> comments = commentMapper.getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
        //2 对子节点依次遍历查询出其子节点
        for (PageComment c : comments) {
            //3 此时把patentCommentId设为自己的Id,对其子节点在进行查询,实现递归
            List<PageComment> replyComments = getPageCommentListByPageAndParentCommentId(page, blogId, c.getId());
            //5 当开始设置子节点的时候已经遍历到叶子结点了,本轮递归结束
            c.setReplyComments(replyComments);
        }
        //4 当没有字节点时即当前节点为叶子结点,开始回归
        return comments;
    }

    /**
     * 将所有子评论递归取出到一个List中
     * 也就是把其他子评论全部并排显示,不管他们是否还有父子关系
     *
     * @param comments
     * @return
     */
    private void getReplyComments(List<PageComment> tmpComments, List<PageComment> comments) {
        for (PageComment c : comments) {
            tmpComments.add(c);
            getReplyComments(tmpComments, c.getReplyComments());
        }
    }

    /**
     * 新增评论
     * @param comment
     */
    @Override
    @Transactional // 开启事务处理
    public void saveComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        if (comment.getAvatar()==null){
            comment.setAvatar("/img/comment-avatar/2.jpg");
        }
        if (comment.getIsPublished() == null) {
            comment.setIsPublished(true);
        }
        if (comment.getIsAdminComment() == null){
            comment.setIsAdminComment(false);
        }
        if (comment.getIsNotice() == null){
            comment.setIsNotice(false);
        }
        boolean save = this.save(comment);
        if(!save){
            throw new RuntimeException(new BizException("评论失败"));
        }
    }

    /**
     * 根据id删除评论,会把其子评论也一起删除
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result deleteComment(Long id) {
        //1 查询出子评论
        List<Comment> allReplyComments = getAllReplyComments(id);
        //2 删除子评论
        for (Comment comment : allReplyComments) {
            delete(comment);
        }
        //3 删除此评论
        if (!this.removeById(id)) {
            throw new RuntimeException(new BizException("删除失败"));
        }
        return Result.ok("删除成功");
    }

    /**
     * 按id递归查询子评论
     *
     * @param parentCommentId 需要查询子评论的父评论id
     * @return
     */
    private List<Comment> getAllReplyComments(Long parentCommentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentCommentId,parentCommentId);
        List<Comment> comments = this.list(queryWrapper);
        for (Comment c : comments) {
            List<Comment> replyComments = getAllReplyComments(c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    /**
     * 递归删除子评论
     *
     * @param comment 需要删除子评论的父评论
     * @return
     */
    private void delete(Comment comment) {
        for (Comment c : comment.getReplyComments()) {
            delete(c);
        }
        if (!this.removeById(comment.getId())) {
            throw new RuntimeException(new BizException("评论删除失败"));
        }
    }

    @Override
    public int getCommentCount() {
        return this.list().size();
    }
}
