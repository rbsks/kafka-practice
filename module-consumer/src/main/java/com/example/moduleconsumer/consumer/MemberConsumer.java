package com.example.moduleconsumer.consumer;

import com.example.moduleconsumer.request.CreateMemberRequest;
import com.example.moduleconsumer.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConsumer {

    private final MemberService memberService;

    @KafkaListener(topics = "create_member", groupId = "create_member", containerFactory = "consumerListener")
    public void createMember(CreateMemberRequest createMemberRequest) {
        log.info("create member : {}", createMemberRequest);
        memberService.createMember(createMemberRequest);
    }

}
