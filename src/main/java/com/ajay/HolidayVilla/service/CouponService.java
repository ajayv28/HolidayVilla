package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.CouponTransformer;
import com.ajay.HolidayVilla.dto.request.CouponRequest;
import com.ajay.HolidayVilla.dto.response.CouponResponse;
import com.ajay.HolidayVilla.model.Coupon;
import com.ajay.HolidayVilla.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    public CouponResponse registerCoupon(CouponRequest couponRequest) {
        Coupon savedCoupon = couponRepository.save(CouponTransformer.couponRequestToCoupon(couponRequest));
        return CouponTransformer.couponToCouponResponse(savedCoupon);
    }
}
