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
@TableName("schedule_job")
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 任务id
     */
        @TableId(value = "job_id", type = IdType.AUTO)
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
     * cron表达式
     */
      private String cron;

      /**
     * 任务状态
     */
      private Integer status;

      /**
     * 备注
     */
      private String remark;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

    
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
    
    public String getCron() {
        return cron;
    }

      public void setCron(String cron) {
          this.cron = cron;
      }
    
    public Integer getStatus() {
        return status;
    }

      public void setStatus(Integer status) {
          this.status = status;
      }
    
    public String getRemark() {
        return remark;
    }

      public void setRemark(String remark) {
          this.remark = remark;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }

    @Override
    public String toString() {
        return "ScheduleJob{" +
              "jobId=" + jobId +
                  ", beanName=" + beanName +
                  ", methodName=" + methodName +
                  ", params=" + params +
                  ", cron=" + cron +
                  ", status=" + status +
                  ", remark=" + remark +
                  ", createTime=" + createTime +
              "}";
    }
}
