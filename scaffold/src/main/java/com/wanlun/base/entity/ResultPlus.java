package com.wanlun.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2023/2/16 15:00
 * @description  专门用于给统一返回结果类 Result 作二次封装的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultPlus {
    private int code;
    private String message;
    private Object data;
}
