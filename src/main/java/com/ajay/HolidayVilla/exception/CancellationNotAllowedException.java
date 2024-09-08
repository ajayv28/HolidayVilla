package com.ajay.HolidayVilla.exception;

public class CancellationNotAllowedException extends RuntimeException{

    public CancellationNotAllowedException(String message){
        super(message);
    }
}
