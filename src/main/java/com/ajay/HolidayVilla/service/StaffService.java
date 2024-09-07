package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.StaffTransformer;
import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    public StaffResponse registerStaff(StaffRequest staffRequest) {
        Staff savedStaff = staffRepository.save(StaffTransformer.staffRequestToStaff(staffRequest));
        return StaffTransformer.staffToStaffResponse(savedStaff);
    }
}
