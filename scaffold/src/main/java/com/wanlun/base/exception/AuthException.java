package com.wanlun.base.exception;

/**
 * 认证异常 说明携带的 token无效
 *    说明：AuthException与 TokenException的区别在于：
 *          TokenException是解析token格式是否正确时抛出的异常
 *          AuthException是token格式解析正确，但是无法取出token载荷中保存的信息时抛出的异常
 * @author 记住吾名梦寒
 * @since 2023/2/16 
 */
public class AuthException extends ServiceException {
    public AuthException() {
        //抛出身份认证异常
        super("Authentication failed", 4003);
    }
}
