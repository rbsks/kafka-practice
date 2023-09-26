package com.example.moduleconsumer.consumer;

import com.example.moduleconsumer.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponConsumer {

    private final CouponService couponService;

    @KafkaListener(topics = "create_coupon", groupId = "create_coupon", containerFactory = "couponConsumerListener")
    public void createMember(@Payload Long memberId) {
        couponService.createCoupon(memberId);
    }

}
