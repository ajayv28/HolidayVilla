package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Staff;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MaintenanceResponse {

    String maintenanceId;

    Room room;

    Date dateOfMaintenance;

    String followups;

    Staff staff;

}
