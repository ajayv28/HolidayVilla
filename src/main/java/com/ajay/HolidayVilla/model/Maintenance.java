package com.ajay.HolidayVilla.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String maintenanceId;

    @ManyToOne
    @JoinColumn
    Staff staff;

    @ManyToOne
    @JoinColumn
    Room room;

    Date dateOfMaintenance;

    String followups;

}
