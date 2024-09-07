package com.ajay.HolidayVilla.dto.request;

import com.ajay.HolidayVilla.Enum.Gender;
import com.ajay.HolidayVilla.model.Booking;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestRequest {

    String email;

    long phoneNumber;

    String password;

    String name;

    Date dob;

    int age;

    Gender gender;

}
