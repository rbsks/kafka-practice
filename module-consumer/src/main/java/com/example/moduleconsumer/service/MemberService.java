package com.example.moduleconsumer.service;

import com.example.moduleconsumer.request.CreateMemberRequest;
import com.example.modulejpa.domain.Member;
import com.example.modulejpa.entity.MemberEntity;
import com.example.modulejpa.repository.MemberRepository;
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
