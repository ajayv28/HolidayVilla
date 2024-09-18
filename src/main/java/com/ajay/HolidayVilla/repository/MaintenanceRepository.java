package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Maintenance;
import com.ajay.HolidayVilla.model.Room;
import com.sun.tools.javac.Main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    
    public Maintenance findByMaintenanceId(String maintenanceId);

    //@Query(value="select * from room where room_status in ('OUT_OF_SERVICE','OUT_OF_ORDER','VACANT') and (last_maintenance_done + INTERVAL 30 DAY) > :today", nativeQuery=true)
    @Query("SELECT r FROM Room r WHERE r.roomStatus IN ('OUT_OF_SERVICE', 'OUT_OF_ORDER', 'VACANT') AND r.lastMaintenanceDone < :date")
    public List<Room> allVacantRoomsDueForMaintenance(java.util.Date date);

    //@Query(value="select * from room where id in(select distinct room_id from maintenance where LENGTH(followups) > 0)", nativeQuery=true)
    @Query("SELECT r FROM Room r WHERE r.id IN (SELECT DISTINCT m.room.id FROM Maintenance m WHERE LENGTH(m.followups) > 0)")
    public List<Room> allRoomsWithFollowups();

    @Query(value="select * from maintenance where room_id =(select id from room where room_no =:roomNo) and LENGTH(followups) > 0", nativeQuery=true)
    public List<Maintenance> allMaintenanceWithFollowupsByRoomNo(String roomNo);

    @Query(value="select * from maintenance where date_of_maintenance between :fromDate and :toDate", nativeQuery=true)
    public List<Maintenance> allMaintenanceBetweenDates(Date fromDate, Date toDate);

    @Query(value="select * from maintenance where staff_id =(select id from staff where email =:staffEmail) and (date_of_maintenance between :fromDate and :toDate)", nativeQuery=true)
    public List<Maintenance> allMaintenanceByStaffBetweenDates(Date fromDate, Date toDate, String staffEmail);
}
