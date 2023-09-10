package com.example.modulecore.domain;

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
}
