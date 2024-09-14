package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class StaffResponse {

    String email;

    long phoneNumber;

    String name;

    Department department;

    boolean employmentStatus;

    Date dob;

    Gender gender;
}
