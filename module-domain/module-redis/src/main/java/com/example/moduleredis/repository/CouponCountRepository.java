package com.example.moduleredis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Long increment() {
        return redisTemplate.opsForValue().increment("coupon_count");
    }
}
