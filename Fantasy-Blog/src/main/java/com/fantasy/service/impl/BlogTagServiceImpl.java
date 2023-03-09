package com.fantasy.service.impl;

import com.fantasy.entity.BlogTag;
import com.fantasy.entity.Tag;
import com.fantasy.mapper.BlogTagMapper;
import com.fantasy.mapper.TagMapper;
import com.fantasy.model.vo.TagBlogCount;
import com.fantasy.service.IBlogTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {
    
    @Autowired
    private TagMapper tagMapper;
    
    @Autowired
    private TagServiceImpl tagService;
    
    @Override
    public Map<String, List> getTagBlogCountMap() {
        //查询标签id对应的博客数量
        List<TagBlogCount> tagBlogCountList = tagMapper.getTagBlogCount();
        //查询所有标签的id和名称
        List<Tag> tagList = tagService.list();
        //所有标签名称的List
        List<String> legend = new ArrayList<>();
        for (Tag tag : tagList) {
            legend.add(tag.getName());
        }
        //标签对应的博客数量List
        List<TagBlogCount> series = new ArrayList<>();
        if (tagBlogCountList.size() == tagList.size()) {
            Map<Long, String> m = new HashMap<>();
            for (Tag t : tagList) {
                m.put(t.getId(), t.getName());
            }
            for (TagBlogCount t : tagBlogCountList) {
                t.setName(m.get(t.getId()));
                series.add(t);
            }
        } else {
            Map<Long, Integer> m = new HashMap<>();
            for (TagBlogCount t : tagBlogCountList) {
                m.put(t.getId(), t.getValue());
            }
            for (Tag t : tagList) {
                TagBlogCount tagBlogCount = new TagBlogCount();
                tagBlogCount.setName(t.getName());
                Integer count = m.get(t.getId());
                if (count == null) {
                    tagBlogCount.setValue(0);
                } else {
                    tagBlogCount.setValue(count);
                }
                series.add(tagBlogCount);
            }
        }
        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }
}
