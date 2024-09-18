package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

    public Guest findByEmail(String guestEmail);

    public Guest findByPhoneNumber(long phoneNumber);

    @Query(value="select * from guest where currently_in_house = true", nativeQuery=true)
    public List<Guest> getAllTodayInHouseGuest();
}
