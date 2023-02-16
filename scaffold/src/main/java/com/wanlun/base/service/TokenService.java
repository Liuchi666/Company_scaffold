package com.wanlun.base.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Map;

/**
 * Token服务
 *
 * @author: 记住吾名梦寒
 * @since: 2023/2/16 15:00
 * @update: 2023/2/16 15:00
 **/
public interface TokenService {
    String generate(Object subject, long id);

    String generate(Object subject, long id, int hours);

    String generate(Object subject, Map<String, Object> payload);

    String generate(Object subject, Map<String, Object> payload, int hours);

    Jws<Claims> parse(Object subject, String token);
}
