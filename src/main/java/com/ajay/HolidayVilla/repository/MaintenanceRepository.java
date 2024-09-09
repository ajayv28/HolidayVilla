package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Maintenance;
import com.sun.tools.javac.Main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    public Maintenance findByMaintenanceId(String maintenanceId);
}
