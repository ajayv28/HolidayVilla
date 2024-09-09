package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    long phoneNumber;

    String password;

    String name;

    Date dob;

    int age;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String role;

    @CreationTimestamp
    Date joiningDate;

    @OneToMany(mappedBy = "staff")
    List<Maintenance> maintenanceList;

    @OneToMany(mappedBy = "staff")
    List<MaterialRequisition> materialRequisitionList;
}
