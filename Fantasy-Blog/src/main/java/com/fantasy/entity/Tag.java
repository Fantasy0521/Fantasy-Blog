package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private String tagName;

      /**
     * 标签颜色(可选)
     */
      private String color;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getTagName() {
        return tagName;
    }

      public void setTagName(String tagName) {
          this.tagName = tagName;
      }
    
    public String getColor() {
        return color;
    }

      public void setColor(String color) {
          this.color = color;
      }

    @Override
    public String toString() {
        return "Tag{" +
              "id=" + id +
                  ", tagName=" + tagName +
                  ", color=" + color +
              "}";
    }
}
