package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.GuestTransformer;
import com.ajay.HolidayVilla.dto.request.GuestRequest;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.exception.AlreadyRegisteredException;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public GuestResponse registerGuest(GuestRequest guestRequest) {
        if(guestRepository.findByEmail(guestRequest.getEmail()) != null)
            throw new AlreadyRegisteredException("Guest is already registered with given email address");
        if(guestRepository.findByPhoneNumber(guestRequest.getPhoneNumber()) != null)
            throw new AlreadyRegisteredException("Guest is already registered with given phone number");

        Guest savedGuest = guestRepository.save(GuestTransformer.guestRequestToGuest(guestRequest));
        return GuestTransformer.guestToGuestResponse(savedGuest);
    }
}
