package com.fantasy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasy.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.Result.Result;
import com.fantasy.model.dto.BlogDto;
import com.fantasy.model.vo.BlogDetail;
import com.fantasy.model.vo.BlogInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IBlogService extends IService<Blog> {

    PageResult<BlogInfo> getAllBlogsByPage(Integer pageNum);

    BlogDetail getBlogById(Long id);

    PageResult<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum);

    Map<String, Object> getArchiveBlogAndCountByIsPublished();

    List<Blog> getSearchBlogs(String query);

    Result saveBlog(BlogDto blog, String type);

    PageInfo<BlogDto> getAllorSearchBlogs(String title, Integer categoryId, Integer pageNum, Integer pageSize);

    BlogDto getBlogDtoById(Long id);

    Result deleteBlogById(Integer id);

    void updateBlogTopById(Long id, Boolean top);

    int getBlogCount();
}
