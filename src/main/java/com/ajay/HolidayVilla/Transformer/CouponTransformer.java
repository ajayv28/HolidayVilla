package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.CouponRequest;
import com.ajay.HolidayVilla.dto.response.CouponResponse;
import com.ajay.HolidayVilla.model.Coupon;
import jakarta.servlet.http.PushBuilder;
import org.hibernate.id.factory.internal.UUIDGenerationTypeStrategy;

import java.util.UUID;

public class CouponTransformer {

    public static Coupon couponRequestToCoupon(CouponRequest couponRequest){
        return Coupon.builder()
                .quantityRemaining(couponRequest.getQuantityRemaining())
                .offerPercentage(couponRequest.getOfferPercentage())
                .couponCode(String.valueOf(UUID.randomUUID()))
                .build();
    }

    public static CouponResponse couponToCouponResponse(Coupon coupon){
        return CouponResponse.builder()
                .couponCode(coupon.getCouponCode())
                .offerPercentage(coupon.getOfferPercentage())
                .quantityRemaining(coupon.getQuantityRemaining())
                .build();
    }
}
