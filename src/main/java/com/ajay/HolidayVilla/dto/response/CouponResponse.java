package com.ajay.HolidayVilla.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CouponResponse {

    String couponCode;

    int quantityRemaining;

    double offerPercentage;
}
