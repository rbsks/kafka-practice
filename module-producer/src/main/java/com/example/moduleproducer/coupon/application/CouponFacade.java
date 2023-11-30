package com.example.moduleproducer.coupon.application;

import com.example.moduleproducer.coupon.service.AppliedUserService;
import com.example.moduleproducer.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;

@Slf4j
@Component
public class CouponFacade {

    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final CouponService couponService;
    private final AppliedUserService appliedUserService;
    private final String CREATE_COUPON_TOPIC = "create_coupon";

    public CouponFacade(
            @Qualifier("couponKafkaTemplate") KafkaTemplate<String, Long> kafkaTemplate,
            CouponService couponService, AppliedUserService appliedUserService) {
        this.couponService = couponService;
        this.appliedUserService = appliedUserService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createCoupon(final Long memberId) {
        if (!Objects.equals(appliedUserService.isCouponIssued(memberId), 1L)) {
            log.error("이미 쿠폰을 발급 받은 회원입니다.");
            return;
        }
        if (couponService.getNumberOfCouponIssued() > 100L) {
            log.error("쿠폰 마감!!!!");
            return;
        }

        ListenableFuture<SendResult<String, Long>> send = kafkaTemplate.send(CREATE_COUPON_TOPIC, memberId);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, Long> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("{} {}", recordMetadata.topic(), recordMetadata.partition());
            }
        });
    }

}
