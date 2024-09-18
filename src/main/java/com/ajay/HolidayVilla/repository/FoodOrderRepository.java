package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.Enum.FoodType;
import com.ajay.HolidayVilla.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

    public FoodOrder findByOrderId(String orderId);

    @Query(value = "select * from food_order where food_type = :foodType", nativeQuery = true)
    public List<FoodOrder> getAllFoodOrderByFoodType(String foodType);

    @Query(value = "select * from food_order where DATE(order_date_and_time) = DATE(:date)", nativeQuery = true)
    public List<FoodOrder> getAllFoodOrderByOrderDate(Date date);

    @Query(value = "select * from food_order where room_id = (select id from room where room_no = :roomNo)", nativeQuery = true)
    public List<FoodOrder> getAllFoodOrderByRoomNo(String roomNo);

    @Query(value = "select * from food_order where guest_id = (select id from guest where email = :guestEmail)", nativeQuery = true)
    public List<FoodOrder> getAllFoodOrderByGuestEmail(String guestEmail);
}
