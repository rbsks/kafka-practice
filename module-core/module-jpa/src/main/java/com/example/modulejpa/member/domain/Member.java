package com.example.modulejpa.member.domain;

import com.example.modulejpa.member.entitiy.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public static Member of (MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .build();
    }
}
