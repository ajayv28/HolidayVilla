package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Transformer.BookingTransformer;
import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.exception.*;
import com.ajay.HolidayVilla.model.*;
import com.ajay.HolidayVilla.repository.BookingRepository;
import com.ajay.HolidayVilla.repository.CouponRepository;
import com.ajay.HolidayVilla.repository.GuestRepository;
import com.ajay.HolidayVilla.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.sql.Date;
import java.time.Period;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    RoomRepository roomRepository;

///check with room entity********************************************************

    public List<RoomResponse> getAllTodayInhouseGuestRooms() {
        Date currDate = Date.valueOf(LocalDate.now());
        List<Room> roomList = roomRepository.getAllTodayInhouseGuestRooms(currDate);
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
        }
    

    public int getCountOfTodayInhouseBooking() {
        Date currDate = Date.valueOf(LocalDate.now());
        int count = roomRepository.getCountOfTodayInhouseBooking(currDate);
            return count;
        }

    
    public List<RoomResponse> getAllRoomByRoomStatus(String roomStatus) {
        List<Room> roomList = roomRepository.getAllRoomByRoomStatus(roomStatus);
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
        }

//**************************************************************
    
    public BookingResponse createBooking(BookingRequest bookingRequest, String guestEmail) {

        Guest currGuest = guestRepository.findByEmail(guestEmail);
        if(currGuest.isCurrentlyActiveBooking()==true)
            throw new AlreadyBookingOngoingException("Already an booking is schedules with this user. Only one booking possible");

        boolean isOutOfService = false;

        Room currRoom = bookingRepository.getAvailableRoom(bookingRequest.getFromDate(), bookingRequest.getToDate(), bookingRequest.getRoomType());
        if(currRoom == null) {
            currRoom = bookingRepository.getAvailableRoomIncludingOOS(bookingRequest.getFromDate(), bookingRequest.getToDate(), bookingRequest.getRoomType());
            isOutOfService = true;
        }
        if(currRoom == null)
            throw new RoomNotAvailableBetweenGivenDates("Sorry we are fully booked in given dates. Please try to change dates and try again");

        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest);

        double offerPercent = 0.0;

        if(bookingRequest.getCouponCode().length()>0) {
            Coupon coupon = couponRepository.findByCouponCode(bookingRequest.getCouponCode());
            if (coupon == null)
                throw new CouponNotExistException("Sorry, no coupon exist with given Coupon Code");
            if (coupon.getQuantityRemaining() == 0)
                throw new CouponExpiredException("Sorry given coupon code expired or quantity exhausted");

            offerPercent = coupon.getOfferPercentage();
            coupon.setQuantityRemaining(coupon.getQuantityRemaining() - 1);
            couponRepository.save(coupon);
        }
            Period period = Period.between(booking.getFromDate().toLocalDate(), booking.getToDate().toLocalDate());
            double totalDays= period.getDays()+1;
            double totalFare = currRoom.getFarePerDay()*totalDays;
            double offerAmount = (totalFare * offerPercent) / 100;
            booking.setTotalFare(totalFare-offerAmount);
            booking.setBookingStatus(BookingStatus.CONFIRMED);

            booking.setGuest(currGuest);
            booking.setRoom(currRoom);

            currRoom.getBookingList().addLast(booking);
            currGuest.getBookings().addLast(booking);
            currGuest.setCurrentlyActiveBooking(true);

            //******* send mail to HSK, FO that booking made on OOS room*******

            roomRepository.save(currRoom);
            guestRepository.save(currGuest);

            return BookingTransformer.bookingToBookingResponse(booking);

    }


    public BookingResponse cancelBooking(String guestEmail) {
        Guest currGuest = guestRepository.findByEmail(guestEmail);
        if(currGuest.isCurrentlyActiveBooking()==false)
            throw new NoOngoingBookingException("Sorry, there is no upcoming booking found for you");

        Booking booking = currGuest.getBookings().getLast();

        if(booking.getBookingStatus().toString().equals("GUEST_IN_HOUSE"))
            throw new CancellationNotAllowedException("Guest already checked in. Cancellation not allowed");

        if(booking.getBookingStatus().toString().equals("GUEST_CHECKED_OUT"))
            throw new CancellationNotAllowedException("Booking already completed successfully and Guest checked out. Cancellation not allowed");

        if(booking.getBookingStatus().toString().equals("CANCELLED"))
            throw new CancellationNotAllowedException("This booking has already been cancelled. So Cancellation not allowed");


        booking.setBookingStatus(BookingStatus.CANCELLED);
        currGuest.setCurrentlyActiveBooking(false);

        guestRepository.save(currGuest);
        return BookingTransformer.bookingToBookingResponse(bookingRepository.save(booking));
    }


    public BookingResponse cancelBookingByBookingId(String bookingId) {
        Booking currBooking = bookingRepository.findByBookingId(bookingId);
        Guest currGuest = currBooking.getGuest();

        if(currBooking.getBookingStatus().toString().equals("GUEST_IN_HOUSE"))
            throw new CancellationNotAllowedException("Guest already checked in. Cancellation not allowed");

        if(currBooking.getBookingStatus().toString().equals("GUEST_CHECKED_OUT"))
            throw new CancellationNotAllowedException("Booking already completed successfully and Guest checked out. Cancellation not allowed");

        if(currBooking.getBookingStatus().toString().equals("CANCELLED"))
            throw new CancellationNotAllowedException("This booking has already been cancelled. So Cancellation not allowed");


        currBooking.setBookingStatus(BookingStatus.CANCELLED);
        currGuest.setCurrentlyActiveBooking(false);

        guestRepository.save(currGuest);
        return BookingTransformer.bookingToBookingResponse(bookingRepository.save(currBooking));
    }

    public BookingResponse changeBookingRoomIfPossible(String bookingId) {
    }

    public BookingResponse changeBookingRoomWithUpgradingIfPossible(String bookingId) {
    }

    public BookingResponse getBookingByBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        return BookingTransformer.bookingToBookingResponse(booking);
    }

    public List<BookingResponse> getAllUpcomingArrivalBooking() {
        List<Booking> bookingList = bookingRepository.getAllUpcomingArrivalBooking();
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllUpcomingArrivalBookingByRoomNo(String roomNo) {
        List<Booking> bookingList = bookingRepository.getAllUpcomingArrivalBookingByRoomNo(roomNo);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllUpcomingArrivalBookingByGuestEmail(String guestEmail) {
        List<Booking> bookingList = bookingRepository.getAllUpcomingArrivalBookingByGuestEmail(guestEmail);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllBookingBetweenDates(Date fromdate, Date toDate) {
        List<Booking> bookingList = bookingRepository.getAllBookingBetweenDates(fromdate, toDate);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllCheckedOutBookingBetweenDates(Date fromDate, Date toDate) {
        List<Booking> bookingList = bookingRepository.getAllCheckedOutBookingBetweenDates(fromdate, toDate);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllCheckedOutBookingByGuestEmail(String guestEmail) {
        List<Booking> bookingList = bookingRepository.getAllCheckedOutBookingByGuestEmail(guestEmail);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllCancelledBookingBetweenDates(Date fromDate, Date toDate) {
        List<Booking> bookingList = bookingRepository.getAllCancelledBookingBetweenDates(fromdate, toDate);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllCancelledBookingByGuestEmail(String guestEmail) {
        List<Booking> bookingList = bookingRepository.getAllCancelledBookingByGuestEmail(guestEmail);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }
    


    public List<BookingResponse> getAllUpcomingArrivalStayMoreThanNDays(int n) {
        List<Booking> bookingList = bookingRepository.getAllUpcomingArrivalStayMoreThanNDays(n);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }

    public List<BookingResponse> getAllBookingOccupiedOnGivenDate(Date date) {
        List<Booking> bookingList = bookingRepository.getAllBookingOccupiedOnGivenDate(date);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }
}
