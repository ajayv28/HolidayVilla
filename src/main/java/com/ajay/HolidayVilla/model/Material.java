package com.ajay.HolidayVilla.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true, nullable = false)
    String materialName;

    String supplierName;

    String supplierEmail;

    double price;

    @OneToMany(mappedBy = "material")
    List<MaterialRequisition> materialRequisitionList;
}
