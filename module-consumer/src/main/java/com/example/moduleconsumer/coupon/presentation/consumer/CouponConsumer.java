package com.example.moduleconsumer.coupon.presentation.consumer;

import com.example.moduleconsumer.coupon.application.CouponFacade;
import com.example.moduleconsumer.member.presentation.dto.request.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponConsumer {

    private final CouponFacade couponFacade;

    @KafkaListener(topics = "create_coupon", groupId = "create_coupon", containerFactory = "couponConsumerListener")
    public void createMember(
            @Header(name = "kafka_offset") int offset,
            @Header(name = "kafka_receivedPartitionId") int partitionId,
            @Header(name = "kafka_groupId") String groupId,
            @Header(name = "kafka_consumer") KafkaConsumer<String, CreateMemberRequest> consumer,
            @Payload Long memberId) {
        log.info("offset: {}, partitionId: {}, groupId: {}", offset, partitionId, groupId);
        log.info("consumer: {}", consumer.groupMetadata());
        couponFacade.createCoupon(memberId);
    }

}
