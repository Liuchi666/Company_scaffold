package com.wanlun.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2023/2/16 15:00
 * @description    Redis中操作 Hash数据类型的工具类
 */
@Component
public class RedisHashUtil{

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  向Redis中插入一条hash数据
     * @param hashName  Redis中 hash数据类型最外围的 key
     * @param key 内部的key
     * @param value 内部的value
     */
    public void saveToRedis(String hashName, String key,Integer value) {
        redisTemplate.opsForHash().put(hashName,key,value);
    }

    /**
     *  从Redis中删除一条hash数据
     */
    public void deleteFromRedis(String hashName, String key) {
        redisTemplate.opsForHash().delete(hashName,key);
    }

    /**
     *  将Redis中的hash数据的value加一
     */
    public void incrementCount(String hashName, String key) {
        redisTemplate.opsForHash().increment(hashName,key,1);
    }

    /**
     *  将Redis中的hash数据的value减一
     */
    public void decrementCount(String hashName, String key) {
        redisTemplate.opsForHash().increment(hashName,key,-1);
    }

    /**
     *  查询某条hash数据是否存在
     */
    public Integer getLikeFromRedis(String hashName, String key){
        Integer code = (Integer) redisTemplate.opsForHash().get(hashName,key);
        return code;
    }


}
