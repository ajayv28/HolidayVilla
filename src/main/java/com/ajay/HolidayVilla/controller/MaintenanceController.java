package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;


    @PostMapping("/postMaintenance")
    public ResponseEntity postMaintenance(@RequestBody MaintenanceRequest maintenanceRequest, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        MaintenanceResponse maintenanceResponse = maintenanceService.postMaintenance(maintenanceRequest, staffEmail);
        return new ResponseEntity(maintenanceResponse, HttpStatus.CREATED);
    }


    @GetMapping("/all-vacant-rooms-due-for-maintenance") //30 days
    @GetMapping("all-occupied-room-due- maintenance-but-occupied-for-more-than-30-upcoming-days")

    @GetMapping("/all-rooms-with-followups")
    @GetMapping("/all-maintenance-with-followups-by-roomNo")

    @PutMapping("/update-followups-by-maintenanceId")

    @GetMapping("/all-maintenance-between-dates")
    @GetMapping("/all--maintenance-by-staffEmail-between-dates")


}
