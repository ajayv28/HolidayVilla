package com.ajay.HolidayVilla.exception;

public class AlreadyBookingOngoingException extends RuntimeException{

    public AlreadyBookingOngoingException(String message){
        super(message);
    }
}
