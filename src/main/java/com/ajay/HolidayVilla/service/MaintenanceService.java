package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.MaintenanceTransformer;
import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.exception.UserNotExistException;
import com.ajay.HolidayVilla.model.Maintenance;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.repository.MaintenanceRepository;
import com.ajay.HolidayVilla.repository.RoomRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
        if(room==null)
            throw new UserNotExistException("Sorry no room exist with given number");
        maintenance.setRoom(room);
        maintenance.setStaff(staff);
        //maintenance.setDateOfMaintenance(Date.valueOf(LocalDate.now()));

        java.util.Date currentDate = new java.util.Date();

        room.getMaintenanceHistory().add(maintenance);
        room.setLastMaintenanceDone(currentDate);
        staff.getMaintenanceList().add(maintenance);

        staffRepository.save(staff);
        roomRepository.save(room);

        return MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenanceRepository.save(maintenance));

    }

    public MaintenanceResponse updateFollowupsByMaintenanceId(String maintenanceId, String newFollowup) {
        Maintenance maintenance = maintenanceRepository.findByMaintenanceId(maintenanceId);
        maintenance.setFollowups(newFollowup.length()==0 ? null : newFollowup);
        return MaintenanceTransformer.maintenanceToMaintenanceResponse(maintenanceRepository.save(maintenance));
    }

    public List<RoomResponse> allVacantRoomsDueForMaintenance() {
        java.util.Date currentDate = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DAY_OF_MONTH, -30);

        java.util.Date newDate = calendar.getTime();

        List<Room> roomList = maintenanceRepository.allVacantRoomsDueForMaintenance(newDate);
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for(Room room: roomList)
            roomResponseList.add(RoomTransformer.roomToRoomResponse(room));

        return roomResponseList;
    }


    public List<RoomResponse> allRoomsWithFollowups() {
        List<Room> maintenanceList = maintenanceRepository.allRoomsWithFollowups();
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for(Room room: maintenanceList)
            roomResponseList.add(RoomTransformer.roomToRoomResponse(room));

        return roomResponseList;
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
