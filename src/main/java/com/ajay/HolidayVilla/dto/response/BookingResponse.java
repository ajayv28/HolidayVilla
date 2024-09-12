package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.RoomType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookingResponse {

    String bookingId;

    RoomType roomType;

    Date fromDate;

    Date toDate;

    double totalFare;

}
