package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.Department;
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

    @Enumerated(EnumType.STRING)
    Department department;

    boolean employmentStatus;

    Date dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String role;

    double salary;

    @CreationTimestamp
    Date joiningDate;

    @OneToMany(mappedBy = "staff")
    List<Maintenance> maintenanceList;

    @OneToMany(mappedBy = "requisitionStaff")
    List<MaterialRequisition> materialRequisitionList;

    @OneToMany(mappedBy = "staff")
    List<Transaction> transactionList;
}
