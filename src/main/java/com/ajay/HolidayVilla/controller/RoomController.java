package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.Enum.RoomStatus;
import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.service.BookingService;
import com.ajay.HolidayVilla.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity addRoom(@RequestBody RoomRequest roomRequest){
        RoomResponse roomResponse = roomService.addRoom(roomRequest);
        return new ResponseEntity(roomResponse, HttpStatus.CREATED);
    }

    @PutMapping("/check-in-with-bookingId")
    public ResponseEntity checkInWithBookingId(@RequestParam String bookingId){
        RoomResponse roomResponse = roomService.checkInWithBookingId(bookingId);
        return new ResponseEntity(roomResponse, HttpStatus.OK);
    }

    @PutMapping("/check-out-with-bookingId")
    public ResponseEntity checkOutWithBookingId(@RequestParam String bookingId){
        RoomResponse roomResponse = roomService.checkOutWithBookingId(bookingId);
        return new ResponseEntity(roomResponse, HttpStatus.OK);
    }

    @PutMapping("/change-room-status-by-roomNo")
    public ResponseEntity changeRoomStatusByRoomNo(@RequestParam String roomNo, @RequestParam RoomStatus roomStatus ){
        RoomResponse roomResponse = roomService.changeRoomStatusByRoomNo(roomNo, roomStatus);
        return new ResponseEntity(roomResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-today-inhouse-room")
    public ResponseEntity getAllTodayInHouseRoom(){
        List<RoomResponse> roomResponse = roomService.getAllTodayInHouseRoom();
        return new ResponseEntity(roomResponse, HttpStatus.OK);
    }

    @GetMapping("/get-count-of-today-inhouse-room")
    public ResponseEntity getCountOfTodayInHouseRoom(){
        int bookingResponse = roomService.getCountOfTodayInHouseRoom();
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }


    @GetMapping("/get-all-room-by-room-status")  //use to string
    public ResponseEntity getAllRoomByRoomStatus(@RequestParam RoomStatus roomStatus){
        List<RoomResponse> roomResponse = roomService.getAllRoomByRoomStatus(roomStatus.toString());
        return new ResponseEntity(roomResponse, HttpStatus.OK);
    }
}
