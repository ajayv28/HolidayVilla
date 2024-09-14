package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

    public Guest findByEmail(String guestEmail);

    public Guest findByPhoneNumber(long phoneNumber);
}
