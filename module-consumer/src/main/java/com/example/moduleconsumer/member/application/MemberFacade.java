package com.example.moduleconsumer.member.application;

import com.example.moduleconsumer.member.presentation.dto.request.CreateMemberRequest;
import com.example.moduleconsumer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    public void createMember(CreateMemberRequest createMemberRequest) {
        memberService.createMember(createMemberRequest);
    }
}
