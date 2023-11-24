package com.example.modulejpa.repository;

import com.example.modulejpa.domain.Coupon;
import com.example.modulejpa.entity.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Transactional
    public Coupon save(CouponEntity couponEntity) {
        CouponEntity saveCoupon = couponJpaRepository.save(couponEntity);
        return Coupon.builder()
                .id(saveCoupon.getId())
                .memberId(saveCoupon.getMemberId())
                .build();
    }
}
