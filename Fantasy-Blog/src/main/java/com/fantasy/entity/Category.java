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
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private String categoryName;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getCategoryName() {
        return categoryName;
    }

      public void setCategoryName(String categoryName) {
          this.categoryName = categoryName;
      }

    @Override
    public String toString() {
        return "Category{" +
              "id=" + id +
                  ", categoryName=" + categoryName +
              "}";
    }
}
