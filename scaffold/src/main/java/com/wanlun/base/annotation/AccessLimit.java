package com.wanlun.base.annotation;

import java.lang.annotation.*;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2023/2/16 15:00
 * @description
 *      自定义注解 @AccessLimit，用于限制用户对接口的请求次数限制
 *      用法：用于接口声明之上
 */
@Target({ElementType.TYPE,ElementType.METHOD})  //该自定义注解可以用在类上、接口上、方法声明上
@Retention(RetentionPolicy.RUNTIME)  //该注解在运行时可由VM保留，既可以通过反射机制获取
@Documented
public @interface AccessLimit {

    /**
     *  指定的时间，单位：秒
     */
    int seconds() default 60;

    /**
     *  在指定时间内对API的请求次数
     */
    int maxCount() default 1;
}
