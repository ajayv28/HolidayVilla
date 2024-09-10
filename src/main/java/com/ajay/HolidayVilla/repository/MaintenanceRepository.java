package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Maintenance;
import com.sun.tools.javac.Main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    public Maintenance findByMaintenanceId(String maintenanceId);

    public List<Maintenance> allVacantRoomsDueForMaintenance();

    public List<Maintenance> allOccupiedRoomDueForMaintenanceButOccupiedForMoreThan30UpcomingDays();

    public List<Maintenance> allRoomsWithFollowups();

    public List<Maintenance> allMaintenanceWithFollowupsByRoomNo(String roomNo);

    public List<Maintenance> allMaintenanceBetweenDates(Date fromDate, Date toDate);

    public List<Maintenance> allMaintenanceByStaffBetweenDates(Date fromDate, Date toDate, String staffEmail);
}
