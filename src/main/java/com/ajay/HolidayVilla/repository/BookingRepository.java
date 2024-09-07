package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.model.Booking;
import com.ajay.HolidayVilla.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    //@Query(value = "select * from room where room_type= :type and id not in(select room_id from booking where room_type= :type and ((from_date<= :from and to_date>= :from) or (from_date<= :to and to_date>= :to))) order by rand() limit 1", nativeQuery = true)
    @Query(value = "select * from room where room_type= :type and id not in(select room_id from booking where room_type= :type and (from_date <= :to and to_date >= :from)) order by rand() limit 1", nativeQuery = true)
    public Room getAvailableRoom(Date from, Date to, RoomType type);

}
