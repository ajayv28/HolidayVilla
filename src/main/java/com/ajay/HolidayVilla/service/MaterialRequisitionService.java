package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.RequisitionStatus;
import com.ajay.HolidayVilla.Transformer.MaterialRequisitionTransformer;
import com.ajay.HolidayVilla.Transformer.MaterialTransformer;
import com.ajay.HolidayVilla.dto.request.MaterialRequisitionRequest;
import com.ajay.HolidayVilla.dto.response.MaterialRequisitionResponse;
import com.ajay.HolidayVilla.model.MaterialRequisition;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.repository.MaterialRequisitionRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialRequisitionService {

    @Autowired
    MaterialRequisitionRepository materialRequisitionRepository;

    @Autowired
    StaffRepository staffRepository;

    public MaterialRequisitionResponse raiseMaterialRequisition(MaterialRequisitionRequest materialRequisitionRequest, String staffEmail) {

        Staff staff = staffRepository.findByEmail(staffEmail);
        MaterialRequisition materialRequisition = MaterialRequisitionTransformer.materialRequisitionRequestToMaterialRequisition(materialRequisitionRequest);

        materialRequisition.setRequisitionStaff(staff);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);

        staff.getMaterialRequisitionList().add(savedMaterialRequisition);
        staffRepository.save(staff);

        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse cancelRequisition(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        materialRequisition.setRequisitionStatus(RequisitionStatus.CANCELLED);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse markReceivedByRequisitionId(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        materialRequisition.setRequisitionStatus(RequisitionStatus.MATERIAL_RECEIVED);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }


    public MaterialRequisitionResponse processRequisitionByRequisitionId(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        //send mail
        materialRequisition.setRequisitionStatus(RequisitionStatus.PROCESSED);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }


    public List<MaterialRequisitionResponse> followUpOnAllElapsedRequisition() {
        List<MaterialRequisition> elapsedRequisitionList = materialRequisitionRepository.getElapsedRequisitionList();

        for (MaterialRequisition curr : elapsedRequisitionList) {
            //send mail
        }

        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : elapsedRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));

        return responses;
    }


    public MaterialRequisitionResponse changeExpectedDeliveryDate(String requisitionId, Date newDate) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        Date oldDate = materialRequisition.getExpectingDeliveryDate();
        materialRequisition.setExpectingDeliveryDate(newDate);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        //send mail
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse changeRequisitionQuantity(String requisitionId, double newQuantity) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        double oldQuantity = materialRequisition.getRequisitionQuantity();
        materialRequisition.setRequisitionQuantity(newQuantity);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        //send mail
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }


    public MaterialRequisitionResponse getMaterialRequisitionByRequisitionId(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(materialRequisition);
    }


    public List<MaterialRequisitionResponse> getAllInProgressMaterialRequisition() {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllInProgressMaterialRequisition();
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllInProgressMaterialRequisitionByDepartment(String department) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllInProgressMaterialRequisitionByDepartment(department);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllNotProcessedMaterialRequisition() {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllNotProcessedMaterialRequisition();
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllNotProcessedMaterialRequisitionByDepartment(String department) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllNotProcessedMaterialRequisitionByDepartment(department);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }

    public List<MaterialRequisitionResponse> getAllCancelledMaterialRequisition() {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllCancelledMaterialRequisition();
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }

    public List<MaterialRequisitionResponse> getAllCancelledMaterialRequisitionByDepartment(String department) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllCancelledMaterialRequisitionByDepartment(department);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }

    public List<MaterialRequisitionResponse> getAllMaterialRequisitionByDeliveryDate(Date date) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllMaterialRequisitionByDeliveryDate(date);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllMaterialRequisitionBetweenDeliveryDate(Date fromDate, Date toDate) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllMaterialRequisitionBetweenDeliveryDate(fromDate, toDate);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllMaterialRequisitionByMaterialName(String materialName) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllMaterialRequisitionByMaterialName(materialName);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllMaterialRequisitionByStaffEmail(String staffEmail) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllMaterialRequisitionByStaffEmail(staffEmail);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }


    public List<MaterialRequisitionResponse> getAllReceivedMaterialRequisitionByDepartment(String department) {
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllReceivedMaterialRequisitionByDepartment(department);
        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : materialRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));
        return responses;
    }

}