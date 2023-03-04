package com.fantasy.interceptor;

import com.fantasy.exception.BizException;
import com.fantasy.model.Result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义业务异常拦截器
 */
@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result bizHandler(BizException e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result runtimeHandler(RuntimeException e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

}
