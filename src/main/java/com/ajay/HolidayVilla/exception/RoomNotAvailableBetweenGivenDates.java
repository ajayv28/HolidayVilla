package com.ajay.HolidayVilla.exception;

import com.ajay.HolidayVilla.model.Room;

public class RoomNotAvailableBetweenGivenDates extends RuntimeException{

    public RoomNotAvailableBetweenGivenDates(String message){
        super(message);
    }
}
