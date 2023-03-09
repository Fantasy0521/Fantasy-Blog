package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import com.fantasy.mapper.BlogMapper;
import com.fantasy.mapper.CategoryMapper;
import com.fantasy.model.Result.PageResult;
import com.fantasy.model.vo.CategoryBlogCount;
import com.fantasy.model.vo.PageComment;
import com.fantasy.model.vo.TagBlogCount;
import com.fantasy.service.IBlogService;
import com.fantasy.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private BlogMapper blogMapper;
    
    @Override
    public Map<String, List> getCategoryBlogCountMap() {
        //查询分类id对应的博客数量
        List<CategoryBlogCount> categoryBlogCountList = blogMapper.getCategoryBlogCountList();
        //查询所有分类的id和名称
        List<Category> categoryList = this.list();
        //所有分类名称的List
        List<String> legend = new ArrayList<>();
        for (Category category : categoryList) {
            legend.add(category.getName());
        }
        //分类对应的博客数量List
        List<CategoryBlogCount> series = new ArrayList<>();
        if (categoryBlogCountList.size() == categoryList.size()) {
            Map<Long, String> m = new HashMap<>();
            for (Category c : categoryList) {
                m.put(c.getId(), c.getName());
            }
            for (CategoryBlogCount c : categoryBlogCountList) {
                c.setName(m.get(c.getId()));
                series.add(c);
            }
        } else {
            Map<Long, Integer> m = new HashMap<>();
            for (CategoryBlogCount c : categoryBlogCountList) {
                m.put(c.getId(), c.getValue());
            }
            for (Category c : categoryList) {
                CategoryBlogCount categoryBlogCount = new CategoryBlogCount();
                categoryBlogCount.setName(c.getName());
                Integer count = m.get(c.getId());
                if (count == null) {
                    categoryBlogCount.setValue(0);
                } else {
                    categoryBlogCount.setValue(count);
                }
                series.add(categoryBlogCount);
            }
        }
        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }
}
