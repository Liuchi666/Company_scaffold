package com.wanlun.base.exception;


/**
 *      refreshToken过期异常
 */
public class RefreshTkExpireException extends ServiceException {
    public RefreshTkExpireException() {
        //抛出refreshToken过期异常
        super("请携带refreshToken", 4007);
    }
}
