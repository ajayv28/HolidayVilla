package com.ajay.HolidayVilla.dto.request;

import com.ajay.HolidayVilla.Enum.RoomStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequest {

    RoomType roomType;

    int capacity;

    double farePerDay;
}
