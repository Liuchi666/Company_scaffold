package com.wanlun.base.exception;


/**
 * Token格式异常
 * 说明：AuthException与 TokenException的区别在于：
 *        TokenException是解析token格式是否正确时抛出的异常
 *        AuthException是token格式解析正确，但是无法取出token载荷中保存的信息时抛出的异常
 *
 * @author 记住吾名梦寒
 * @since 22023/2/16 15:00
 */
public class TokenException extends ServiceException {
    public TokenException() {
        super("无效的token",4005);
    }
}
