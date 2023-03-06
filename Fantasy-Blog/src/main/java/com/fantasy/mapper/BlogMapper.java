package com.fantasy.mapper;

import com.fantasy.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantasy.model.vo.BlogInfo;
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
public interface BlogMapper extends BaseMapper<Blog> {

    List<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName);

}
