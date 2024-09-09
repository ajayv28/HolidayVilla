package com.ajay.HolidayVilla.model;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.RequisitionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String requisitionId;

    @Enumerated(EnumType.STRING)
    Department department;

    Date expectingDeliveryDate;

    @CreationTimestamp
    Date dateOfRequisition;

    double requisitionQuantity;

    RequisitionStatus requisitionStatus;

    @ManyToOne
    @JoinColumn
    Material requisitionMaterial;

    @ManyToOne
    @JoinColumn
    Staff requisitionStaff;
}
