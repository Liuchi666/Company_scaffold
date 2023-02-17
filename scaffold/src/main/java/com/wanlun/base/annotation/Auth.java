package com.wanlun.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 认证注解 @Auth 说明：
 *      用法： 用于标注需要认证的接口之上
 *      配合 AuthMethodArgumentResolver使用（在该类中定义了接口参数类型、认证流程等细节）
 *
 * @author 记住吾名梦寒
 * @update 2023/2/16 
 * @since 2023/2/16 
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    boolean required() default true;
}
