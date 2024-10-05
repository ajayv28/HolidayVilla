package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.service.BookingService;
import com.ajay.HolidayVilla.service.CommonService;
import com.ajay.HolidayVilla.service.GuestService;
import com.ajay.HolidayVilla.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonController {

    @Autowired
    CommonService commonService;


    @GetMapping("/login-status")
    public String getLoginStatus(@AuthenticationPrincipal UserDetails userDetails){
        return commonService.getLoginStatus(userDetails);
    }

    @GetMapping("/logged-in-staff-name")
    public String getLoggedInStaffName(@AuthenticationPrincipal UserDetails userDetails){
        return commonService.getLoggedInStaffName(userDetails);
    }

    @GetMapping("/logged-in-guest-name")
    public String getLoggedInGuestName(@AuthenticationPrincipal UserDetails userDetails){
        return commonService.getLoggedInGuestName(userDetails);
    }

    @GetMapping("/logged-in-staff-detail")
    public StaffResponse getLoggedInStaffDetail(@AuthenticationPrincipal UserDetails userDetails){
        return commonService.getLoggedInStaffDetail(userDetails);
    }

    @GetMapping("/logged-in-guest-detail")
    public GuestResponse getLoggedInGuestDetail(@AuthenticationPrincipal UserDetails userDetails){
        return commonService.getLoggedInGuestDetail(userDetails);
    }
}
