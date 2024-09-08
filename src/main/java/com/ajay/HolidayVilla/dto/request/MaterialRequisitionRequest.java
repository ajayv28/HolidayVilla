package com.ajay.HolidayVilla.dto.request;

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
public class MaterialRequisitionRequest {

    Department department;

    Date expectingDeliveryDate;

    double requisitionQuantity;

    Material requisitionMaterial;

}
