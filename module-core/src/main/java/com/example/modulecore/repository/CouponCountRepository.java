package com.example.modulecore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Long increment() {
        return redisTemplate.opsForValue().increment("coupon_count");
    }
}
