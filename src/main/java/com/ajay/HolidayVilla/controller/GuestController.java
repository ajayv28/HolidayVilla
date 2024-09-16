package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.request.FoodOrderRequest;
import com.ajay.HolidayVilla.dto.request.GuestRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.dto.response.FoodOrderResponse;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.service.BookingService;
import com.ajay.HolidayVilla.service.FoodOrderService;
import com.ajay.HolidayVilla.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    BookingService bookingService;

    @Autowired
    FoodOrderService foodOrderService;


    //**TESTED**
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity registerGuest(@RequestBody GuestRequest guestRequest){

        GuestResponse guestResponse = guestService.registerGuest(guestRequest);
        return new ResponseEntity(guestResponse, HttpStatus.CREATED);
    }


    //**TESTED**
    @PostMapping("/create-booking")
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest, guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.CREATED);
    }

    @PutMapping("/cancel-last-booking")
    public ResponseEntity cancelLastBooking(@AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        BookingResponse bookingResponse = bookingService.cancelLastBooking(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.CREATED);
    }


    @PostMapping("/order-food")
    public ResponseEntity orderFood(@RequestBody FoodOrderRequest foodOrderRequest, @AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        FoodOrderResponse foodOrderResponse = foodOrderService.orderFood(foodOrderRequest, guestEmail);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-my-food-order")
    public ResponseEntity getAllFoodOrderByGuestEmail(@AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByGuestEmail(guestEmail);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }



    @GetMapping("/get-all-my-booking")
    public ResponseEntity getAllMyBooking(@AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        List<BookingResponse> bookingResponse = bookingService.getAllBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-my-checked_out-booking")
    public ResponseEntity getAllMyCheckedOutBooking(@AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        List<BookingResponse> bookingResponse = bookingService.getAllCheckedOutBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-my-cancelled-booking")
    public ResponseEntity getAllMyCancelledBookingGuestEmail(@AuthenticationPrincipal UserDetails userDetails){
        String guestEmail = userDetails.getUsername();
        List<BookingResponse> bookingResponse = bookingService.getAllCancelledBookingByGuestEmail(guestEmail);
        return new ResponseEntity(bookingResponse, HttpStatus.OK);
    }


}
