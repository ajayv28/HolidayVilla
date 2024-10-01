package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    //**TESTED* *FRONTEND ADDED**
    @PostMapping("/post-maintenance-job")
    public ResponseEntity postMaintenanceJob(@RequestBody MaintenanceRequest maintenanceRequest, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        MaintenanceResponse maintenanceResponse = maintenanceService.postMaintenance(maintenanceRequest, staffEmail);
        return new ResponseEntity(maintenanceResponse, HttpStatus.CREATED);
    }

    //**TESTED* *FRONTEND ADDED**
    @PutMapping("/update-followups-by-maintenanceId")
    public ResponseEntity updateFollowupsByMaintenanceId(@RequestParam String maintenanceId, @RequestParam String newFollowup){
        MaintenanceResponse maintenanceResponse = maintenanceService.updateFollowupsByMaintenanceId(maintenanceId, newFollowup);
        return new ResponseEntity(maintenanceResponse, HttpStatus.OK);
    }



    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-vacant-rooms-due-for-maintenance") //30 days
    public ResponseEntity allVacantRoomsDueForMaintenance(){
        List<RoomResponse> roomResponseList = maintenanceService.allVacantRoomsDueForMaintenance();
        return new ResponseEntity(roomResponseList, HttpStatus.OK);
    }




    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-rooms-with-followups")
    public ResponseEntity allRoomsWithFollowups(){
        List<RoomResponse> roomResponseList = maintenanceService.allRoomsWithFollowups();
        return new ResponseEntity(roomResponseList, HttpStatus.OK);
    }

    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-maintenance-with-followups-by-roomNo")
    public ResponseEntity allMaintenanceWithFollowupsByRoomNo(@RequestParam String roomNo){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceWithFollowupsByRoomNo(roomNo);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-maintenance-between-dates")
    public ResponseEntity allMaintenanceBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceBetweenDates(fromDate, toDate);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-maintenance-by-logged-in-staff-between-dates")
    public ResponseEntity allMaintenanceByLoggedInStaffBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceByStaffBetweenDates(fromDate, toDate, staffEmail);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    //**TESTED* *FRONTEND ADDED**
    @GetMapping("/all-maintenance-by-staffEmail-between-dates")
    public ResponseEntity allMaintenanceByStaffEmailBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate, @RequestParam String staffEmail){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceByStaffBetweenDates(fromDate, toDate, staffEmail);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

}
