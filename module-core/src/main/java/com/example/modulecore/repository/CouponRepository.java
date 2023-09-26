package com.example.modulecore.repository;

import com.example.modulecore.domain.Coupon;
import com.example.modulecore.entity.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    public long getCount() {
        return couponJpaRepository.count();
    }

    @Transactional
    public Coupon save(CouponEntity couponEntity) {
        CouponEntity saveCoupon = couponJpaRepository.save(couponEntity);
        return Coupon.builder()
                .id(saveCoupon.getId())
                .memberId(saveCoupon.getMemberId())
                .build();
    }
}
