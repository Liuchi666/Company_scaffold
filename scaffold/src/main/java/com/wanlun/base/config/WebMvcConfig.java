package com.wanlun.base.config;

import com.wanlun.base.handler.JacksonObjectHandler;
import com.wanlun.base.interceptor.RequestInterceptor;
import com.wanlun.base.resolver.AuthMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc配置
 *
 * @author 记住吾名梦寒
 * @update 2023/2/16 
 * @since 2023/2/16 
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    RequestInterceptor requestInterceptor;

    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                //允许的源头
                .allowedOrigins("*")
                // 设置允许的请求方式
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否允许cookie
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600)
                // 设置允许的header属性
                .allowedHeaders("*");
    }

    /**
     * 添加 @Auth 认证注解参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authMethodArgumentResolver());
    }

    /**
     * 注册 @Auth 认证注解参数解析器
     */
    @Bean
    public AuthMethodArgumentResolver authMethodArgumentResolver() {
        return new AuthMethodArgumentResolver();
    }

    /**
     *   注册拦截器
     * @param registry 拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }

    /**
     *  扩展springmvc中的消息转换器
     *  也就是说，原先springmvc框架里面本身就有8个消息转换器(可以下断点看形参内容得知)，但是如果我们要拓展功能，就要重新创建一个消息转换器，
     *  在新的消息转换器里面增强功能之后，将这个新的消息转换器添加到消息转换器容器(即方法的形参,一个List集合)中，并设置优先级(即index参数，跟索引一样越小越先执行)
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层通过jackson将Java对象转为json数据
        messageConverter.setObjectMapper(new JacksonObjectHandler());
        //将以上我们的消息转换器添加到消息转换器容器中，并设置执行优先级为0(索引为0最先执行)
        converters.add(0,messageConverter);
    }

}
