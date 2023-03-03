package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public class Moment implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 动态内容
     */
      private String content;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

      /**
     * 点赞数量
     */
      private Integer likes;

      /**
     * 是否公开
     */
      private Boolean isPublished;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getContent() {
        return content;
    }

      public void setContent(String content) {
          this.content = content;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public Integer getLikes() {
        return likes;
    }

      public void setLikes(Integer likes) {
          this.likes = likes;
      }
    
    public Boolean getIsPublished() {
        return isPublished;
    }

      public void setIsPublished(Boolean isPublished) {
          this.isPublished = isPublished;
      }

    @Override
    public String toString() {
        return "Moment{" +
              "id=" + id +
                  ", content=" + content +
                  ", createTime=" + createTime +
                  ", likes=" + likes +
                  ", isPublished=" + isPublished +
              "}";
    }
}
