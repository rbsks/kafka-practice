package com.example.moduleconsumer.coupon.service;

import com.example.modulejpa.coupon.entitiy.CouponEntity;
import com.example.modulejpa.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void createCoupon(Long memberId) {
        couponRepository.save(
                CouponEntity.builder()
                        .memberId(memberId)
                        .build()
        );
    }
}
