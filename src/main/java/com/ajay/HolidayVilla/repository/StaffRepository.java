package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    public Staff findByEmail(String email);

    public Staff findByPhoneNumber(long phoneNumber);

    @Query(value = "select * from staff where employment_status = true", nativeQuery = true)
    public List<Staff> getAllCurrentStaff();

    @Query(value = "select * from staff where employment_status = false", nativeQuery = true)
    public List<Staff> getAllExStaff();

    @Query(value = "select * from staff where employment_status = true and department = :department", nativeQuery = true)
    public List<Staff> getAllCurrentStaffByDepartment(String department);

    @Query(value = "select * from staff where employment_status = false and department = :department", nativeQuery = true)
    public List<Staff> getAllExStaffByDepartment(String department);

    @Query(value = "select salary from staff where email = :staffEmail", nativeQuery = true)
    public double getStaffSalaryByStaffEmail(String staffEmail);
}
