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
                .build();
    }

    public static TransactionResponse transactionToTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDateAndTime(transaction.getTransactionDateAndTime())
                .fundType(transaction.getFundType())
                .room(transaction.getRoom())
                .guest(transaction.getGuest())
                .booking(transaction.getBooking())
                .material(transaction.getMaterial())
                .materialRequisition(transaction.getMaterialRequisition())
                .foodOrder(transaction.getFoodOrder())
                .period(transaction.getPeriod())
                .department(transaction.getDepartment())
                .build();
    }
}
