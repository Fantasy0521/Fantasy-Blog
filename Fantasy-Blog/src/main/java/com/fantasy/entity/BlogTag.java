package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@TableName("blog_tag")
public class BlogTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogId;

    private Long tagId;

    
    public Long getBlogId() {
        return blogId;
    }

      public void setBlogId(Long blogId) {
          this.blogId = blogId;
      }
    
    public Long getTagId() {
        return tagId;
    }

      public void setTagId(Long tagId) {
          this.tagId = tagId;
      }

    @Override
    public String toString() {
        return "BlogTag{" +
              "blogId=" + blogId +
                  ", tagId=" + tagId +
              "}";
    }
}
