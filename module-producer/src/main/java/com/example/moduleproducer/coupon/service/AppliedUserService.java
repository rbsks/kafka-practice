package com.example.moduleproducer.coupon.service;

import com.example.moduleredis.repository.AppliedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppliedUserService {

    private final AppliedUserRepository appliedUserRepository;

    public Long isCouponIssued(Long memberId) {
       return appliedUserRepository.add(memberId);
    }
}
