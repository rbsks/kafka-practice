package com.example.modulejpa.coupon.repository;

import com.example.modulejpa.coupon.entitiy.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
}
