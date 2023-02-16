package com.wanlun.base.enums;

/**
 * Auth认证模块 Token 类型枚举（用于生成token或者解析token时的Subject）
 *
 * @author 记住吾名梦寒
 * @since 2023/2/16 15:00
 */
public enum TokenSubject {
    ACCESS("access"), REFRESH("refresh");

    private final String type;

    TokenSubject(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
