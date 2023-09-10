package com.example.modulecore.repository;

import com.example.modulecore.domain.Member;
import com.example.modulecore.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Member save(MemberEntity memberEntity) {
        MemberEntity saveMember = memberJpaRepository.save(memberEntity);
        return Member.builder()
                .id(saveMember.getId())
                .name(saveMember.getName())
                .email(saveMember.getEmail())
                .phoneNumber(saveMember.getPhoneNumber())
                .build();
    }
}