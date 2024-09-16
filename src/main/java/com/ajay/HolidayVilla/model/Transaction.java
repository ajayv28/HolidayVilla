package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true, nullable = false)
    String transactionId;

    @CreationTimestamp
    Date transactionDateAndTime;

    @Enumerated(EnumType.STRING)
    FundType fundType;

    String period;

    String comments;

    double amount;

    //we will manually map below based on requirement in service layers wherever money transaction takes place

    @Enumerated(EnumType.STRING)
    Department department;

    @ManyToOne
    @JoinColumn
    Room room;

    @ManyToOne
    @JoinColumn
    Guest guest;

    @ManyToOne
    @JoinColumn
    Staff staff;


    @ManyToOne
    @JoinColumn(unique = false)
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
