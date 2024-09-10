package com.ajay.HolidayVilla.dto.response;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.model.Material;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MaterialRequisitionResponse {

    String requisitionId;

    Department department;

    Date expectingDeliveryDate;

    Date dateOfRequisition;

    double requisitionQuantity;

    Material requisitionMaterial;

}