package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.TransactionTransformer;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.model.Transaction;
import com.ajay.HolidayVilla.repository.StaffRepository;
import com.ajay.HolidayVilla.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StaffRepository staffRepository;


    public TransactionResponse newTransaction(TransactionRequest transactionRequest, String staffEmail) {
        Transaction transaction = transactionRepository.save(TransactionTransformer.transactionRequestToTransaction(transactionRequest));
        transaction.setStaff(staffRepository.findByEmail(staffEmail));
        Transaction savedTransaction = transactionRepository.save(transaction);
        staffRepository.findByEmail(staffEmail).getTransactionList().add(savedTransaction);
        staffRepository.save(staffRepository.findByEmail(staffEmail));
        return TransactionTransformer.transactionToTransactionResponse(savedTransaction);
    }

    public TransactionResponse getTransactionsByTransactionId(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        return TransactionTransformer.transactionToTransactionResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByTypeAndPeriod(String period, String fundType) {
        List<Transaction> transactionList = transactionRepository.getTransactionsByTypeAndPeriod(period, fundType);
        List<TransactionResponse> transactionResponseList = new ArrayList<>();

        for(Transaction transaction : transactionList)
            transactionResponseList.add(TransactionTransformer.transactionToTransactionResponse(transaction));
        return transactionResponseList;
    }

    public List<TransactionResponse> getTransactionsByDepartmentAndPeriod(String period, String department) {
        List<Transaction> transactionList = transactionRepository.getTransactionsByDepartmentAndPeriod(period, department);
        List<TransactionResponse> transactionResponseList = new ArrayList<>();

        for(Transaction transaction : transactionList)
            transactionResponseList.add(TransactionTransformer.transactionToTransactionResponse(transaction));
        return transactionResponseList;
    }

    public List<TransactionResponse> getTransactionsMadeBetweenDates(Date fromDate, Date toDate) {
        List<Transaction> transactionList = transactionRepository.getTransactionsMadeBetweenDates(fromDate, toDate);
        List<TransactionResponse> transactionResponseList = new ArrayList<>();

        for(Transaction transaction : transactionList)
            transactionResponseList.add(TransactionTransformer.transactionToTransactionResponse(transaction));
        return transactionResponseList;
    }
}
