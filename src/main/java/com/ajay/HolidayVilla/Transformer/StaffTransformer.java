package com.ajay.HolidayVilla.Transformer;


import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.model.Staff;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StaffTransformer {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Staff staffRequestToStaff(StaffRequest staffRequest){
        return Staff.builder()
                .email(staffRequest.getEmail())
                .phoneNumber(staffRequest.getPhoneNumber())
                .gender(staffRequest.getGender())
                .password(passwordEncoder.encode(staffRequest.getPassword()))
                .name(staffRequest.getName())
                .dob(staffRequest.getDob())
                .employmentStatus(true)
                .role("ROLE_" + staffRequest.getDepartment().toString())
                .salary(staffRequest.getSalary())
                .department(staffRequest.getDepartment())
                .build();
    }

    public static StaffResponse staffToStaffResponse(Staff staff){
        return StaffResponse.builder()
                .email(staff.getEmail())
                .phoneNumber(staff.getPhoneNumber())
                .name(staff.getName())
                .dob(staff.getDob())
                .gender(staff.getGender())
                .employmentStatus(staff.isEmploymentStatus())
                .department(staff.getDepartment())
                .build();
    }
}
