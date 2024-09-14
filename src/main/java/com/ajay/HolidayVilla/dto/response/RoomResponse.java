package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.RoomStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoomResponse {

    String roomNo;

    RoomType roomType;

    int capacity;

    RoomStatus roomStatus;

    double farePerDay;
}
