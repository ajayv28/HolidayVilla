package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GuestResponse {

    String email;

    long phoneNumber;

    String name;

    Date dob;

    int age;

    Gender gender;

    boolean currentlyActiveBooking;
}
