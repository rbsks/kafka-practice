package com.example.moduleproducer.coupon.presentation.producer;

import com.example.moduleproducer.coupon.application.CouponFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponProducer {

    private final CouponFacade couponFacade;


    @PostMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createCoupon(@PathVariable final Long memberId) {
       couponFacade.createCoupon(memberId);
    }
}
