package com.shop.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisCache() {

    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void put(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void put(Object key, Object value, int expire) {
        redisTemplate.opsForValue().set(key, value, expire);
    }

    public void remove(Object key) {
        redisTemplate.delete(key);
    }

}
