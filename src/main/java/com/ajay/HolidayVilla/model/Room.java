package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.Enum.RoomStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
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

    @Column(unique = true, nullable = false)
    String roomNO;

    @Enumerated(EnumType.STRING)
    RoomType roomType;

    int capacity;

    @Enumerated(EnumType.STRING)
    RoomStatus roomStatus;

    double farePerDay;

    @CreationTimestamp
    Date lastMaintenanceDone;

    @OneToMany(mappedBy = "room")
    List<Booking> bookingList;

    @OneToMany(mappedBy = "room")
    List<Maintenance> maintenanceHistory;

    @OneToMany(mappedBy = "room")
    List<Transaction> transactionList;

    @OneToMany(mappedBy = "room")
    List<FoodOrder> foodOrderList;
}
