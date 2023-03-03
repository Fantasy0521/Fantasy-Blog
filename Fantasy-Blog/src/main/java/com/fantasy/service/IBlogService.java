package com.fantasy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.vo.BlogInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IBlogService extends IService<Blog> {

    public PageResult<BlogInfo> getAllBlogsByPage(Integer pageNum);

}
