package com.ajay.HolidayVilla.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MaterialResponse {

    String materialName;

    String supplierName;

    String supplierEmail;

    double price;

}
