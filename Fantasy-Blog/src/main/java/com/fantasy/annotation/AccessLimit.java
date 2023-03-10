package com.fantasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 访问控制
 * 限制请求访问的间隔时间
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 限制周期(秒)
     *
     * @return
     */
    int seconds();

    /**
     * 规定周期内限制次数
     * @return
     */
    int maxCount();

    /**
     * 触发限制时的消息提示
     * @return
     */
    String msg() default "操作频率过高";
    
}
