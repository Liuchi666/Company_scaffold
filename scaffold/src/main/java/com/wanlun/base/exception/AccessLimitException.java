package com.wanlun.base.exception;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2023/2/16 
 * @description  用户访问某一接口超出限制抛出的异常
 */
public class AccessLimitException extends ServiceException{
    public AccessLimitException() {
        //抛出请求次数超出限制异常
        super("请求过于频繁请稍后再试",4009);
    }
}
