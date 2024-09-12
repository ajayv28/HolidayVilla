package com.ajay.HolidayVilla.configuration;

import com.ajay.HolidayVilla.model.*;
import com.ajay.HolidayVilla.repository.GuestRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    StaffRepository staffRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Guest guest = guestRepository.findByEmail(email);
        if (guest != null) {
            return new UserDetailsCreator(guest);
        }

        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            return new UserDetailsCreator(staff);
        }


        throw new UsernameNotFoundException("Invalid email id");
    }
}
