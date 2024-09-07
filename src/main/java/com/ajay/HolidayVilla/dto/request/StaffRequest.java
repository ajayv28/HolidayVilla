package com.ajay.HolidayVilla.dto.request;

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
public class StaffRequest {

    String email;

    long phoneNumber;

    String password;

    String name;

    Date dob;

    int age;

    Gender gender;

    String role;

}
