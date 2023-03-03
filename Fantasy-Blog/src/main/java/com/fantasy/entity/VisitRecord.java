package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("visit_record")
public class VisitRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 访问量
     */
      private Integer pv;

      /**
     * 独立用户
     */
      private Integer uv;

      /**
     * 日期"02-23"
     */
      private String date;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public Integer getPv() {
        return pv;
    }

      public void setPv(Integer pv) {
          this.pv = pv;
      }
    
    public Integer getUv() {
        return uv;
    }

      public void setUv(Integer uv) {
          this.uv = uv;
      }
    
    public String getDate() {
        return date;
    }

      public void setDate(String date) {
          this.date = date;
      }

    @Override
    public String toString() {
        return "VisitRecord{" +
              "id=" + id +
                  ", pv=" + pv +
                  ", uv=" + uv +
                  ", date=" + date +
              "}";
    }
}
