package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.Enum.FoodType;
import com.ajay.HolidayVilla.dto.request.FoodOrderRequest;
import com.ajay.HolidayVilla.dto.response.FoodOrderResponse;
import com.ajay.HolidayVilla.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/food-order")
public class FoodOrderController {

    @Autowired
    FoodOrderService foodOrderService;

    //**TESTED**
    @PreAuthorize("hasAnyRole('ROOM_DIVISION','KITCHEN_FOOD')")
    @PostMapping("/order-compensation-food")
    public ResponseEntity orderCompensationFood(@RequestBody FoodOrderRequest foodOrderRequest, @RequestParam String guestEmail){
        FoodOrderResponse foodOrderResponse = foodOrderService.orderCompensationFood(foodOrderRequest, guestEmail);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-all-today-food-order")
    public ResponseEntity getAllTodayFoodOrder(){
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByOrderDate(Date.valueOf(LocalDate.now()));
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-food-order-by-orderId")
    public ResponseEntity getFoodOrderByOrderId(@RequestParam String orderId){
        FoodOrderResponse foodOrderResponse = foodOrderService.getFoodOrderByOrderId(orderId);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-all-food-order-by-foodType")
    public ResponseEntity getAllFoodOrderByFoodType(@RequestParam FoodType foodType){
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByFoodType(foodType.toString());
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-all-food-order-by-orderDate")
    public ResponseEntity getAllFoodOrderByOrderDate(@RequestParam Date date){
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByOrderDate(date);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-all-food-order-by-roomNo")
    public ResponseEntity getAllFoodOrderByRoomNo(@RequestParam String roomNo){
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByRoomNo(roomNo);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

    //**TESTED**
    @GetMapping("/get-all-food-order-by-guestEmail")
    public ResponseEntity getAllFoodOrderByGuestEmail(@RequestParam String guestEmail){
        List<FoodOrderResponse> foodOrderResponse = foodOrderService.getAllFoodOrderByGuestEmail(guestEmail);
        return new ResponseEntity(foodOrderResponse, HttpStatus.CREATED);
    }

}
