package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.FoodOrderRequest;
import com.ajay.HolidayVilla.dto.response.FoodOrderResponse;
import com.ajay.HolidayVilla.model.FoodOrder;

import java.util.UUID;

public class FoodOrderTransformer {

    public static FoodOrder foodOrderRequestToFoodOrder(FoodOrderRequest foodOrderRequest){
        return FoodOrder.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .foodType(foodOrderRequest.getFoodType())
                .build();
    }

    public static FoodOrderResponse foodOrderToFoodOrderResponse(FoodOrder foodOrder){
        return FoodOrderResponse.builder()
                .orderId(foodOrder.getOrderId())
                .foodType(foodOrder.getFoodType())
                .guest(foodOrder.getGuest())
                .orderDateAndTime(foodOrder.getOrderDateAndTime())
                .room(foodOrder.getRoom())
                .transaction(foodOrder.getTransaction())
                .build();
    }
}