package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.GuestTransformer;
import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.dto.request.GuestRequest;
import com.ajay.HolidayVilla.dto.response.GuestResponse;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.exception.AlreadyRegisteredException;
import com.ajay.HolidayVilla.model.Booking;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    JavaMailSender javaMailSender;


    public void sendConfirmationMail (Guest guest) {
        String text = "Dear Mr./Mrs. " + guest.getName() + ", your guest account successfully created with HolidayVilla. Use your email - " + guest.getEmail() + " and your password to login. We are happy to deliver our top notch hospitality service to you. Kindly call our 24x7 support hotline for any assistance.";


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply.holiday.villa.1@gmail.com");
        simpleMailMessage.setTo(guest.getEmail());
        simpleMailMessage.setSubject("HolidayVilla - Guest Account Created Successfully");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }

    public GuestResponse registerGuest(GuestRequest guestRequest) {
        if(guestRepository.findByEmail(guestRequest.getEmail()) != null)
            throw new AlreadyRegisteredException("Guest is already registered with given email address");
        if(guestRepository.findByPhoneNumber(guestRequest.getPhoneNumber()) != null)
            throw new AlreadyRegisteredException("Guest is already registered with given phone number");

        Guest savedGuest = guestRepository.save(GuestTransformer.guestRequestToGuest(guestRequest));
        sendConfirmationMail(savedGuest);
        return GuestTransformer.guestToGuestResponse(savedGuest);
    }

    public List<GuestResponse> getAllTodayInHouseGuest() {
        List<Guest> guestList = guestRepository.getAllTodayInHouseGuest();
        List<GuestResponse> responseList = new ArrayList<>();
        for(Guest guest:guestList)
            responseList.add(GuestTransformer.guestToGuestResponse(guest));

        return responseList;
    }
}
