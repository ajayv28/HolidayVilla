package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.model.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.reflect.DeclareParents;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionResponse {

    String transactionId;

    Date transactionDateAndTime;

    FundType fundType;

    double amount;

    String period;

    String comments;

    Department department;

    RoomResponse roomResponse;


    StaffResponse staffResponse;


    GuestResponse guestResponse;

    @JsonBackReference
    BookingResponse bookingResponse;

    @JsonBackReference
    MaterialRequisitionResponse materialRequisitionResponse;


    MaterialResponse materialResponse;

    @JsonBackReference
    FoodOrderResponse foodOrderResponse;
}
