package com.ajay.HolidayVilla.dto.request;

import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {


    RoomType roomType;

    Date fromDate;

    Date toDate;

    boolean breakfastIncluded;

    String couponCode;

}
