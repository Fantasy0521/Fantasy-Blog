package com.fantasy.aspect;

import com.fantasy.annotation.OperationLogger;
import com.fantasy.annotation.VisitLogger;
import com.fantasy.entity.ExceptionLog;
import com.fantasy.service.IExceptionLogService;
import com.fantasy.util.AopUtils;
import com.fantasy.util.IpAddressUtils;
import com.fantasy.util.JacksonUtils;
import com.fantasy.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * AOP 记录异常日志并存入数据库
 * 配合自定义注解使用
 */
@Component
@Aspect
public class ExceptionLogAspect {

    @Autowired
    IExceptionLogService exceptionLogService;

    /**
     * 配置切入点
     * 该表达式意为 把controller包下的所有方法都设为切入点
     */
    @Pointcut("execution(* com.fantasy.controller..*.*(..))")
    public void logPointcut() {

    }

    /**
     * 设置异常增强
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        //1 获取异常详细信息
        ExceptionLog log = handleLog(joinPoint, e);
        System.out.println(log);
        //2 存入数据库
        exceptionLogService.saveExceptionLog(log);
    }

    /**
     * 设置ExceptionLog对象属性 异常详细信息
     *
     * @return
     */
    private ExceptionLog handleLog(JoinPoint joinPoint, Exception e) {
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        //todo 使用swagger后，可以直接使用注解上的内容作为 ExceptionLog 的 description
        String description = getDescriptionFromSwagger(joinPoint);
        String error = StringUtils.getStackTrace(e);
        ExceptionLog log = new ExceptionLog(uri, method, description, error, ip, userAgent);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000));
        return log;
    }


    /**
     * 通过注解获取description
     * @param joinPoint
     * @return
     */
    private String getDescriptionFromAnnotations(JoinPoint joinPoint) {
        String description = "";
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
        if (operationLogger != null) {
            description = operationLogger.value();
            return description;
        }
        VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        if (visitLogger != null) {
            description = visitLogger.behavior();
            return description;
        }
        return description;
    }

    /**
     * 通过Swagger注解获取description
     * @param joinPoint
     * @return
     */
    private String getDescriptionFromSwagger(JoinPoint joinPoint) {
        String description = "";
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            description = annotation.value();
            return description;
        }
        return description;
    }


}
