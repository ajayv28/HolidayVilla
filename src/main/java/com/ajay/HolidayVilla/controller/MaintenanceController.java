package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

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

    @PutMapping("/update-followups-by-maintenanceId")
    public ResponseEntity updateFollowupsByMaintenanceId(@RequestParam String maintenanceId, @RequestParam String newFollowup){
        MaintenanceResponse maintenanceResponse = maintenanceService.updateFollowupsByMaintenanceId(maintenanceId, newFollowup);
        return new ResponseEntity(maintenanceResponse, HttpStatus.OK);
    }




    @GetMapping("/all-vacant-rooms-due-for-maintenance") //30 days
    public ResponseEntity allVacantRoomsDueForMaintenance(){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allVacantRoomsDueForMaintenance();
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    @GetMapping("all-occupied-room-due-for-maintenance-but-occupied-for-more-than-30-upcoming-days")
    public ResponseEntity allOccupiedRoomDueForMaintenanceButOccupiedForMoreThan30UpcomingDays(){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allOccupiedRoomDueForMaintenanceButOccupiedForMoreThan30UpcomingDays();
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }





    @GetMapping("/all-rooms-with-followups")
    public ResponseEntity allRoomsWithFollowups(){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allRoomsWithFollowups();
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    @GetMapping("/all-maintenance-with-followups-by-roomNo")
    public ResponseEntity allMaintenanceWithFollowupsByRoomNo(@RequestParam String roomNo){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceWithFollowupsByRoomNo(roomNo);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    @GetMapping("/all-maintenance-between-dates")
    public ResponseEntity allMaintenanceBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate){
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceBetweenDates(fromDate, toDate);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

    @GetMapping("/all-maintenance-by-staff-between-dates")
    public ResponseEntity allMaintenanceByStaffBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        List<MaintenanceResponse> maintenanceResponseList = maintenanceService.allMaintenanceByStaffBetweenDates(fromDate, toDate, staffEmail);
        return new ResponseEntity(maintenanceResponseList, HttpStatus.OK);
    }

}
