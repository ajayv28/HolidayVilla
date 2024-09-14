package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.Transformer.StaffTransformer;
import com.ajay.HolidayVilla.Transformer.TransactionTransformer;
import com.ajay.HolidayVilla.dto.request.StaffRequest;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.StaffResponse;
import com.ajay.HolidayVilla.dto.response.TransactionResponse;
import com.ajay.HolidayVilla.exception.AlreadyRegisteredException;
import com.ajay.HolidayVilla.exception.NoAccessForThisRequestException;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.model.Transaction;
import com.ajay.HolidayVilla.repository.StaffRepository;
import com.ajay.HolidayVilla.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    TransactionRepository transactionRepository;


    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public StaffResponse onBoardStaff(StaffRequest staffRequest, String staffEmail) {
        if(staffRepository.findByEmail(staffRequest.getEmail()) != null)
            throw new AlreadyRegisteredException("Staff is already registered with given email address");
        if(staffRepository.findByPhoneNumber(staffRequest.getPhoneNumber()) != null)
            throw new AlreadyRegisteredException("Staff is already registered with given phone number");

        Staff staff = staffRepository.findByEmail(staffEmail);
        if(staffRequest.getDepartment().toString().equals("HR") && (!staff.getRole().equals("ROLE_MANAGER"))){
            throw new NoAccessForThisRequestException("You do not have the access to Onboard HR staff. Kindly ask a Manager to Onboard this Staff");
        }
        Staff savedStaff = staffRepository.save(StaffTransformer.staffRequestToStaff(staffRequest));
        return StaffTransformer.staffToStaffResponse(savedStaff);
    }

    public StaffResponse makeStaffAsManager(String staffEmail, String currStaffEmail) {
        Staff currStaff = staffRepository.findByEmail(currStaffEmail);
        if(!currStaff.getRole().equals("ROLE_MANAGER")){
            throw new NoAccessForThisRequestException("You do not have the access to make given staff as manager. Kindly ask a Manager to execute this request");
        }

        Staff staff = staffRepository.findByEmail(staffEmail);
        staff.setRole("ROLE_MANAGER");
        return StaffTransformer.staffToStaffResponse(staffRepository.save(staff));
    }

    public StaffResponse offBoardStaff(String staffEmail) {
        Staff currStaff = staffRepository.findByEmail(staffEmail);
        currStaff.setRole("ROLE_EX_EMPLOYEE");
        currStaff.setEmploymentStatus(false);
        return StaffTransformer.staffToStaffResponse(staffRepository.save(currStaff));
    }

    public StaffResponse getStaffByStaffEmail(String staffEmail) {
        Staff currStaff = staffRepository.findByEmail(staffEmail);
        return StaffTransformer.staffToStaffResponse(staffRepository.save(currStaff));
    }

    public StaffResponse resetPassword(String staffEmail, String newPassword) {
        Staff currStaff = staffRepository.findByEmail(staffEmail);
        currStaff.setPassword(passwordEncoder.encode(newPassword));
        return StaffTransformer.staffToStaffResponse(staffRepository.save(currStaff));
    }

    public StaffResponse changeRole(String staffEmail, String newRole) {
        Staff currStaff = staffRepository.findByEmail(staffEmail);
        currStaff.setRole("ROLE_" + newRole);
        return StaffTransformer.staffToStaffResponse(staffRepository.save(currStaff));
    }

    public StaffResponse makeStaffManagerAccess(String staffEmail) {
        Staff currStaff = staffRepository.findByEmail(staffEmail);
        currStaff.setRole("ROLE_MANAGER");
        return StaffTransformer.staffToStaffResponse(staffRepository.save(currStaff));
    }

    public List<StaffResponse> getAllCurrentStaff() {
        List<Staff> staffList = staffRepository.getAllCurrentStaff();
        List<StaffResponse> staffResponseList = new ArrayList<>();

        for(Staff staff : staffList)
            staffResponseList.add(StaffTransformer.staffToStaffResponse(staff));

        return staffResponseList;
    }

    public List<StaffResponse> getAllExStaff() {
        List<Staff> staffList = staffRepository.getAllExStaff();
        List<StaffResponse> staffResponseList = new ArrayList<>();

        for(Staff staff : staffList)
            staffResponseList.add(StaffTransformer.staffToStaffResponse(staff));

        return staffResponseList;
    }

    public List<StaffResponse> getAllCurrentStaffByDepartment(String department) {
        List<Staff> staffList = staffRepository.getAllCurrentStaffByDepartment(department);
        List<StaffResponse> staffResponseList = new ArrayList<>();

        for(Staff staff : staffList)
            staffResponseList.add(StaffTransformer.staffToStaffResponse(staff));

        return staffResponseList;
    }

    public List<StaffResponse> getAllExStaffByDepartment(String department) {
        List<Staff> staffList = staffRepository.getAllExStaffByDepartment(department);
        List<StaffResponse> staffResponseList = new ArrayList<>();

        for(Staff staff : staffList)
            staffResponseList.add(StaffTransformer.staffToStaffResponse(staff));

        return staffResponseList;
    }

    public double getStaffSalaryByStaffEmail(String staffEmail) {
        double salary = staffRepository.getStaffSalaryByStaffEmail(staffEmail);
        return salary;
    }




    public StaffResponse changeStaffSalaryByStaffEmail(String staffEmail, double newSalary) {
        Staff staff = staffRepository.findByEmail(staffEmail);
        staff.setSalary(newSalary);
        return StaffTransformer.staffToStaffResponse(staffRepository.save(staff));
    }


    public List<TransactionResponse> createTransactionForPayroll() {
        List<Staff> currentStaffList = staffRepository.getAllCurrentStaff();
        List<TransactionResponse> transactionResponseList = new ArrayList<>();

        for(Staff staff : currentStaffList){

            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setFundType(FundType.DEBIT);
            Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
            transaction.setDepartment(Department.HR);
            transaction.setStaff(staff);
            transaction.setAmount(staff.getSalary());
            transaction.setComments("STAFF SALARY");

            staff.getTransactionList().add(transaction);
            staffRepository.save(staff);
            transactionResponseList.add(TransactionTransformer.transactionToTransactionResponse(transactionRepository.save(transaction)));
        }

        return transactionResponseList;
    }



}
