package com.example.moduleproducer.controller;

import com.example.moduleproducer.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponProducer {

    private final ApplyService applyService;


    @PostMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createCoupon(@PathVariable Long memberId) {
       applyService.apply(memberId);
    }
}
