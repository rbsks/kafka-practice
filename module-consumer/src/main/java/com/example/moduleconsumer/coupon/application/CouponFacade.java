package com.example.moduleconsumer.coupon.application;

import com.example.moduleconsumer.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;

    public void createCoupon(Long memberId) {
        couponService.createCoupon(memberId);
    }
}
