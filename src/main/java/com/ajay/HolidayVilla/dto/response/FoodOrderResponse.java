package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.FoodType;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Transaction;
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
public class FoodOrderResponse {

    String orderId;

    Date orderDateAndTime;

    FoodType foodType;

    Room room;

    Guest guest;

    Transaction transaction;

}
