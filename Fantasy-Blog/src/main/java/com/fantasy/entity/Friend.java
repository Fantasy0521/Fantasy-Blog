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
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 昵称
     */
      private String nickname;

      /**
     * 描述
     */
      private String description;

      /**
     * 站点
     */
      private String website;

      /**
     * 头像
     */
      private String avatar;

      /**
     * 公开或隐藏
     */
      private Boolean isPublished;

      /**
     * 点击次数
     */
      private Integer views;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getNickname() {
        return nickname;
    }

      public void setNickname(String nickname) {
          this.nickname = nickname;
      }
    
    public String getDescription() {
        return description;
    }

      public void setDescription(String description) {
          this.description = description;
      }
    
    public String getWebsite() {
        return website;
    }

      public void setWebsite(String website) {
          this.website = website;
      }
    
    public String getAvatar() {
        return avatar;
    }

      public void setAvatar(String avatar) {
          this.avatar = avatar;
      }
    
    public Boolean getIsPublished() {
        return isPublished;
    }

      public void setIsPublished(Boolean isPublished) {
          this.isPublished = isPublished;
      }
    
    public Integer getViews() {
        return views;
    }

      public void setViews(Integer views) {
          this.views = views;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }

    @Override
    public String toString() {
        return "Friend{" +
              "id=" + id +
                  ", nickname=" + nickname +
                  ", description=" + description +
                  ", website=" + website +
                  ", avatar=" + avatar +
                  ", isPublished=" + isPublished +
                  ", views=" + views +
                  ", createTime=" + createTime +
              "}";
    }
}
