package com.wanlun.base.entity;

import lombok.Getter;

/**
 *  统一返回结果实体类
 * @author 记住吾名梦寒
 * @update 2023/2/16 15:00
 * @since 2023/2/16 15:00
 */
@Getter
public class Result {
    private int code;
    private String message;
    private Object data;

    private Result setResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    /**
     *  请求成功
     */
    public Result success() {
        return setResult(200, "Success", null);
    }

    /**
     *  请求成功，携带返回数据
     */
    public Result success(Object data) {
        return setResult(200, "Success", data);
    }

    /**
     *  #双token专用# 请求成功，且嵌套携带提示信息(第一层的data就是第二层，此方法的参数作为第二层的属性值)
     */
    public Result success(int code,String message,Object data) {
        ResultPlus tempResult = ResultPlus.builder().code(code).message(message).data(data).build();
        return setResult(200,"Success",tempResult);
    }

    /**
     *  请求失败，携带返回数据
     */
    public Result fail(Object data, String message, int code) {
        return setResult(code, message, data);
    }

    /**
     *  请求失败
     */
    public Result fail(String message, int code) {
        return setResult(code, message, null);
    }
}
