package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.Enum.RoomType;
import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.model.Booking;
import com.ajay.HolidayVilla.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    //@Query(value = "select * from room where room_type= :type and id not in(select room_id from booking where room_type= :type and ((from_date<= :from and to_date>= :from) or (from_date<= :to and to_date>= :to))) order by rand() limit 1", nativeQuery = true)
    @Query(value = "select r from Room r where r.roomType= :type and r.roomStatus not in ('OUT_OF_ORDER', 'OUT_OF_SERVICE') and r.id not in (select b.room.id from Booking b where b.roomType= :type and b.bookingStatus <> 'CANCELLED' and (b.fromDate <= :to and b.toDate >= :from)) order by function('RAND') limit 1")
    public Room getAvailableRoom(Date from, Date to, RoomType type);

    @Query(value = "select r from Room r where r.roomType= :type and r.roomStatus <> 'OUT_OF_ORDER' and r.id not in (select b.room.id from Booking b where b.roomType= :type and b.bookingStatus <> 'CANCELLED' and (b.fromDate <= :to and b.toDate >= :from)) order by function('RAND') limit 1")
    public Room getAvailableRoomIncludingOOS(Date from, Date to, RoomType type);

    public Booking findByBookingId(String bookingId);

    @Query(value = "select * from booking where guest_id = (select id from guest where email = :guestEmail)", nativeQuery=true)
    public List<Booking> getAllBookingByGuestEmail(String guestEmail);

    @Query(value = "select * from booking where from_date > curdate()", nativeQuery=true)
    public List<Booking> getAllUpcomingArrivalBooking();

    @Query(value = "select * from booking where from_date > curdate() and room_id = (select id from room where room_no = :roomNo)", nativeQuery=true)
    public List<Booking> getAllUpcomingArrivalBookingByRoomNo(String roomNo);

    @Query(value = "select * from booking where from_date > curdate() and guest_id = (select id from guest where email = :guestEmail)", nativeQuery=true)
    public List<Booking> getAllUpcomingArrivalBookingByGuestEmail(String guestEmail);

    @Query(value="select * from booking where from_date <= :toDate and to_date >= :fromDate", nativeQuery=true)
    public List<Booking> getAllBookingBetweenDates(Date fromDate, Date toDate);

    @Query(value="select * from booking where booking_status = 'GUEST_CHECKED_OUT' and from_date <= :toDate and to_date >= :fromDate", nativeQuery=true)
    public List<Booking> getAllCheckedOutBookingBetweenDates(Date fromDate, Date toDate);

    @Query(value="select * from booking where guest_id =(select id from guest where email = :guestEmail) and (booking_status = 'GUEST_CHECKED_OUT')", nativeQuery=true)
    public List<Booking> getAllCheckedOutBookingByGuestEmail(String guestEmail);

    @Query(value="select * from booking where booking_status = 'CANCELLED' and from_date <= :toDate and to_date >= :fromDate", nativeQuery=true)
    public List<Booking> getAllCancelledBookingBetweenDates(Date fromDate, Date toDate);

    @Query(value="select * from booking where guest_id =(select id from guest where email = :guestEmail) and (booking_status = 'CANCELLED')", nativeQuery=true)
    public List<Booking> getAllCancelledBookingByGuestEmail(String guestEmail);

    @Query(value="select * from booking where from_date > curdate() and datediff(to_date, from_date) >= :n", nativeQuery=true)
    public List<Booking> getAllUpcomingArrivalStayMoreThanNDays(int n);

    @Query(value="select * from booking where from_date <= :date and to_date >= :date", nativeQuery=true)
    public List<Booking> getAllBookingOccupiedOnGivenDate(Date date);


}