package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.GuestRequest;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    GuestService guestService;

    @PostMapping("/register")
    public ResponseEntity registerGuest(@RequestBody GuestRequest guestRequest){

        GuestResponse guestResponse = guestService.registerGuest(guestRequest);
        return new ResponseEntity(guestResponse, HttpStatus.CREATED);
    }

    //order breakfast - f&b order
    //order lunch - f&b order

}
