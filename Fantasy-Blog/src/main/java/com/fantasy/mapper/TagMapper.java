package com.fantasy.mapper;

import com.fantasy.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantasy.model.vo.TagBlogCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 博客标签持久层接口
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Mapper
//@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据博客Id查询其所有标签
     * @param blogId  博客Id
     * @return
     */
    List<Tag> getTagListById(Long blogId);

    List<TagBlogCount> getTagBlogCount();
}
