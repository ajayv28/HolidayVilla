package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/add")
    public ResponseEntity addRoom(@RequestBody RoomRequest roomRequest){
        RoomResponse roomResponse = roomService.addRoom(roomRequest);
        return new ResponseEntity(roomResponse, HttpStatus.CREATED);
    }
}
