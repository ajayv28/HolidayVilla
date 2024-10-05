package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.GuestTransformer;
import com.ajay.HolidayVilla.Transformer.StaffTransformer;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.repository.GuestRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    GuestRepository guestRepository;

    public String getLoginStatus(UserDetails userDetails) {
        if(userDetails == null)
            return "none";
        else if(guestRepository.findByEmail(userDetails.getUsername()) != null && guestRepository.findByEmail(userDetails.getUsername()).getRole().equals("ROLE_GUEST"))
            return "GUEST";
        else
            return "STAFF";
    }

    public String getLoggedInStaffName(UserDetails userDetails) {
        return staffRepository.findByEmail(userDetails.getUsername()).getName();
    }

    public String getLoggedInGuestName(UserDetails userDetails) {
        return guestRepository.findByEmail(userDetails.getUsername()).getName();
    }

    public StaffResponse getLoggedInStaffDetail(UserDetails userDetails) {
        return StaffTransformer.staffToStaffResponse(staffRepository.findByEmail(userDetails.getUsername()));
    }

    public GuestResponse getLoggedInGuestDetail(UserDetails userDetails) {
        return GuestTransformer.guestToGuestResponse(guestRepository.findByEmail(userDetails.getUsername()));
    }
}
