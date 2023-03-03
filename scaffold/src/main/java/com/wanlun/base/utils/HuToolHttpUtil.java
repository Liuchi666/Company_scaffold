package com.wanlun.base.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2023/2/23
 * @description
 */
public class HuToolHttpUtil {

    /**
     * 	发送一个 post请求，可自定义请求头和请求体内容
     * 	(此方法是在请求体中通过json字符串传递参数,接口提供方需用@RequestBody接收)
     * @param url	请求路径 url
     * @param params 请求体中携带的参数
     * @param headers	请求头中携带的参数
     * @return	响应体对象
     * @throws IOException
     */
    public static String postOfBody(String url, Map<String,Object> params, Map<String,String> headers){
        HttpRequest request = HttpRequest.post(url)
                .headerMap(headers, false)      //isOverride代表是否覆盖原先的请求头信息
                .body(JSON.toJSONString(params)).timeout(20000);   //请求体中带json格式的参数
        HttpResponse execute = request.execute();
        String body = execute.body();
        return body;
    }

    /**
     * 	发送一个 post请求，可自定义请求头和请求参数
     * 	(此方法是通过模拟form表单)
     * @param url	请求路径 url
     * @param params 请求体中携带的参数
     * @param headers	请求头中携带的参数
     * @return	响应体对象
     * @throws IOException
     */
    public static String postOfForm(String url, Map<String,Object> params, Map<String,String> headers){
        HttpRequest request = HttpRequest.post(url)
                .headerMap(headers, false)      //isOverride代表是否覆盖原先的请求头信息
                .form(params) //表单内容
                .timeout(20000);
        HttpResponse execute = request.execute();
        String body = execute.body();
        return body;
    }


}
