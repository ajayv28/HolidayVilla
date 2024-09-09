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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    public MaintenanceResponse updateFollowupsByMaintenanceId(String maintenanceId, String newFollowup) {
        Maintenance maintenance = maintenanceRepository.findByMaintenanceId(maintenanceId);
        maintenance.setFollowups(newFollowup);
        return MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenanceRepository.save(maintenance));
    }

    public List<MaintenanceResponse> allVacantRoomsDueForMaintenance() {
        List<Maintenance> maintenanceList = maintenanceRepository.allVacantRoomsDueForMaintenance();
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }

    public List<MaintenanceResponse> allOccupiedRoomDueForMaintenanceButOccupiedForMoreThan30UpcomingDays() {
        List<Maintenance> maintenanceList = maintenanceRepository.allOccupiedRoomDueForMaintenanceButOccupiedForMoreThan30UpcomingDays();
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }

    public List<MaintenanceResponse> allRoomsWithFollowups() {
        List<Maintenance> maintenanceList = maintenanceRepository.allRoomsWithFollowups();
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }

    public List<MaintenanceResponse> allMaintenanceWithFollowupsByRoomNo(String roomNo) {
        List<Maintenance> maintenanceList = maintenanceRepository.allMaintenanceWithFollowupsByRoomNo(roomNo);
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }

    public List<MaintenanceResponse> allMaintenanceBetweenDates(Date fromDate, Date toDate) {
        List<Maintenance> maintenanceList = maintenanceRepository.allMaintenanceBetweenDates(fromDate, toDate);
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }

    public List<MaintenanceResponse> allMaintenanceByStaffBetweenDates(Date fromDate, Date toDate, String staffEmail) {
        List<Maintenance> maintenanceList = maintenanceRepository.allMaintenanceByStaffBetweenDates(fromDate, toDate, staffEmail);
        List<MaintenanceResponse> maintenanceResponseList = new ArrayList<>();
        for(Maintenance maintenance: maintenanceList)
            maintenanceResponseList.add(MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenance));

        return maintenanceResponseList;
    }
}
