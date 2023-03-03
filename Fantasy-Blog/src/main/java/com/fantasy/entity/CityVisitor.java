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
@TableName("city_visitor")
public class CityVisitor implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 城市名称
     */
        private String city;

      /**
     * 独立访客数量
     */
      private Integer uv;

    
    public String getCity() {
        return city;
    }

      public void setCity(String city) {
          this.city = city;
      }
    
    public Integer getUv() {
        return uv;
    }

      public void setUv(Integer uv) {
          this.uv = uv;
      }

    @Override
    public String toString() {
        return "CityVisitor{" +
              "city=" + city +
                  ", uv=" + uv +
              "}";
    }
}
