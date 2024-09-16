package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    StaffService staffService;


    //**TESTED**
    @PostMapping("/onBoard")
    public ResponseEntity onBoardStaff(@RequestBody StaffRequest staffRequest, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        StaffResponse staffResponse = staffService.onBoardStaff(staffRequest, staffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/make-staff-as-manager")
    public ResponseEntity makeStaffAsManager(@RequestParam String staffEmail, @AuthenticationPrincipal UserDetails userDetails){
        String currStaffEmail = userDetails.getUsername();
        StaffResponse staffResponse = staffService.makeStaffAsManager(staffEmail, currStaffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @PutMapping("/offBoard")
    public ResponseEntity offBoardStaff(@RequestParam String staffEmail){
        StaffResponse staffResponse = staffService.offBoardStaff(staffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @GetMapping("/get-staff-by-staffEmail")
    public ResponseEntity getStaffByStaffEmail(@RequestParam String staffEmail){
        StaffResponse staffResponse = staffService.getStaffByStaffEmail(staffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestParam String staffEmail, @RequestParam String newPassword){
        StaffResponse staffResponse = staffService.resetPassword(staffEmail, newPassword);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @PutMapping("/change-role")
    public ResponseEntity changeRole(@RequestParam String staffEmail, @RequestParam Department department){
        StaffResponse staffResponse = staffService.changeRole(staffEmail, department.toString());
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @PutMapping("/make-staff-manager-access")
    public ResponseEntity makeStaffManagerAccess(@RequestParam String staffEmail){
        StaffResponse staffResponse = staffService.makeStaffManagerAccess(staffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @GetMapping("/all-current-staff")
    public ResponseEntity getAllCurrentStaff(){
        List<StaffResponse> staffResponse = staffService.getAllCurrentStaff();
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @GetMapping("/all-ex-staff")
    public ResponseEntity getAllExStaff(){
        List<StaffResponse> staffResponse = staffService.getAllExStaff();
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @GetMapping("/all-current-staff-by-department")
    public ResponseEntity getAllCurrentStaffByDepartment(@RequestParam Department department){
        List<StaffResponse> staffResponse = staffService.getAllCurrentStaffByDepartment(department.toString());
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @GetMapping("/all-ex-staff-by-department")
    public ResponseEntity getAllExStaffByDepartment(@RequestParam Department department){
        List<StaffResponse> staffResponse = staffService.getAllExStaffByDepartment(department.toString());
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }





    @GetMapping("/get-staff-salary-by-staffEmail")
    public ResponseEntity getStaffSalaryByStaffEmail(@RequestParam String staffEmail){
        double staffResponse = staffService.getStaffSalaryByStaffEmail(staffEmail);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }

    @PutMapping("/change-staff-salary-by-staffEmail")
    public ResponseEntity changeStaffSalaryByStaffEmail(@RequestParam String staffEmail, @RequestParam double newSalary){
        StaffResponse staffResponse = staffService.changeStaffSalaryByStaffEmail(staffEmail, newSalary);
        return new ResponseEntity(staffResponse, HttpStatus.OK);
    }


    @GetMapping("/create-transaction-for-payroll")
    public ResponseEntity createTransactionForPayroll(){
        List<TransactionResponse> response = staffService.createTransactionForPayroll();
        return new ResponseEntity(response, HttpStatus.OK);
    }



}
