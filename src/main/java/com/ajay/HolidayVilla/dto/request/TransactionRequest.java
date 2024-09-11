package com.ajay.HolidayVilla.dto.request;

import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.model.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    FundType fundType;

}
