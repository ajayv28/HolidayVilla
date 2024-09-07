package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest, guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.CREATED);
    }
}
