package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.model.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
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

    Room room;

    Guest guest;

    Booking booking;

    MaterialRequisition materialRequisition;

    Material material;

    FoodOrder foodOrder;
}
