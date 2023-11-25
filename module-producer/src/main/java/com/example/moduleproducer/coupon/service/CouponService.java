package com.example.moduleproducer.coupon.service;

import com.example.moduleredis.repository.CouponCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponCountRepository couponCountRepository;

    public Long getNumberOfCouponIssued() {
        return couponCountRepository.increment();
    }
}
