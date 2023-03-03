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
@TableName("schedule_job_log")
public class ScheduleJobLog implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 任务日志id
     */
        @TableId(value = "log_id", type = IdType.AUTO)
      private Long logId;

      /**
     * 任务id
     */
      private Long jobId;

      /**
     * spring bean名称
     */
      private String beanName;

      /**
     * 方法名
     */
      private String methodName;

      /**
     * 参数
     */
      private String params;

      /**
     * 任务执行结果
     */
      private Integer status;

      /**
     * 异常信息
     */
      private String error;

      /**
     * 耗时（单位：毫秒）
     */
      private Integer times;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

    
    public Long getLogId() {
        return logId;
    }

      public void setLogId(Long logId) {
          this.logId = logId;
      }
    
    public Long getJobId() {
        return jobId;
    }

      public void setJobId(Long jobId) {
          this.jobId = jobId;
      }
    
    public String getBeanName() {
        return beanName;
    }

      public void setBeanName(String beanName) {
          this.beanName = beanName;
      }
    
    public String getMethodName() {
        return methodName;
    }

      public void setMethodName(String methodName) {
          this.methodName = methodName;
      }
    
    public String getParams() {
        return params;
    }

      public void setParams(String params) {
          this.params = params;
      }
    
    public Integer getStatus() {
        return status;
    }

      public void setStatus(Integer status) {
          this.status = status;
      }
    
    public String getError() {
        return error;
    }

      public void setError(String error) {
          this.error = error;
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

    @Override
    public String toString() {
        return "ScheduleJobLog{" +
              "logId=" + logId +
                  ", jobId=" + jobId +
                  ", beanName=" + beanName +
                  ", methodName=" + methodName +
                  ", params=" + params +
                  ", status=" + status +
                  ", error=" + error +
                  ", times=" + times +
                  ", createTime=" + createTime +
              "}";
    }
}
