package com.ajay.HolidayVilla.exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message){
        super(message);
    }
}
