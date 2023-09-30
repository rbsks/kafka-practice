package com.example.moduleproducer.controller;

import com.example.moduleproducer.request.CreateMemberRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
public class MemberProducer {

    private final String CREATE_MEMBER_TOPIC = "create_member";
    private final KafkaTemplate<String, CreateMemberRequest> kafkaTemplate;

    public MemberProducer(@Qualifier("memberKafkaTemplate") KafkaTemplate<String, CreateMemberRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        ListenableFuture<SendResult<String, CreateMemberRequest>> send = kafkaTemplate.send(CREATE_MEMBER_TOPIC, createMemberRequest);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, CreateMemberRequest> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("{} {}", recordMetadata.topic(), recordMetadata.partition());
            }
        });
    }
}
