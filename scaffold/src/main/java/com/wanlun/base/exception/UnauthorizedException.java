package com.wanlun.base.exception;

/**
 * 未认证异常（在需要携带 token的时候没有携带）
 *
 * @author 记住吾名梦寒
 * @since 2023/2/16 15:00
 */
public class UnauthorizedException extends ServiceException {
    public UnauthorizedException() {
        super("Unauthorized", 4001);
    }
}
