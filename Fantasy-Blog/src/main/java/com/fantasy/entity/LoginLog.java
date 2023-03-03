package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 用户名称
     */
      private String username;

      /**
     * ip
     */
      private String ip;

      /**
     * ip来源
     */
      private String ipSource;

      /**
     * 操作系统
     */
      private String os;

      /**
     * 浏览器
     */
      private String browser;

      /**
     * 登录状态
     */
      private Boolean status;

      /**
     * 操作描述
     */
      private String description;

      /**
     * 登录时间
     */
      private LocalDateTime createTime;

      /**
     * user-agent用户代理
     */
      private String userAgent;

    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }
    
    public String getIp() {
        return ip;
    }

      public void setIp(String ip) {
          this.ip = ip;
      }
    
    public String getIpSource() {
        return ipSource;
    }

      public void setIpSource(String ipSource) {
          this.ipSource = ipSource;
      }
    
    public String getOs() {
        return os;
    }

      public void setOs(String os) {
          this.os = os;
      }
    
    public String getBrowser() {
        return browser;
    }

      public void setBrowser(String browser) {
          this.browser = browser;
      }
    
    public Boolean getStatus() {
        return status;
    }

      public void setStatus(Boolean status) {
          this.status = status;
      }
    
    public String getDescription() {
        return description;
    }

      public void setDescription(String description) {
          this.description = description;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public String getUserAgent() {
        return userAgent;
    }

      public void setUserAgent(String userAgent) {
          this.userAgent = userAgent;
      }

    @Override
    public String toString() {
        return "LoginLog{" +
              "id=" + id +
                  ", username=" + username +
                  ", ip=" + ip +
                  ", ipSource=" + ipSource +
                  ", os=" + os +
                  ", browser=" + browser +
                  ", status=" + status +
                  ", description=" + description +
                  ", createTime=" + createTime +
                  ", userAgent=" + userAgent +
              "}";
    }
}
