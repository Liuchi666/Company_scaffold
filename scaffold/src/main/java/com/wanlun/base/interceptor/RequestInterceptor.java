package com.wanlun.base.interceptor;

import com.wanlun.base.annotation.AccessLimit;
import com.wanlun.base.exception.AccessLimitException;
import com.wanlun.base.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by LPB on 2022-05-01.
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *     在拦截器中判断某一ip对某一接口的访问次数是否超过限制
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求拦截器执行了=========================");
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
                if (accessLimit == null) {
                    return true;  //直接放行
                }
                int seconds = accessLimit.seconds(); //获取指定的时间
                int maxCount = accessLimit.maxCount();  //获取指定时间内指定执行的次数
                //获取请求的ip
                String ip = IpUtil.getIpAddress(request);
//                String ip = request.getRemoteHost();
                //获取servlet路径（即接口路径）
                String servletPath = request.getServletPath();
                //拼接redis的key   getContextPath()获取项目名路径
                String key = ip + ":" + servletPath;
                // 已经访问的次数
                Integer count = (Integer) redisTemplate.opsForValue().get(key);
                if (count != null) {
                    //打印记录信息
                    log.info("(ip限流请求次数) ip:{} 接口名:{} 访问次数:{}", ip, servletPath, count);
                }
                if (count == null || count == -1) {
                    //说明还没有请求记录，则向redis中插入一条记录
                    redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
                    return true;  //放行
                }
                if (count < maxCount) {
                    //还没有超过最大请求限制，请求次数加一
                    redisTemplate.opsForValue().increment(key, 1);
                    return true;
                }

                /**  拦截器中直接返回false可直接中断 http请求,
                 *      可能会导致我们在WebMvcConfigurer的addCrosMappings中配置的跨域问题解决方案就无法正常执行,
                 *      所以我们在拦截器中preHandle中return false前要给response进行配置如下：  */
                String origin = request.getHeader("Origin");
                response.addHeader("Access-Control-Allow-Origin", origin);
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.addHeader("Access-Control-Max-Age", "3600");

                //超过了最大请求限制，直接返回请求过于频繁
                throw new AccessLimitException();
            }
        return true;
    }

}
