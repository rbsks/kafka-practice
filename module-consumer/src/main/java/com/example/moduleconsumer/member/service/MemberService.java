package com.example.moduleconsumer.member.service;

import com.example.moduleconsumer.member.presentation.dto.request.CreateMemberRequest;
import com.example.modulejpa.member.domain.Member;
import com.example.modulejpa.member.entitiy.MemberEntity;
import com.example.modulejpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(CreateMemberRequest createMemberRequest) {
        return Member.of(memberRepository.save(
                MemberEntity.builder()
                        .email(createMemberRequest.getEmail())
                        .name(createMemberRequest.getName())
                        .phoneNumber(createMemberRequest.getPhoneNumber())
                        .build()
                )
        );
    }
}
