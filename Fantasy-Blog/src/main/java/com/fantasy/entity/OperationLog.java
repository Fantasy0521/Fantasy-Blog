package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@TableName("operation_log")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 操作者用户名
     */
      private String username;

      /**
     * 请求接口
     */
      private String uri;

      /**
     * 请求方式
     */
      private String method;

      /**
     * 请求参数
     */
      private String param;

      /**
     * 操作描述
     */
      private String description;

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
     * 请求耗时（毫秒）
     */
      private Integer times;

      /**
     * 操作时间
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
    
    public String getUri() {
        return uri;
    }

      public void setUri(String uri) {
          this.uri = uri;
      }
    
    public String getMethod() {
        return method;
    }

      public void setMethod(String method) {
          this.method = method;
      }
    
    public String getParam() {
        return param;
    }

      public void setParam(String param) {
          this.param = param;
      }
    
    public String getDescription() {
        return description;
    }

      public void setDescription(String description) {
          this.description = description;
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
    
    public Integer getTimes() {
        return times;
    }

      public void setTimes(Integer times) {
          this.times = times;
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
        return "OperationLog{" +
              "id=" + id +
                  ", username=" + username +
                  ", uri=" + uri +
                  ", method=" + method +
                  ", param=" + param +
                  ", description=" + description +
                  ", ip=" + ip +
                  ", ipSource=" + ipSource +
                  ", os=" + os +
                  ", browser=" + browser +
                  ", times=" + times +
                  ", createTime=" + createTime +
                  ", userAgent=" + userAgent +
              "}";
    }

    public OperationLog(String username, String uri, String method, String description, String ip, Integer times, String userAgent) {
        this.username = username;
        this.uri = uri;
        this.method = method;
        this.description = description;
        this.ip = ip;
        this.times = times;
        this.createTime = LocalDateTime.now();
        this.userAgent = userAgent;
    }
}
