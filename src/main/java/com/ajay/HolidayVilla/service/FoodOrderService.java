package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FoodType;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.Transformer.FoodOrderTransformer;
import com.ajay.HolidayVilla.Transformer.TransactionTransformer;
import com.ajay.HolidayVilla.dto.request.FoodOrderRequest;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.FoodOrderResponse;
import com.ajay.HolidayVilla.model.FoodOrder;
import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.model.Transaction;
import com.ajay.HolidayVilla.repository.FoodOrderRepository;
import com.ajay.HolidayVilla.repository.GuestRepository;
import com.ajay.HolidayVilla.repository.RoomRepository;
import com.ajay.HolidayVilla.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodOrderService {

    @Autowired
    FoodOrderRepository foodOrderRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TransactionRepository transactionRepository;


    public FoodOrderResponse orderFood(FoodOrderRequest foodOrderRequest, String guestEmail) {
        Guest guest = guestRepository.findByEmail(guestEmail);
        FoodOrder foodOrder = FoodOrderTransformer.foodOrderRequestToFoodOrder(foodOrderRequest);
        foodOrderRepository.save(foodOrder);
        if(foodOrderRequest.getRoomNo().length()>0) {
            Room room = roomRepository.findByRoomNo(foodOrderRequest.getRoomNo());
            foodOrder.setRoom(room);
            room.getFoodOrderList().add(foodOrder);
            roomRepository.save(room);
        }
        foodOrder.setGuest(guest);
        guest.getFoodOrderList().add(foodOrder);

        guestRepository.save(guest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFundType(FundType.CREDIT);
        Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
        transaction.setDepartment(Department.KITCHEN_FOOD);
        transaction.setGuest(guest);
        transaction.setComments("FOOD ORDER");

        FoodOrder savedFoodOrder = foodOrderRepository.save(foodOrder);
        transaction.setFoodOrder(savedFoodOrder);
        transactionRepository.save(transaction);

        guest.getTransactionList().add(transaction);
        savedFoodOrder.setTransaction(transaction);
        savedFoodOrder = foodOrderRepository.save(savedFoodOrder);
        //send mail to KITCHEN

        return FoodOrderTransformer.foodOrderToFoodOrderResponse(savedFoodOrder);
    }

    public FoodOrderResponse orderCompensationFood(FoodOrderRequest foodOrderRequest, String guestEmail) {
        FoodOrderResponse foodOrderResponse = orderFood(foodOrderRequest,guestEmail);
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(foodOrderResponse.getOrderId());
        foodOrder.getTransaction().setFundType(FundType.FREE);
        foodOrder.getTransaction().setComments("COMPENSATION FOOD ORDER");
        transactionRepository.save(foodOrder.getTransaction());

        return FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrderRepository.save(foodOrder));
    }


    public FoodOrderResponse getFoodOrderByOrderId(String orderId) {
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(orderId);
        return FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrder);
    }

    public List<FoodOrderResponse> getAllFoodOrderByFoodType(String foodType) {
        List<FoodOrder> foodOrderList = foodOrderRepository.getAllFoodOrderByFoodType(foodType);
        List<FoodOrderResponse> foodOrderResponseList = new ArrayList<>();

        for(FoodOrder foodOrder : foodOrderList)
            foodOrderResponseList.add(FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrder));

        return foodOrderResponseList;
    }

    public List<FoodOrderResponse> getAllFoodOrderByOrderDate(Date date) {
        List<FoodOrder> foodOrderList = foodOrderRepository.getAllFoodOrderByOrderDate(date);
        List<FoodOrderResponse> foodOrderResponseList = new ArrayList<>();

        for(FoodOrder foodOrder : foodOrderList)
            foodOrderResponseList.add(FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrder));

        return foodOrderResponseList;
    }

    public List<FoodOrderResponse> getAllFoodOrderByRoomNo(String roomNo) {
        List<FoodOrder> foodOrderList = foodOrderRepository.getAllFoodOrderByRoomNo(roomNo);
        List<FoodOrderResponse> foodOrderResponseList = new ArrayList<>();

        for(FoodOrder foodOrder : foodOrderList)
            foodOrderResponseList.add(FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrder));

        return foodOrderResponseList;
    }

    public List<FoodOrderResponse> getAllFoodOrderByGuestEmail(String guestEmail) {
        List<FoodOrder> foodOrderList = foodOrderRepository.getAllFoodOrderByGuestEmail(guestEmail);
        List<FoodOrderResponse> foodOrderResponseList = new ArrayList<>();

        for(FoodOrder foodOrder : foodOrderList)
            foodOrderResponseList.add(FoodOrderTransformer.foodOrderToFoodOrderResponse(foodOrder));

        return foodOrderResponseList;
    }


}
