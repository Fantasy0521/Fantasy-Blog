package com.fantasy.service;

import com.fantasy.entity.BlogTag;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IBlogTagService extends IService<BlogTag> {

    Map<String, List> getTagBlogCountMap();
    
}
