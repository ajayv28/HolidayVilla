package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.model.Booking;

import java.util.UUID;

public class BookingTransformer {


    public static Booking bookingRequestToBooking(BookingRequest bookingRequest){
        return Booking.builder()
                .roomType(bookingRequest.getRoomType())
                .fromDate(bookingRequest.getFromDate())
                .toDate(bookingRequest.getToDate())
                .bookingId(String.valueOf(UUID.randomUUID()))
                .couponCode(bookingRequest.getCouponCode())
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking){
        return BookingResponse.builder()
                .roomType(booking.getRoomType())
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .totalFare(booking.getTotalFare())
                .bookingId(booking.getBookingId())
                .couponCode(booking.getCouponCode())
                .guestResponse(GuestTransformer.guestToGuestResponse(booking.getGuest()))
                .roomResponse(RoomTransformer.roomToRoomResponse(booking.getRoom()))
                .transactionId(booking.getTransaction().getLast().getTransactionId())
                .bookingStatus(booking.getBookingStatus())
                .build();
    }

    public static BookingRequest bookingToBookingRequest(Booking booking){
        return BookingRequest.builder()
                .roomType(booking.getRoomType())
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .couponCode(booking.getCouponCode())
                .build();
    }
}
