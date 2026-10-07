package com.farkhod.famousbooksapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StringValueRedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public void save(String key, String value, int seconds) {
        stringRedisTemplate.opsForValue()
                .set(key, value, Expiration.from(seconds, TimeUnit.SECONDS));
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue()
                .get(key);
    }

    public void clear(String key) {
        stringRedisTemplate.delete(key);
    }

}
