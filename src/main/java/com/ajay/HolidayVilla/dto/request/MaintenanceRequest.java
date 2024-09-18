package com.ajay.HolidayVilla.dto.request;

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
public class MaintenanceRequest {

    String roomNo;

    String followups;

}
