package com.doo.aqqle.portal.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void writeToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public String readFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Long incrementCache(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
    public Long decrementCache(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
