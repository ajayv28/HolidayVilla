package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.MaintenanceRequest;
import com.ajay.HolidayVilla.dto.response.MaintenanceResponse;
import com.ajay.HolidayVilla.model.Maintenance;

import java.util.UUID;

public class MaintenanceTransformer {

    public static Maintenance maintenanceRequestToMaintenance(MaintenanceRequest maintenanceRequest){
        return Maintenance.builder()
                .followups(maintenanceRequest.getFollowups())
                .maintenanceId(String.valueOf(UUID.randomUUID()))
                .build();
    }

    public static MaintenanceResponse maintenanceToMaintenanceResponse(Maintenance maintenance){
        return MaintenanceResponse.builder()
                .dateOfMaintenance(maintenance.getDateOfMaintenance())
                .staffResponse(StaffTransformer.staffToStaffResponse(maintenance.getStaff()))
                .roomResponse(RoomTransformer.roomToRoomResponse(maintenance.getRoom()))
                .followups(maintenance.getFollowups())
                .maintenanceId(maintenance.getMaintenanceId())
                .build();
    }
}
