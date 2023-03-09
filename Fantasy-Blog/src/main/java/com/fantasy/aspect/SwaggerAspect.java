package com.fantasy.aspect;

import com.fantasy.annotation.OperationLogger;
import com.fantasy.annotation.VisitLogger;
import com.fantasy.entity.OperationLog;
import com.fantasy.entity.VisitLog;
import com.fantasy.entity.Visitor;
import com.fantasy.model.Result.Result;
import com.fantasy.model.vo.BlogDetail;
import com.fantasy.service.IOperationLogService;
import com.fantasy.service.IVisitLogService;
import com.fantasy.service.IVisitorService;
import com.fantasy.util.AopUtils;
import com.fantasy.util.IpAddressUtils;
import com.fantasy.util.JacksonUtils;
import com.fantasy.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AOP自动记录操作日志和访问日志
 * 本来是使用自定义注解OperationLogger和VisitLogger的,现在直接使用Swagger
 * 通过ApiOperation注解中的note来区分是访问日志还是操作日志
 * 规定note=1 为 访问日志 note=2 为操作日志
 */
@Component
@Aspect
public class SwaggerAspect {

    @Autowired
    IOperationLogService operationLogService;
    @Autowired
    IVisitLogService visitLogService;
    @Autowired
    IVisitorService visitorService;

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
     *
     * @param joinPoint
     * @param apiOperation
     * @return
     * @throws Throwable
     */
    @Around("logPointcut(apiOperation)")
    public Object logAround(ProceedingJoinPoint joinPoint, ApiOperation apiOperation) throws Throwable {
        //1 计算并记录业务方法执行时间
        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //2 判断是访问日志还是操作日志
        String notes = apiOperation.notes();
        if ("1".equals(notes)) {//访问日志
            //获取请求对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //校验访客标识码
            String identification = checkIdentification(request);
            //获取访问日志
            VisitLog visitLog = handleLog(joinPoint, apiOperation, request, result, times, identification);
            //存入数据库
            visitLogService.saveVisitLog(visitLog);
        } else {// 否则都是操作日志  也就是这里认为默认就是操作日志 即使notes没有设置
            //获取操作日志
            OperationLog operationLog = handleLog(joinPoint, apiOperation, times);
            //存入数据库
            operationLogService.saveOperationLog(operationLog);
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
        //TODO 没有使用Token 这里直接先写死了
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


    /**
     * 校验访客标识码
     *
     * @param request
     * @return
     */
    private String checkIdentification(HttpServletRequest request) {
        String identification = request.getHeader("identification");
        if (identification == null) {
            //请求头没有uuid，签发uuid并保存到数据库和Redis
            identification = saveUUID(request);
        } else {
            //TODO 校验Redis中是否存在uuid
            //Redis中不存在uuid

            //校验数据库中是否存在uuid
            boolean mysqlHas = visitorService.hasUUID(identification);
            if (mysqlHas) {
                //数据库存在，保存至Redis

            } else {
                //数据库不存在，签发新的uuid
                identification = saveUUID(request);
            }

        }
        return identification;
    }

    /**
     * 签发UUID，并保存至数据库和Redis
     *
     * @param request
     * @return
     */
    private String saveUUID(HttpServletRequest request) {
        //获取响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //获取当前时间戳，精确到小时，防刷访客数据
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String timestamp = Long.toString(calendar.getTimeInMillis() / 1000);
        //获取访问者基本信息
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        //根据时间戳、ip、userAgent生成UUID
        String nameUUID = timestamp + ip + userAgent;
        String uuid = UUID.nameUUIDFromBytes(nameUUID.getBytes()).toString();
        //添加访客标识码UUID至响应头
        response.addHeader("identification", uuid);
        //暴露自定义header供页面资源使用
        response.addHeader("Access-Control-Expose-Headers", "identification");
        //TODO 校验Redis中是否存在uuid
//        boolean redisHas = redisService.hasValueInSet(RedisKeyConfig.IDENTIFICATION_SET, uuid);
        if (true) {
            //保存至Redis
            //redisService.saveValueToSet(RedisKeyConfig.IDENTIFICATION_SET, uuid);
            //保存至数据库
            Visitor visitor = new Visitor(uuid, ip, userAgent);
            visitorService.saveVisitor(visitor);
        }
        return uuid;
    }

    /**
     * 设置VisitLogger对象属性
     *
     * @param joinPoint
     * @param result
     * @param times
     * @return
     */
    private VisitLog handleLog(ProceedingJoinPoint joinPoint, ApiOperation apiOperation, HttpServletRequest request, Object result,
                               int times, String identification) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String behavior = apiOperation.value();
        String content = "";
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        Map<String, String> map = judgeBehavior(behavior, content, requestParams, result);
        VisitLog log = new VisitLog(identification, uri, method, behavior, map.get("content"), map.get("remark"), ip, times, userAgent);
        log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000));
        return log;
    }

    /**
     * 根据访问行为，设置对应的访问内容或备注
     *
     * @param behavior
     * @param content
     * @param requestParams
     * @param result
     * @return
     */
    private Map<String, String> judgeBehavior(String behavior, String content, Map<String, Object> requestParams, Object result) {
        Map<String, String> map = new HashMap<>();
        String remark = "";
        if (behavior.equals("访问页面") ) {
            int pageNum = (int) requestParams.get("pageNum");
            remark = "第" + pageNum + "页";
            content = "首页";
        } else if (behavior.equals("访问动态")) {
            int pageNum = (int) requestParams.get("pageNum");
            remark = "第" + pageNum + "页";
            content = "动态";
        } else if (behavior.equals("查看博客")) {
            Result res = (Result) result;
            if (res.getCode() == 200) {
                BlogDetail blog = (BlogDetail) res.getData();
                String title = blog.getTitle();
                content = title;
                remark = "文章标题：" + title;
            }
        } else if (behavior.equals("搜索博客")) {
            Result res = (Result) result;
            if (res.getCode() == 200) {
                String query = (String) requestParams.get("query");
                content = query;
                remark = "搜索内容：" + query;
            }
        } else if (behavior.equals("查看分类")) {
            String categoryName = (String) requestParams.get("categoryName");
            int pageNum = (int) requestParams.get("pageNum");
            content = categoryName;
            remark = "分类名称：" + categoryName + "，第" + pageNum + "页";
        } else if (behavior.equals("查看标签")) {
            String tagName = (String) requestParams.get("tagName");
            int pageNum = (int) requestParams.get("pageNum");
            content = tagName;
            remark = "标签名称：" + tagName + "，第" + pageNum + "页";
        } else if (behavior.equals("点击友链")) {
            String nickname = (String) requestParams.get("nickname");
            content = nickname;
            remark = "友链名称：" + nickname;
        }
        map.put("remark", remark);
        map.put("content", content);
        return map;
    }


}
