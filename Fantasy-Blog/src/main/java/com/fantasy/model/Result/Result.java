package com.fantasy.model.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 封装响应结果
 */
@Data
@NoArgsConstructor
@ToString
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    private Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok(String msg,Object data){
        return new Result(200,msg,data);
    }

    public static Result ok(String msg){
        return new Result(200,msg);
    }

    public static Result ok(Object data){
        return new Result(200,"请求成功",data);
    }

    public static Result error(String msg){
        return new Result(500,msg);
    }

    public static Result error(){
        return new Result(500,"异常错误");
    }

}
