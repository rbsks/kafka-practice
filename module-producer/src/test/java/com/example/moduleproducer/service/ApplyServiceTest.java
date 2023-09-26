package com.example.moduleproducer.service;

import com.example.modulecore.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void oneRequest() {
        applyService.apply(1L);

        long count = couponRepository.getCount();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void manyRequest() throws Exception {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long memberId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(memberId);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        long count = couponRepository.getCount();

        assertThat(count).isEqualTo(100);
    }

    @Test
    public void manyRequestByUniqueUser() throws Exception {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    applyService.apply(1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        long count = couponRepository.getCount();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void redisIncrement() {
        Long coupon_count = redisTemplate.opsForValue().increment("coupon_count");
        String coupon_count1 = redisTemplate.opsForValue().get("coupon_count");
        System.out.println(coupon_count);
        System.out.println(coupon_count1);
    }
}