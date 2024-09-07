package com.ajay.HolidayVilla.Transformer;


import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.model.Staff;

public class StaffTransformer {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Staff staffRequestToStaff(StaffRequest staffRequest){
        return Staff.builder()
                .email(staffRequest.getEmail())
                .phoneNumber(staffRequest.getPhoneNumber())
                .gender(staffRequest.getGender())
                .age(staffRequest.getAge())
                .password(passwordEncoder.encode(staffRequest.getPassword()))
                .name(staffRequest.getName())
                .dob(staffRequest.getDob())
                .role("ROLE_" + staffRequest.getRole().toUpperCase())
                .build();
    }

    public static StaffResponse staffToStaffResponse(Staff staff){
        return StaffResponse.builder()
                .email(staff.getEmail())
                .phoneNumber(staff.getPhoneNumber())
                .name(staff.getName())
                .age(staff.getAge())
                .dob(staff.getDob())
                .gender(staff.getGender())
                .build();
    }
}
