package com.fantasy.model.dto;

import com.fantasy.entity.Blog;
import com.fantasy.entity.Category;
import com.fantasy.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogDto extends Blog {

    //private User user;//文章作者(因为是个人博客，也可以不加作者字段，暂且加上)
    private Category category;//文章分类
    private List<Tag> tags = new ArrayList<>();//文章标签
    private Boolean published;//公开或私密
    private Boolean recommend;//推荐开关
    private Boolean appreciation;//赞赏开关
    private Boolean commentEnabled;//评论开关
    private Boolean top;//是否置顶

    private Object cate;//页面展示层传输的分类对象：正常情况下为 字符串 或 分类id
    private List<Object> tagList;//页面展示层传输的标签对象：正常情况下为 List<Integer>标签id 或 List<String>标签名

}
