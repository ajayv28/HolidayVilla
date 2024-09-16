package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.CouponRequest;
import com.ajay.HolidayVilla.dto.response.CouponResponse;
import com.ajay.HolidayVilla.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    //**TESTED**
    @PostMapping("/register")
    public ResponseEntity registerCoupon(@RequestBody CouponRequest couponRequest){
        CouponResponse couponResponse = couponService.registerCoupon(couponRequest);
        return new ResponseEntity(couponResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity cancelCoupon(@RequestParam String couponCode){
        String response = couponService.cancelCoupon(couponCode);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/changeQuantity")
    public ResponseEntity changeQuantity(@RequestParam String couponCode, @RequestParam int newQuantity){
        CouponResponse couponResponse = couponService.changeQuantity(couponCode, newQuantity);
        return new ResponseEntity(couponResponse, HttpStatus.OK);
    }
}
