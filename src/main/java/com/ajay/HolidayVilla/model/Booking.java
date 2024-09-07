package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String bookingId;

    RoomType roomType;

    Date fromDate;

    Date toDate;

    @CreationTimestamp
    java.util.Date bookingDate;

    boolean breakfastIncluded;

    double totalFare;

    BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn
    Room room;

    @ManyToOne
    @JoinColumn
    Guest guest;
}
