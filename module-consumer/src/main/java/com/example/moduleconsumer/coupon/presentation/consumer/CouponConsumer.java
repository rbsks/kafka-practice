package com.example.moduleconsumer.coupon.presentation.consumer;

import com.example.moduleconsumer.coupon.application.CouponFacade;
import com.example.moduleconsumer.member.presentation.dto.request.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponConsumer {

    private final CouponFacade couponFacade;

//    @KafkaListener(topics = "create_coupon", groupId = "create_coupon", containerFactory = "couponConsumerListener")
//    public void createCoupon(
//            @Header(name = "kafka_offset") int offset,
//            @Header(name = "kafka_receivedPartitionId") int partitionId,
//            @Header(name = "kafka_groupId") String groupId,
//            @Header(name = "kafka_consumer") KafkaConsumer<String, CreateMemberRequest> consumer,
//            @Payload Long memberId) {
//        log.info("offset: {}, partitionId: {}, groupId: {}", offset, partitionId, groupId);
//        log.info("consumer: {}", consumer.groupMetadata());
//        couponFacade.createCoupon(memberId);
//    }

    @KafkaListener(topics = "create_coupon", groupId = "create_coupon", containerFactory = "couponConsumerListener")
    public void createCoupon(Consumer<String, Long> consumer, ConsumerRecord<String, Long> record) {
        Long memberId = record.value();
        int partitionId = record.partition();
        long offset = record.offset();
        String groupId = consumer.groupMetadata().groupId();
        String topic = record.topic();
        log.info("offset: {}, partitionId: {}, groupId: {}", offset, partitionId, groupId);
        try {
            couponFacade.createCoupon(memberId);
            consumer.commitSync();
        } catch (CommitFailedException e) {
            log.error("Failed to commit offsets", e);
            // 컨슈머의 처리시간이 max.poll.interval.ms보다 커서 리밸런싱 작업이 일어나거나
            // session.timeout.ms 시간동안 heartbeat이 오지 않아 리밸런싱 작업이 일어난 경우
            // seek 메서드를 이용해 실패했던 지점으로 offset 값을 되돌림. 컨슈머의 재처리 같은 경우는 정책을 잘 정해야할 필요가 있어보임
            consumer.seek(new TopicPartition(topic, partitionId), offset);
        }

    }

}
