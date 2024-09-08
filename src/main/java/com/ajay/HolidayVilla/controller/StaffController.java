package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.service.BookingService;
import com.ajay.HolidayVilla.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    BookingService bookingService;


    @PostMapping("/register")
    public ResponseEntity registerStaff(@RequestBody StaffRequest staffRequest){
        StaffResponse staffResponse = staffService.registerStaff(staffRequest);
        return new ResponseEntity(staffResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-upcoming-arrival-booking-by-roomNo")
    @GetMapping("/get-all-upcoming-arrival-booking-by-guestEmail")
    @GetMapping("/get-booking-by-bookingId")
    @GetMapping("/get-all-booking-between-dates")
    @GetMapping("/get-all-upcoming-arrival-booking") //sort by room type
    @GetMapping("/get-all-checked_out-booking")
    @GetMapping("/get-all-cancelled-booking")
    @GetMapping("/get-all-inhouse-booking")

    @GetMapping("/get-all-inhouse-breakfast-booking")
    @GetMapping("/get-all-inhouse-no-breakfast-booking")

    @GetMapping("/get-all-today-inhouse-booking")
    @GetMapping("/get-count-of-today-inhouse-booking")
    @GetMapping("/get-all-room-by-room-status")  //use to string
    @GetMapping("/get-all-room-by-room-status")

    @GetMapping("/get-all-upcoming-arrival-stay-more-than-n-days")
    @GetMapping("/get-all-booking-occupied-on-given-date")




    @PutMapping("/cancel-booking-by-bookingId")
    public ResponseEntity cancelBookingByBookingId(@RequestParam String bookingId){
        BookingResponse bookingResponse = bookingService.cancelBookingByBookingId(bookingId);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @PutMapping("/cancel-booking-by-guestEmail")
    public ResponseEntity cancelBookingByGuestEmail(@RequestParam String guestEmail){
        BookingResponse bookingResponse = bookingService.cancelBooking(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }



//    change booking to anothet room and notify guest




}
