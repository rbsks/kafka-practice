package com.example.moduleconsumer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMemberRequest {

    private String name;
    private String email;
    private String phoneNumber;
}
