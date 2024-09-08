package com.ajay.HolidayVilla.exception;

public class CouponExpiredException extends RuntimeException{

    public CouponExpiredException(String message){
        super(message);
    }
}
