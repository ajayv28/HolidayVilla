package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.service.HRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    HRService hrService;

    @PostMapping("/onBoard")
    public ResponseEntity onBoardStaff(@RequestBody StaffRequest staffRequest){
        StaffResponse staffResponse = hrService.onBoardStaff(staffRequest);
        return new ResponseEntity(staffResponse, HttpStatus.CREATED);
    }

    @PostMapping("/offBoard")
    //only remove role



}
