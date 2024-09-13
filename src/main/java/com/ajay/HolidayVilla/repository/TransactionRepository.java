package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    public Transaction findByTransactionId(String transactionId);

    @Query(value = "select * from transaction where fund_type = :fundType and period = :period", nativeQuery = true)
    public List<Transaction> getTransactionsByTypeAndPeriod(String period, String fundType);

    @Query(value = "select * from transaction where department = :department and period = :period", nativeQuery = true)
    public List<Transaction> getTransactionsByDepartmentAndPeriod(String period, String department);

    @Query(value = "select * from transaction where transaction_date_and_time >= :fromDate and transaction_date_and_time <= :toDate", nativeQuery = true)
    public List<Transaction> getTransactionsMadeBetweenDates(Date fromDate, Date toDate);
}
