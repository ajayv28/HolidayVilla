package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.BookingTransformer;
import com.ajay.HolidayVilla.dto.request.BookingRequest;
import com.ajay.HolidayVilla.dto.response.BookingResponse;
import com.ajay.HolidayVilla.exception.RoomNotAvailableBetweenGivenDates;
import com.ajay.HolidayVilla.model.*;
import com.ajay.HolidayVilla.repository.BookingRepository;
import com.ajay.HolidayVilla.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    public BookingResponse createBooking(BookingRequest bookingRequest, String guestEmail) {


        Guest currGuest = guestRepository.findByEmail(guestEmail);
       // check isfree

        Room currRoom = bookingRepository.getAvailableRoom(booking.getFromDate(), booking.getToDate(), booking.getRoomType());
        if(currRoom == null)
            throw new RoomNotAvailableBetweenGivenDates("Sorry we are fully booken in given dates. Please try to change dates and try again");

        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest);

//        coupon
//if yes, reduce coupon qty
//                save coupon
//
//                        booking set fare
//                booking set guest
//                booking set Room
//
//                add booking in guest list
//                save guest
//
//                        save booking


    }
}
