package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;





    @PutMapping("/change-booking-room-ifPossible")
    public ResponseEntity changeBookingRoomIfPossible(@RequestParam String bookingId){
        BookingResponse bookingResponse = bookingService.changeBookingRoomIfPossible(bookingId);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }


    @PutMapping("/cancel-booking-by-bookingId")
    public ResponseEntity cancelBookingByBookingId(@RequestParam String bookingId){
        BookingResponse bookingResponse = bookingService.cancelBookingByBookingId(bookingId);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @PutMapping("/cancel-last-booking-by-guestEmail")
    public ResponseEntity cancelLastBookingByGuestEmail(@RequestParam String guestEmail){
        BookingResponse bookingResponse = bookingService.cancelLastBooking(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }






    @GetMapping("/get-booking-by-bookingId")
    public ResponseEntity getBookingByBookingId(@RequestParam String bookingId){
        BookingResponse bookingResponse = bookingService.getBookingByBookingId(bookingId);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-booking-by-guestEmail")
    public ResponseEntity getAllBookingByGuestEmail(@RequestParam String guestEmail){
        List<BookingResponse> bookingResponse = bookingService.getAllBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-booking-between-dates")
    public ResponseEntity getAllBookingBetweenDates(@RequestParam Date fromdate,@RequestParam Date toDate){
        List<BookingResponse> bookingResponse = bookingService.getAllBookingBetweenDates(fromdate,toDate);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-booking-occupied-on-given-date")
    public ResponseEntity getAllBookingOccupiedOnGivenDate(@RequestParam Date date){
        List<BookingResponse> bookingResponse = bookingService.getAllBookingOccupiedOnGivenDate(date);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }





    @GetMapping("/get-all-upcoming-arrival-booking")
    public ResponseEntity getAllUpcomingArrivalBooking(){
        List<BookingResponse> bookingResponse = bookingService.getAllUpcomingArrivalBooking();
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-upcoming-arrival-booking-by-roomNo")
    public ResponseEntity getAllUpcomingArrivalBookingByRoomNo(@RequestParam String roomNo){
        List<BookingResponse> bookingResponse = bookingService.getAllUpcomingArrivalBookingByRoomNo(roomNo);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-upcoming-arrival-booking-by-guestEmail")
    public ResponseEntity getAllUpcomingArrivalBookingByGuestEmail(@RequestParam String guestEmail){
        List<BookingResponse> bookingResponse = bookingService.getAllUpcomingArrivalBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }


    @GetMapping("/get-all-upcoming-arrival-stay-more-than-n-days")
    public ResponseEntity getAllUpcomingArrivalStayMoreThanNDays(@RequestParam int n){
        List<BookingResponse> bookingResponse = bookingService.getAllUpcomingArrivalStayMoreThanNDays(n);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }







    @GetMapping("/get-all-checkedOut-booking-between-dates")
    public ResponseEntity getAllCheckedOutBookingBetweenDates(@RequestParam Date fromDate, Date toDate){
        List<BookingResponse> bookingResponse = bookingService.getAllCheckedOutBookingBetweenDates(fromDate, toDate);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-checked_out-booking-by-guestEmail")
    public ResponseEntity getAllCheckedOutBookingByGuestEmail(@RequestParam String guestEmail){
        List<BookingResponse> bookingResponse = bookingService.getAllCheckedOutBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }






    @GetMapping("/get-all-cancelled-booking-between-dates")
    public ResponseEntity getAllCancelledBookingBetweenDates(@RequestParam Date fromDate, Date toDate){
        List<BookingResponse> bookingResponse = bookingService.getAllCancelledBookingBetweenDates(fromDate, toDate);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-cancelled-booking-by-guestEmail")
    public ResponseEntity getAllCancelledBookingByGuestEmail(@RequestParam String guestEmail){
        List<BookingResponse> bookingResponse = bookingService.getAllCancelledBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

}
