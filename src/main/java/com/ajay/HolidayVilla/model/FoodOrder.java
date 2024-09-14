package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.FoodType;
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
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true, nullable = false)
    String orderId;

    double amount;

    @CreationTimestamp
    Date orderDateAndTime;

    @Enumerated(EnumType.STRING)
    FoodType foodType;

    @ManyToOne
    @JoinColumn
    Room room;

    @ManyToOne
    @JoinColumn
    Guest guest;

    @OneToOne
    Transaction transaction;
}
