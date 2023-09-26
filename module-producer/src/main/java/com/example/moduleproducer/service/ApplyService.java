package com.example.moduleproducer.service;

import com.example.modulecore.repository.AppliedUserRepository;
import com.example.modulecore.repository.CouponCountRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ApplyService {

    private final CouponCountRepository couponCountRepository;
    private final AppliedUserRepository appliedUserRepository;
    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final String CREATE_COUPON_TOPIC = "create_coupon";

    public ApplyService(
            @Qualifier("couponKafkaTemplate") KafkaTemplate<String, Long> kafkaTemplate,
            CouponCountRepository couponCountRepository, AppliedUserRepository appliedUserRepository) {
        this.couponCountRepository = couponCountRepository;
        this.appliedUserRepository = appliedUserRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void apply(Long memberId) {
        if (appliedUserRepository.add(memberId) != 1) {
            return;
        }
        if (couponCountRepository.increment() > 100L) {
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
