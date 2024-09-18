package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.RoomStatus;
import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.exception.AlreadyBookingOngoingException;
import com.ajay.HolidayVilla.exception.AlreadyRegisteredException;
import com.ajay.HolidayVilla.exception.NoOngoingBookingException;
import com.ajay.HolidayVilla.model.Booking;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.repository.BookingRepository;
import com.ajay.HolidayVilla.repository.GuestRepository;
import com.ajay.HolidayVilla.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.security.KeyStore;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;



    public RoomResponse addRoom(RoomRequest roomRequest) {
        if(roomRepository.findByRoomNo(roomRequest.getRoomNo()) != null)
            throw new AlreadyRegisteredException("ROOM NUMBER is already existing in the current facility. Kindly consider another unique number");
        Room savedRoom = roomRepository.save(RoomTransformer.roomRequestToRoom(roomRequest));
        return RoomTransformer.roomToRoomResponse(savedRoom);
    }


    public RoomResponse checkInWithBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if(booking.getBookingStatus().toString().equals("CANCELLED"))
            throw new NoOngoingBookingException("This booking has been cancelled already");
        if(booking.getBookingStatus().toString().equals("GUEST_IN_HOUSE"))
            throw new AlreadyBookingOngoingException("This booking has already been checked in");
        if(booking.getBookingStatus().toString().equals("GUEST_CHECKED_OUT"))
            throw new NoOngoingBookingException("This booking has been already checked out");

        Guest guest = booking.getGuest();
        Room room = booking.getRoom();

        guest.setCurrentlyInHouse(true);
        booking.setBookingStatus(BookingStatus.GUEST_IN_HOUSE);
        room.setRoomStatus(RoomStatus.OCCUPIED);

        guestRepository.save(guest);
        bookingRepository.save(booking);
        return RoomTransformer.roomToRoomResponse(roomRepository.save(room));
    }

    public RoomResponse checkOutWithBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if(booking.getBookingStatus().toString().equals("CANCELLED"))
            throw new NoOngoingBookingException("This booking has been cancelled already");
        if(booking.getBookingStatus().toString().equals("CONFIRMED"))
            throw new AlreadyBookingOngoingException("Guest has not even checked in");
        if(booking.getBookingStatus().toString().equals("GUEST_CHECKED_OUT"))
            throw new NoOngoingBookingException("Guest has already checked out");

        Guest guest = booking.getGuest();
        Room room = booking.getRoom();

        booking.setBookingStatus(BookingStatus.GUEST_CHECKED_OUT);
        guest.setCurrentlyActiveBooking(false);
        guest.setCurrentlyInHouse(false);
        room.setRoomStatus(RoomStatus.VACANT);

        //*************notify hk to clean

        bookingRepository.save(booking);
        guestRepository.save(guest);
        return RoomTransformer.roomToRoomResponse(roomRepository.save(room));
    }


    public RoomResponse earlyCheckOutWithBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        Guest guest = booking.getGuest();
        Room room = booking.getRoom();

        booking.setToDate(Date.valueOf(LocalDate.now()));
        booking.setBookingStatus(BookingStatus.GUEST_CHECKED_OUT);
        guest.setCurrentlyActiveBooking(false);
        guest.setCurrentlyInHouse(false);
        room.setRoomStatus(RoomStatus.VACANT);

        //*************notify hk to clean\



        bookingRepository.save(booking);
        guestRepository.save(guest);
        return RoomTransformer.roomToRoomResponse(roomRepository.save(room));
    }


    public RoomResponse changeRoomStatusByRoomNo(String roomNo, RoomStatus roomStatus) {
        Room room = roomRepository.findByRoomNo(roomNo);
        room.setRoomStatus(roomStatus);
        return RoomTransformer.roomToRoomResponse(roomRepository.save(room));
    }

    public List<RoomResponse> getAllTodayInHouseRoom() {
        List<Room> roomList = roomRepository.getAllTodayInHouseRoom();
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
    }

    public int getCountOfTodayInHouseRoom() {
        int count = roomRepository.getCountOfTodayInHouseRoom();
        return count;
    }


    public List<RoomResponse> getAllRoomByRoomStatus(String roomStatus) {
        List<Room> roomList = roomRepository.getAllRoomByRoomStatus(roomStatus);
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
    }



}
