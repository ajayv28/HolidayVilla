package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.BookingStatus;
import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.Transformer.BookingTransformer;
import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.Transformer.TransactionTransformer;
import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.exception.*;
import com.ajay.HolidayVilla.model.*;
import com.ajay.HolidayVilla.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    TransactionRepository transactionRepository;

    
    public BookingResponse createBooking(BookingRequest bookingRequest, String guestEmail) {

        Guest currGuest = guestRepository.findByEmail(guestEmail);
        if (currGuest.isCurrentlyActiveBooking() == true)
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

        if(bookingRequest.getCouponCode()!= null && bookingRequest.getCouponCode().length()>0) {
            Coupon coupon = couponRepository.findByCouponCode(bookingRequest.getCouponCode());
            if (coupon == null)
                throw new CouponNotExistException("Sorry, no coupon exist with given Coupon Code");
            if (coupon.getQuantityRemaining() == 0)
                throw new CouponExpiredException("Sorry given coupon code expired or quantity exhausted");

            offerPercent = coupon.getOfferPercentage();
            coupon.setQuantityRemaining(coupon.getQuantityRemaining() - 1);
            couponRepository.save(coupon);
            booking.setCouponCode(coupon.getCouponCode());
        }

            double totalDays = ChronoUnit.DAYS.between(booking.getFromDate().toLocalDate(), booking.getToDate().toLocalDate()) + 1;

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
            //*********send confrimation mail to guest

            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setFundType(FundType.CREDIT);
            Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
            transaction.setDepartment(Department.ROOM_DIVISION);
            transaction.setGuest(currGuest);
            transaction.setRoom(currRoom);
            transaction.setAmount(totalFare-offerAmount);
            transaction.setComments("New Room Booking");
            transactionRepository.save(transaction);

            currGuest.getTransactionList().add(transaction);
            currRoom.getTransactionList().add(transaction);
            roomRepository.save(currRoom);
            guestRepository.save(currGuest);
            booking.getTransaction().add(transaction);
            Booking savedBooking = bookingRepository.save(booking);
            transaction.setBooking(savedBooking);
            transactionRepository.save(transaction);
            return BookingTransformer.bookingToBookingResponse(savedBooking);


    }




    public BookingResponse changeBookingRoomIfPossible(String bookingId) {
        Booking currBooking = bookingRepository.findByBookingId(bookingId);
        Guest currGuest = currBooking.getGuest();

        if(currBooking.getBookingStatus().toString().equals("GUEST_IN_HOUSE"))
            throw new CancellationNotAllowedException("Guest already checked in. Cancellation not allowed");

        if(currBooking.getBookingStatus().toString().equals("GUEST_CHECKED_OUT"))
            throw new CancellationNotAllowedException("Booking already completed successfully and Guest checked out. Cancellation not allowed");

        if(currBooking.getBookingStatus().toString().equals("CANCELLED"))
            throw new CancellationNotAllowedException("This booking has already been cancelled. So Cancellation not allowed");


        boolean isOutOfService = false;

        Room currRoom = bookingRepository.getAvailableRoom(currBooking.getFromDate(), currBooking.getToDate(), currBooking.getRoomType());
        if(currRoom == null) {
            currRoom = bookingRepository.getAvailableRoomIncludingOOS(currBooking.getFromDate(), currBooking.getToDate(), currBooking.getRoomType());
            isOutOfService = true;
        }
        if(currRoom == null)
            throw new RoomNotAvailableBetweenGivenDates("Sorry no rooms available to change booking");

        Booking newBooking = new Booking();
        newBooking.setRoomType(currBooking.getRoomType());
        newBooking.setFromDate(currBooking.getFromDate());
        newBooking.setToDate(currBooking.getToDate());
        newBooking.setCouponCode(currBooking.getCouponCode());
        newBooking.setBookingId(String.valueOf(UUID.randomUUID()));

        double offerPercent = 0.0;

        if(newBooking.getCouponCode()!=null && newBooking.getCouponCode().length()>0) {
            Coupon newCoupon = couponRepository.findByCouponCode(newBooking.getCouponCode());
            offerPercent = newCoupon.getOfferPercentage();
            newBooking.setCouponCode(newCoupon.getCouponCode());
        }
        double totalDays = ChronoUnit.DAYS.between(newBooking.getFromDate().toLocalDate(), newBooking.getToDate().toLocalDate()) + 1;

        double totalFare = currRoom.getFarePerDay()*totalDays;
        double offerAmount = (totalFare * offerPercent) / 100;
        newBooking.setTotalFare(totalFare-offerAmount);
        newBooking.setBookingStatus(BookingStatus.CONFIRMED);

        newBooking.setGuest(currGuest);
        newBooking.setRoom(currRoom);

        currRoom.getBookingList().addLast(newBooking);
        currGuest.getBookings().addLast(newBooking);
        currGuest.setCurrentlyActiveBooking(true);

        //******* send mail to HSK, FO that booking made on OOS room*******
        //*********send changing  mail to guest

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFundType(FundType.FREE);
        Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
        transaction.setDepartment(Department.ROOM_DIVISION);
        transaction.setGuest(newBooking.getGuest());
        transaction.setRoom(currRoom);
        transaction.setComments("ROOM CHANGE. Booking ID - " + bookingId );
        transaction.setAmount(0.0);
        transactionRepository.save(transaction);

        currBooking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(currBooking);

        currGuest.getTransactionList().add(transaction);
        newBooking.getRoom().getTransactionList().add(transaction);
        guestRepository.save(currGuest);
        roomRepository.save(currRoom);
        newBooking.getTransaction().add(transaction);

        Booking savedBooking = bookingRepository.save(newBooking);
        transaction.setBooking(savedBooking);
        transactionRepository.save(transaction);
        return BookingTransformer.bookingToBookingResponse(savedBooking);

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

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFundType(FundType.DEBIT);
        Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
        transaction.setDepartment(Department.ROOM_DIVISION);
        transaction.setGuest(currGuest);
        transaction.setRoom(currBooking.getRoom());
        transaction.setAmount(currBooking.getTotalFare());
        transaction.setComments("Refund - Room Booking Cancellation");
        transactionRepository.save(transaction);

        currGuest.getTransactionList().add(transaction);
        currBooking.getRoom().getTransactionList().add(transaction);
        guestRepository.save(currGuest);
        roomRepository.save(currBooking.getRoom());
        currBooking.getTransaction().add(transaction);
        //****send mail to guest

        Booking savedBooking = bookingRepository.save(currBooking);
        transaction.setBooking(savedBooking);
        transactionRepository.save(transaction);
        return BookingTransformer.bookingToBookingResponse(savedBooking);
    }




    public BookingResponse cancelLastBooking(String guestEmail) {
        Guest currGuest = guestRepository.findByEmail(guestEmail);
        if(currGuest == null)
            throw new UserNotExistException("Sorry no guest exist with given email id");

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



        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFundType(FundType.DEBIT);
        Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
        transaction.setDepartment(Department.ROOM_DIVISION);
        transaction.setGuest(currGuest);
        transaction.setRoom(booking.getRoom());
        transaction.setAmount(booking.getTotalFare());
        transaction.setComments("Refund - Room Booking Cancellation");

        currGuest.getTransactionList().add(transaction);
        booking.getRoom().getTransactionList().add(transaction);
        guestRepository.save(currGuest);
        roomRepository.save(booking.getRoom());
        transaction = transactionRepository.save(transaction);
        booking.getTransaction().add(transaction);
        //****send mail to guest

        Booking savedBooking = bookingRepository.save(booking);
        transaction.setBooking(savedBooking);
        transactionRepository.save(transaction);
        return BookingTransformer.bookingToBookingResponse(savedBooking);
    }



    public BookingResponse getBookingByBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        return BookingTransformer.bookingToBookingResponse(booking);
    }

    public List<BookingResponse> getAllBookingByGuestEmail(String guestEmail) {
        List<Booking> bookingList = bookingRepository.getAllBookingByGuestEmail(guestEmail);
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

    public List<BookingResponse> getAllBookingOccupiedOnGivenDate(Date date) {
        List<Booking> bookingList = bookingRepository.getAllBookingOccupiedOnGivenDate(date);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
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

    public List<BookingResponse> getAllUpcomingArrivalStayMoreThanNDays(int n) {
        List<Booking> bookingList = bookingRepository.getAllUpcomingArrivalStayMoreThanNDays(n);
        List<BookingResponse> responseList = new ArrayList<>();
        for(Booking booking:bookingList)
            responseList.add(BookingTransformer.bookingToBookingResponse(booking));

        return responseList;
    }






    public List<BookingResponse> getAllCheckedOutBookingBetweenDates(Date fromDate, Date toDate) {
        List<Booking> bookingList = bookingRepository.getAllCheckedOutBookingBetweenDates(fromDate, toDate);
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
        List<Booking> bookingList = bookingRepository.getAllCancelledBookingBetweenDates(fromDate, toDate);
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


}
