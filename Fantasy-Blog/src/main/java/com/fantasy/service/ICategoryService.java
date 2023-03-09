package com.fantasy.service;

import com.fantasy.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.vo.PageComment;

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
public interface ICategoryService extends IService<Category> {


    Map<String, List> getCategoryBlogCountMap();
    
}
