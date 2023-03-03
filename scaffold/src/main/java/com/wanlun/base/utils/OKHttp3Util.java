package com.wanlun.base.utils;


import com.alibaba.fastjson.JSON;
import com.wanlun.base.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 通过 OKHttp发送 HTTP请求的工具类
 * 依赖的jar包有：OKHttp3.jar
 *
 * @author 记住吾名梦寒
 * @Date 2023/2/22
 */
@Slf4j
public class OKHttp3Util {
    // 创建OkHttpClient对象, 并设置20s超时时间
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();

    /**
     * 发送一个 post请求，可自定义请求头和请求体内容(json格式)
     * @param url       请求路径 url
     * @param paramMap  请求体中携带的参数
     * @param headerMap 请求头中携带的参数
     * @return 响应体对象
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> paramMap, Map<String, String> headerMap) {
        if(paramMap == null){
           paramMap = new HashMap<>();
        }
        if(headerMap == null){
            headerMap = new HashMap<>();
        }
        try {
            //创建OkHttpClient对象 (此处是静态属性中创建)
            Headers headers = null;
            RequestBody requestBody = null;
            if (!paramMap.isEmpty()) {
                //构造出requestBody请求体对象(定义content-type和请求体内容)
                requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"),
                        JSON.toJSONString(paramMap));
            }

            if (!headerMap.isEmpty()) {
                //创建请求头构造器
                Headers.Builder headerBuilder = new Headers.Builder();
                //取出headerMap中封装的请求头信息
                Set<Map.Entry<String, String>> headerEntries = headerMap.entrySet();
                for (Map.Entry<String, String> headerEntry : headerEntries) {
                    String key = headerEntry.getKey();
                    String value = headerEntry.getValue();
                    headerBuilder.add(key, value);
                }
                //构造出请求头对象(包含多个请求头信息)
                headers = headerBuilder.build();
            }

            //创建请求对象
            Request request = new Request.Builder()
                    .url(url)        //请求路径
                    .post(requestBody) //请求方式为post，参数为请求体
                    .headers(headers)    //多个请求头信息
                    .build();

            //发送请求,获取响应对象
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.error(" *发送post请求失败* 请求路径url为： " + url + " 时失败，异常方法为：OKHttp3Util.post()");
                throw new ServiceException("发送post请求异常,响应码为:" + response.code() + ",响应信息为" + response.message());
            }
        } catch (Exception e) {
            log.error("*发送post请求失败*," + e.getMessage() + ",请求url为： " + url + " 时失败, 异常方法为：OKHttp3Util.post()");
            throw new ServiceException("发送post请求失败,报错信息为:" + e.getMessage(),108);
        }
    }

    public static String get(String url, Map<String, Object> params, Map<String, String> headerMap, List<String> restParams) {
        if(params == null){
            params = new HashMap<>();
        }
        if(headerMap == null){
            headerMap = new HashMap<>();
        }
        if(restParams == null){
            restParams = new ArrayList<>();
        }
        try {
            //创建OkHttpClient对象 (此处是静态属性中创建)
            Headers headers = null;
            if(!headerMap.isEmpty()){
                //创建请求头构造器
                Headers.Builder headerBuilder = new Headers.Builder();
                //取出headerMap中封装的请求头信息
                Set<Map.Entry<String, String>> headerEntries = headerMap.entrySet();
                for (Map.Entry<String, String> headerEntry : headerEntries) {
                    String key = headerEntry.getKey();
                    String value = headerEntry.getValue();
                    headerBuilder.add(key, value);
                }
                //构造出请求头对象(包含多个请求头信息)
                headers = headerBuilder.build();
            }

            //对请求路径url进行处理,因为get请求的请求参数都是拼接到url后的 例如 url/1/2?username=admin&password=admin
            if(!restParams.isEmpty()){  //将rest风格中的路径变量拼接到url中
                for (String restParam : restParams) {
                    url += "/" + restParam;
                }
            }

            if(!params.isEmpty()){  //将普通参数拼接进url
                //构建url构造器
                HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
//                urlBuilder.addPathSegments()方法也可以添加rest风格的参数,这里就用以上的
                Set<Map.Entry<String, Object>> entries = params.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    //将普通参数拼接进url
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            //创建请求对象
            Request request = new Request.Builder()
                    .url(url)        //请求路径
                    .get() //请求方式为post，参数为请求体
                    .headers(headers)    //多个请求头信息
                    .build();

            //发送请求,获取响应对象
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.error(" *发送get请求失败* 请求路径url为： " + url + " 时失败，异常方法为：OKHttp3Util.post()");
                throw new ServiceException("发送get请求异常,响应码为:" + response.code() + ",响应信息为" + response.message());
            }
        } catch (Exception e) {
            log.error("*发送get请求失败*," + e.getMessage() + ",请求url为： " + url + " 时失败, 异常方法为：OKHttp3Util.post()");
            throw new ServiceException("发送get请求失败,报错信息为:" + e.getMessage(),108);
        }
    }


}
