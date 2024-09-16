package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    BookingStatus bookingStatus;

    String couponCode;

    RoomResponse roomResponse;

    GuestResponse guestResponse;

    String transactionId;

}
