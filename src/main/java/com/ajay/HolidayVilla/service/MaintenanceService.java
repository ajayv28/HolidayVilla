package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.MaintenanceTransformer;
import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.model.Maintenance;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.repository.MaintenanceRepository;
import com.ajay.HolidayVilla.repository.RoomRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    RoomRepository roomRepository;

    public MaintenanceResponse postMaintenance(MaintenanceRequest maintenanceRequest, String staffEmail) {
        Maintenance maintenance = MaintenanceTransformer.maintenanceRequestToMaintenance(maintenanceRequest);
        Staff staff = staffRepository.findByEmail(staffEmail);
        Room room = roomRepository.findByRoomNo(maintenanceRequest.getRoomNo());
        maintenance.setRoom(room);
        maintenance.setStaff(staff);

        room.getMaintenanceHistory().add(maintenance);
        room.setLastMaintenanceDone(maintenance.getDateOfMaintenance());
        staff.getMaintenanceList().add(maintenance);

        staffRepository.save(staff);
        roomRepository.save(room);

        return MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenanceRepository.save(maintenance));

    }
}
