package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.Enum.RoomStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    RoomType roomType;

    int capacity;

    RoomStatus roomStatus;

    double farePerDay;

    @OneToMany(mappedBy = "room")
    List<Booking> bookings;

}
