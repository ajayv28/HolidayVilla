package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.MaterialRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRequisitionRepository extends JpaRepository<MaterialRequisition, Integer> {

    public MaterialRequisition findByRequisitionId(String requisitionId);
}
