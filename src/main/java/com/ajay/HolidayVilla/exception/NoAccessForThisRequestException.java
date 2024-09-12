package com.ajay.HolidayVilla.exception;

public class NoAccessForThisRequestException extends RuntimeException{
    public NoAccessForThisRequestException(String message){
        super(message);
    }
}
