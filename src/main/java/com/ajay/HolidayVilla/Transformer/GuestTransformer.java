package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.GuestRequest;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GuestTransformer {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Guest guestRequestToGuest(GuestRequest guestRequest){
        return Guest.builder()
                .email(guestRequest.getEmail())
                .phoneNumber(guestRequest.getPhoneNumber())
                .password(passwordEncoder.encode(guestRequest.getPassword()))
                .name(guestRequest.getName())
                .dob(guestRequest.getDob())
                .age(guestRequest.getAge())
                .gender(guestRequest.getGender())
                .currentlyActiveBooking(false)
                .role("ROLE_GUEST")
                .build();
    }

    public static GuestResponse guestToGuestResponse(Guest guest){
        return GuestResponse.builder()
                .email(guest.getEmail())
                .phoneNumber(guest.getPhoneNumber())
                .name(guest.getName())
                .dob(guest.getDob())
                .age(guest.getAge())
                .gender(guest.getGender())
                .currentlyActiveBooking(guest.isCurrentlyActiveBooking())
                .build();
    }
}
