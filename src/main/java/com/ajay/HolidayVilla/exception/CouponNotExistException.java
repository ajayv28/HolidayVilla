package com.ajay.HolidayVilla.exception;

import org.springframework.data.jpa.repository.JpaRepository;

public class CouponNotExistException extends RuntimeException {

    public CouponNotExistException(String message){
        super(message);
    }
}
