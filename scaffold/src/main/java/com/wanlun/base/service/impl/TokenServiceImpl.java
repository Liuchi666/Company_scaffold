package com.wanlun.base.service.impl;

import com.wanlun.base.exception.TokenException;
import com.wanlun.base.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token服务
 *
 * @author 记住吾名梦寒
 * @update 2023/2/16 
 * @since 2023/2/16 
 **/
@SuppressWarnings("all")
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * 注意：
     * 此处为jjwt的简单封装，Token密钥所使用的加密方式为最简单的方式。
     * 实践中建议参考jjwt的文档对该业务层进行进一步的改造。
     **/

    /**
     * Token密钥  生产环境的密钥是 Prod开头  开发环境是 Dev开头
     **/
    @Value("${token.secret}")   //密钥规则在application中配置了
    private String SECRECT;

    /**
     * 将密钥转换成byte数组
     **/
    private byte[] getSecret() {
        return Base64.getEncoder().encode(SECRECT.getBytes());
    }

    /**
     * 生成只有Id的Payload
     **/
    private Map<String, Object> buildPayloadOnlyId(long id) {
        return new HashMap<String, Object>() {
            {
                put("id", id);
            }
        };
    }

    /**
     * 生成Token
     **/
    private String buildToken(Object subject, Map<String, Object> payload, int hours) {
//        return Jwts.builder().setClaims(payload).setSubject(subject.toString()).setExpiration(new Date(new Date().getTime() + (hours * 3600000)))
//                .signWith(SignatureAlgorithm.HS256, getSecret()).compact();

        //临时测试改成单位为 分钟 accessToken有效期2分钟 refreshToken有效期24分钟
        return Jwts.builder().setClaims(payload).setSubject(subject.toString()).setExpiration(new Date(System.currentTimeMillis() + (hours * 3600000)))
                .signWith(SignatureAlgorithm.HS256, getSecret()).compact();
    }

    /**
     * 生成Payload只带Id的Token，有效期为1个小时
     **/
    @Override
    public String generate(Object subject, long id) {
        return buildToken(subject, buildPayloadOnlyId(id), 2);
    }

    /**
     * 生成Payload只带Id的Token，可以自定义有效期
     **/
    @Override
    public String generate(Object subject, long id, int hours) {
        return buildToken(subject, buildPayloadOnlyId(id), hours);
    }

    /**
     * 通过Map设置带Payload的Token，有效期为一个小时
     **/
    @Override
    public String generate(Object subject, Map<String, Object> payload) {
        return buildToken(subject, payload, 1);
    }

    /**
     * 通过Map设置带Payload的Token，可以自定义有效期
     **/
    @Override
    public String generate(Object subject, Map<String, Object> payload, int hours) {
        return buildToken(subject, payload, hours);
    }

    /**
     * 格式化Token，返回一个Jws对象
     **/
    @Override
    public Jws<Claims> parse(Object subject, String token) {
        try {
            return Jwts.parser().requireSubject(subject.toString()).setSigningKey(getSecret()).parseClaimsJws(token);
        } catch (JwtException e) {
            throw new TokenException();
        }
    }

}
