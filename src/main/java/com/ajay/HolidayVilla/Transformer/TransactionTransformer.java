package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TransactionTransformer {

    public static Transaction transactionRequestToTransaction(TransactionRequest transactionRequest){

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        String month = currentDate.format(formatter).toUpperCase();
        int year = currentDate.getYear();

        String period = month + year;

        return Transaction.builder()
                .fundType(transactionRequest.getFundType())
                .transactionId(String.valueOf(UUID.randomUUID()))
                .period(period)
                .comments(transactionRequest.getComments())
                .amount(transactionRequest.getAmount())
                .build();
    }

    public static TransactionResponse transactionToTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDateAndTime(transaction.getTransactionDateAndTime())
                .fundType(transaction.getFundType())
                .roomResponse(RoomTransformer.roomToRoomResponse(transaction.getRoom()))
                .guestResponse(GuestTransformer.guestToGuestResponse(transaction.getGuest()))
                .bookingResponse(BookingTransformer.bookingToBookingResponse(transaction.getBooking()))
                .materialResponse(MaterialTransformer.materialToMaterialResponse(transaction.getMaterial()))
                .materialRequisitionResponse(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(transaction.getMaterialRequisition()))
                .foodOrderResponse(FoodOrderTransformer.foodOrderToFoodOrderResponse(transaction.getFoodOrder()))
                .period(transaction.getPeriod())
                .department(transaction.getDepartment())
                .staffResponse(StaffTransformer.staffToStaffResponse(transaction.getStaff()))
                .comments(transaction.getComments())
                .amount(transaction.getAmount())
                .build();
    }
}
