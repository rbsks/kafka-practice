package com.example.moduleproducer.controller;

import com.example.moduleproducer.request.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberProducer {

    private final String CREATE_MEMBER_TOPIC = "create_member";
    private final KafkaTemplate<String, CreateMemberRequest> kafkaTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        kafkaTemplate.send(CREATE_MEMBER_TOPIC, createMemberRequest);
    }
}
