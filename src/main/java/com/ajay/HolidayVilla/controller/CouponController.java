package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.CouponRequest;
import com.ajay.HolidayVilla.dto.response.CouponResponse;
import com.ajay.HolidayVilla.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping("/register")
    public ResponseEntity registerCoupon(@RequestBody CouponRequest couponRequest){
        CouponResponse couponResponse = couponService.registerCoupon(couponRequest);
        return new ResponseEntity(couponResponse, HttpStatus.CREATED);
    }
}
