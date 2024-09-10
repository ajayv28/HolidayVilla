package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.MaterialRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MaterialRequisitionRepository extends JpaRepository<MaterialRequisition, Integer> {

    public MaterialRequisition findByRequisitionId(String requisitionId);

    @Query(value = "select * from material_requisition where expecting_delivery_date < :currDate", nativeQuery=true)
    public List<MaterialRequisition> getElapsedRequisitionList(Date currDate);

    @Query(value = "select * from material_requisition where requisition_status = 'PROCESSED'", nativeQuery=true)
    public List<MaterialRequisition> getAllInProgressMaterialRequisition();

    @Query(value = "select * from material_requisition where requisition_status = 'PROCESSED' and department = :department", nativeQuery=true)
    public List<MaterialRequisition> getAllInProgressMaterialRequisitionByDepartment(String department);

    @Query(value = "select * from material_requisition where requisition_status = 'REQUISITION_RECEIVED'", nativeQuery=true)
    public List<MaterialRequisition> getAllNotProcessedMaterialRequisition();

    @Query(value = "select * from material_requisition where requisition_status = 'REQUISITION_RECEIVED' and department = :department", nativeQuery=true)
    public List<MaterialRequisition> getAllNotProcessedMaterialRequisitionByDepartment(String department);

    @Query(value = "select * from material_requisition where requisition_status = 'CANCELLED'", nativeQuery=true)
    public List<MaterialRequisition> getAllCancelledMaterialRequisition();

    @Query(value = "select * from material_requisition where requisition_status = 'CANCELLED' and department = :department", nativeQuery=true)
    public List<MaterialRequisition> getAllCancelledMaterialRequisitionByDepartment(String department);

    @Query(value = "select * from material_requisition where expecting_delivery_date = :date", nativeQuery=true)
    public List<MaterialRequisition> getAllMaterialRequisitionByDeliveryDate(Date date);

    @Query(value = "select * from material_requisition where expecting_delivery_date between :fromDate and :toDate", nativeQuery=true)
    public List<MaterialRequisition> getAllMaterialRequisitionBetweenDeliveryDate(Date fromDate, Date toDate);


    //public List<MaterialRequisition> getAllMaterialRequisitionByMaterialName(String materialName);


    //public List<MaterialRequisition> getAllMaterialRequisitionByStaffEmail(String staffEmail);

    @Query(value = "select * from material_requisition where requisition_status = 'MATERIAL_RECEIVED' and department = :department", nativeQuery=true)
    public List<MaterialRequisition> getAllReceivedMaterialRequisitionByDepartment(String department);
}
