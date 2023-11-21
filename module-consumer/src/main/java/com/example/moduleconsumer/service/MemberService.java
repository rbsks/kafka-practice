package com.example.moduleconsumer.service;

import com.example.moduleconsumer.request.CreateMemberRequest;
import com.example.modulerdbms.domain.Member;
import com.example.modulerdbms.entity.MemberEntity;
import com.example.modulerdbms.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(CreateMemberRequest createMemberRequest) {
        return memberRepository.save(
                MemberEntity.builder()
                        .email(createMemberRequest.getEmail())
                        .name(createMemberRequest.getName())
                        .phoneNumber(createMemberRequest.getPhoneNumber())
                        .build()
        );
    }
}
