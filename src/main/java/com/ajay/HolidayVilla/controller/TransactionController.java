package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/create")
    public ResponseEntity newTransaction(@RequestBody TransactionRequest transactionRequest, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        TransactionResponse transactionResponse = transactionService.newTransaction(transactionRequest, staffEmail);
        return new ResponseEntity(transactionResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-transaction-by-transactionId")
    public ResponseEntity getTransactionsByTransactionId(@RequestParam String transactionId){
        TransactionResponse transactionResponse = transactionService.getTransactionsByTransactionId(transactionId);
        return new ResponseEntity(transactionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-transactions-by-type-and-period")
    public ResponseEntity getTransactionsByTypeAndPeriod(@RequestParam String period, @RequestParam FundType fundType){
        List<TransactionResponse> transactionResponseList = transactionService.getTransactionsByTypeAndPeriod(period, fundType.toString());
        return new ResponseEntity(transactionResponseList, HttpStatus.OK);
    }

    @GetMapping("/get-transactions-by-department-and-period")
    public ResponseEntity getTransactionsByDepartmentAndPeriod(@RequestParam String period, @RequestParam Department department){
        List<TransactionResponse> transactionResponseList = transactionService.getTransactionsByDepartmentAndPeriod(period, department.toString());
        return new ResponseEntity(transactionResponseList, HttpStatus.OK);
    }

    @GetMapping("/get-transactions-made-between-dates")
    public ResponseEntity getTransactionsMadeBetweenDates(@RequestParam Date fromDate, @RequestParam Date toDate){
        List<TransactionResponse> transactionResponseList = transactionService.getTransactionsMadeBetweenDates(fromDate, toDate);
        return new ResponseEntity(transactionResponseList, HttpStatus.OK);
    }


}
