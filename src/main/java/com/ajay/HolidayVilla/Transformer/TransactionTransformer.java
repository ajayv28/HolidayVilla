package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.model.Transaction;

import java.util.UUID;

public class TransactionTransformer {

    public Transaction transactionRequestToTransaction(TransactionRequest transactionRequest){
        return Transaction.builder()
                .fundType(transactionRequest.getFundType())
                .transactionId(String.valueOf(UUID.randomUUID()))
                .build();
    }

    public TransactionResponse transactionToTransactionResponse(Transaction transaction){
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
                .build();
    }
}
