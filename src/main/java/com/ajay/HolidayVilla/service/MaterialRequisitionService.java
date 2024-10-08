package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.Enum.FundType;
import com.ajay.HolidayVilla.Enum.RequisitionStatus;
import com.ajay.HolidayVilla.Transformer.MaterialRequisitionTransformer;
import com.ajay.HolidayVilla.Transformer.MaterialTransformer;
import com.ajay.HolidayVilla.Transformer.TransactionTransformer;
import com.ajay.HolidayVilla.dto.request.MaterialRequisitionRequest;
import com.ajay.HolidayVilla.dto.request.TransactionRequest;
import com.ajay.HolidayVilla.dto.response.MaterialRequisitionResponse;
import com.ajay.HolidayVilla.exception.InvalidInputException;
import com.ajay.HolidayVilla.exception.NoAccessForThisRequestException;
import com.ajay.HolidayVilla.model.Material;
import com.ajay.HolidayVilla.model.MaterialRequisition;
import com.ajay.HolidayVilla.model.Staff;
import com.ajay.HolidayVilla.model.Transaction;
import com.ajay.HolidayVilla.repository.MaterialRepository;
import com.ajay.HolidayVilla.repository.MaterialRequisitionRepository;
import com.ajay.HolidayVilla.repository.StaffRepository;
import com.ajay.HolidayVilla.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialRequisitionService {

    @Autowired
    MaterialRequisitionRepository materialRequisitionRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public MaterialRequisitionResponse raiseMaterialRequisition(MaterialRequisitionRequest materialRequisitionRequest, String staffEmail) {

        Staff staff = staffRepository.findByEmail(staffEmail);
        MaterialRequisition materialRequisition = MaterialRequisitionTransformer.materialRequisitionRequestToMaterialRequisition(materialRequisitionRequest);

        materialRequisition.setRequisitionStaff(staff);
        Material material = materialRepository.findByMaterialName(materialRequisitionRequest.getRequisitionMaterial().toUpperCase());
        if(material == null)
            throw new InvalidInputException("No material exist with given name. Pls approach to purchase dept to create given material in our database");
        materialRequisition.setRequisitionMaterial(material);

        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);

        staff.getMaterialRequisitionList().add(savedMaterialRequisition);
        staffRepository.save(staff);
        material.getMaterialRequisitionList().add(savedMaterialRequisition);
        materialRepository.save(material);

        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse cancelRequisition(String requisitionId, String staffEmail) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        if (materialRequisition == null)
            throw new InvalidInputException("Sorry given requisition id does not exist");
        Staff staff = staffRepository.findByEmail(staffEmail);
        if ((materialRequisition.getRequisitionStaff() != staff) && !(staff.getRole().equals("ROLE_MANAGER")))
            throw new NoAccessForThisRequestException("You do not have access to proceed with this request");
        if (materialRequisition.getRequisitionStatus().toString().equals("MATERIAL_RECEIVED"))
            throw new InvalidInputException("Material already received");
        if (materialRequisition.getRequisitionStatus().toString().equals("CANCELLED"))
            throw new InvalidInputException("Requisition already cancelled");

        if (materialRequisition.getRequisitionStatus().toString().equals("PROCESSED")){
        //SEND MAIL TO CANCEL
    }

        materialRequisition.setRequisitionStatus(RequisitionStatus.CANCELLED);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse markReceivedByRequisitionId(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        materialRequisition.setRequisitionStatus(RequisitionStatus.MATERIAL_RECEIVED);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFundType(FundType.DEBIT);
        Transaction transaction = TransactionTransformer.transactionRequestToTransaction(transactionRequest);
        transaction.setDepartment(materialRequisition.getDepartment());
        transaction.setMaterial(materialRequisition.getRequisitionMaterial());
        transaction.setMaterialRequisition(materialRequisition);
        transaction.setAmount(materialRequisition.getRequisitionQuantity()*materialRequisition.getRequisitionMaterial().getPrice());
        transaction.setComments("MATERIAL PURCHASE");
        Transaction savedTransaction = transactionRepository.save(transaction);

        materialRequisition.setTransaction(savedTransaction);
        materialRequisition.getRequisitionMaterial().getTransactionList().add(savedTransaction);
        materialRequisitionRepository.save(materialRequisition);
        materialRepository.save(materialRequisition.getRequisitionMaterial());

        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }


    public MaterialRequisitionResponse processRequisitionByRequisitionId(String requisitionId) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        if(materialRequisition.getRequisitionStatus().toString().equals("PROCESSED"))
            throw new InvalidInputException("Requisition already processed");
        if(materialRequisition.getRequisitionStatus().toString().equals("MATERIAL_RECEIVED"))
            throw new InvalidInputException("Material already received");
        if(materialRequisition.getRequisitionStatus().toString().equals("CANCELLED"))
            throw new InvalidInputException("Requisition already cancelled");
        //send mail *******************
        materialRequisition.setRequisitionStatus(RequisitionStatus.PROCESSED);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }


    public List<MaterialRequisitionResponse> followUpOnAllElapsedRequisition() {

        Date currDate = Date.valueOf(LocalDate.now());
        List<MaterialRequisition> elapsedRequisitionList = materialRequisitionRepository.getElapsedRequisitionList(currDate);

        for (MaterialRequisition curr : elapsedRequisitionList) {
            //send mail ********************
        }

        List<MaterialRequisitionResponse> responses = new ArrayList<>();
        for(MaterialRequisition curr : elapsedRequisitionList)
            responses.add(MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(curr));

        return responses;
    }


    public MaterialRequisitionResponse changeExpectedDeliveryDate(String requisitionId, Date newDate) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        if(materialRequisition.getRequisitionStatus().toString().equals("MATERIAL_RECEIVED"))
            throw new InvalidInputException("Material already received");
        if(materialRequisition.getRequisitionStatus().toString().equals("CANCELLED"))
            throw new InvalidInputException("Requisition already cancelled");
        Date oldDate = materialRequisition.getExpectingDeliveryDate();
        materialRequisition.setExpectingDeliveryDate(newDate);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        //send mail ********************
        return MaterialRequisitionTransformer.materialRequisitionToMaterialRequisitionResponse(savedMaterialRequisition);
    }

    public MaterialRequisitionResponse changeRequisitionQuantity(String requisitionId, double newQuantity) {
        MaterialRequisition materialRequisition = materialRequisitionRepository.findByRequisitionId(requisitionId);
        if(materialRequisition.getRequisitionStatus().toString().equals("MATERIAL_RECEIVED"))
            throw new InvalidInputException("Material already received");
        if(materialRequisition.getRequisitionStatus().toString().equals("CANCELLED"))
            throw new InvalidInputException("Requisition already cancelled");
        double oldQuantity = materialRequisition.getRequisitionQuantity();
        materialRequisition.setRequisitionQuantity(newQuantity);
        MaterialRequisition savedMaterialRequisition = materialRequisitionRepository.save(materialRequisition);
        //send mail ***************
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
        List<MaterialRequisition> materialRequisitionList = materialRequisitionRepository.getAllMaterialRequisitionByMaterialName(materialName.toUpperCase());
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