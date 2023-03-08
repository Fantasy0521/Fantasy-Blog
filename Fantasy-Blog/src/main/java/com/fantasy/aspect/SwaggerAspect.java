package com.fantasy.aspect;

import com.fantasy.annotation.OperationLogger;
import com.fantasy.entity.OperationLog;
import com.fantasy.util.AopUtils;
import com.fantasy.util.IpAddressUtils;
import com.fantasy.util.JacksonUtils;
import com.fantasy.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * AOP自动记录操作日志和访问日志
 * 本来是使用自定义注解OperationLogger和VisitLogger的,现在直接使用Swagger
 * 通过ApiOperation注解中的note来区分是访问日志还是操作日志
 * 规定note=1 为 访问日志 note=2 为操作日志 
 */
@Component
@Aspect
public class SwaggerAspect {

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(apiOperation)")
    public void logPointcut(ApiOperation apiOperation) {
    }

    /**
     * 配置环绕通知
     * 记录日志
     * @param joinPoint
     * @param apiOperation
     * @return
     * @throws Throwable
     */
    @Around("logPointcut(apiOperation)")
    public Object logAround(ProceedingJoinPoint joinPoint,ApiOperation apiOperation) throws Throwable {
        //1 计算并记录业务方法执行时间
        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //2 判断是访问日志还是操作日志
        String notes = apiOperation.notes();
        if ("1".equals(notes)){//访问日志
            //获取访问日志
           
            //存入数据库
            
        }else {// 否则都是操作日志  也就是这里认为默认就是操作日志 即使notes没有设置
            //获取操作日志
            OperationLog operationLog = handleLog(joinPoint, apiOperation, times);
            //存入数据库
            
        }
        return result;
    }

    /**
     * 获取HttpServletRequest请求对象，并设置OperationLog对象属性
     *
     * @param apiOperation
     * @param times
     * @return
     */
    private OperationLog handleLog(ProceedingJoinPoint joinPoint, ApiOperation apiOperation, int times) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        String username = JwtUtils.getTokenBody(request.getHeader("Authorization")).getSubject();
        //TODO 没有使用Token 这里直接先死了
        String username = "Admin";
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String description = apiOperation.value();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        OperationLog log = new OperationLog(username, uri, method, description, ip, times, userAgent);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000));
        return log;
    }
    
    
}
