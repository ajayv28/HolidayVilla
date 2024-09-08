package com.ajay.HolidayVilla.exception;

public class NoOngoingBookingException extends RuntimeException{

    public NoOngoingBookingException(String message){
        super(message);
    }
}
