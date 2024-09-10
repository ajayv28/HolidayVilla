package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    public Room findByRoomNo(String roomNo);

    @Query(value="select * from room where room_status = 'OCCUPIED'", nativeQuery=true)
    public List<Room> getAllTodayInHouseRoom();

    @Query(value="select count(*) from room where room_status = 'OCCUPIED'", nativeQuery=true)
    public int getCountOfTodayInHouseRoom();

    @Query(value="select * from room where room_status = :roomStatus", nativeQuery=true)
    public List<Room> getAllRoomByRoomStatus(String roomStatus);
}
