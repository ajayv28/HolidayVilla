package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.FundType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String transactionId;

    @CreationTimestamp
    Date transactionDateAndTime;

    @Enumerated(EnumType.STRING)
    FundType fundType;

    @ManyToOne
    @JoinColumn
    Room room;

    @ManyToOne
    @JoinColumn
    Guest guest;

    @OneToOne
    @JoinColumn
    Booking booking;

    @OneToOne
    @JoinColumn
    MaterialRequisition materialRequisition;

    @ManyToOne
    @JoinColumn
    Material material;

    @OneToOne
    @JoinColumn
    FoodOrder foodOrder;

}
