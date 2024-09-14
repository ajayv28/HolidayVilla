package com.ajay.HolidayVilla.exception;

public class AlreadyRegisteredException extends RuntimeException{
    public AlreadyRegisteredException(String message){
        super(message);
    }
}
