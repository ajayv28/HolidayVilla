package com.ajay.HolidayVilla.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialRequest {

    String materialName;

    String supplierName;

    String supplierEmail;

    double price;

}
