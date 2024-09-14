package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.Enum.Department;
import com.ajay.HolidayVilla.dto.request.MaterialRequisitionRequest;
import com.ajay.HolidayVilla.dto.response.MaterialRequisitionResponse;
import com.ajay.HolidayVilla.service.MaterialRequisitionService;
import com.ajay.HolidayVilla.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ReactiveOffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/material-requisition")
public class MaterialRequisitionController {

    @Autowired
    MaterialRequisitionService materialRequisitionService;



    @PostMapping("/raise-requisition")
    public ResponseEntity raiseMaterialRequisition(@RequestBody MaterialRequisitionRequest materialRequisitionRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String staffEmail = userDetails.getUsername();
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.raiseMaterialRequisition(materialRequisitionRequest, staffEmail);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }


    @PutMapping("/cancel-requisition")
    public ResponseEntity cancelRequisition(@RequestParam String requisitionId, @AuthenticationPrincipal UserDetails userDetails){
        String staffEmail = userDetails.getUsername();
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.cancelRequisition(requisitionId, staffEmail);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }



    @PutMapping("/mark-received-by-requisitionId")
    public ResponseEntity markReceivedByRequisitionId(@RequestParam String requisitionId){
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.markReceivedByRequisitionId(requisitionId);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @PutMapping("/process-requisition-by-requisitionId")
    public ResponseEntity processRequisitionByRequisitionId(@RequestParam String requisitionId){
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.processRequisitionByRequisitionId(requisitionId);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/followUp-on-all-elapsed-requisition")
    public ResponseEntity followUpOnAllElapsedRequisition(){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.followUpOnAllElapsedRequisition();
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }





    @PutMapping("/change-expected-delivery-date")
    public ResponseEntity changeExpectedDeliveryDate(@RequestParam String requisitionId, @RequestParam Date newDate){
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.changeExpectedDeliveryDate(requisitionId, newDate);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @PutMapping("/change-requisition-quantity")
    public ResponseEntity changeRequisitionQuantity(@RequestParam String requisitionId, @RequestParam double newQuantity){
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.changeRequisitionQuantity(requisitionId, newQuantity);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }






    @GetMapping("/get-material-requisition-by-requisitionId")
    public ResponseEntity getMaterialRequisitionByRequisitionId(@RequestParam String requisitionId){
        MaterialRequisitionResponse materialRequisitionResponse = materialRequisitionService.getMaterialRequisitionByRequisitionId(requisitionId);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }




    @GetMapping("/get-all-inprogress-material-requisition")
    public ResponseEntity getAllInProgressMaterialRequisition(){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllInProgressMaterialRequisition();
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-inprogress-material-requisition-by-department")
    public ResponseEntity getAllInProgressMaterialRequisitionByDepartment(@RequestParam Department department){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllInProgressMaterialRequisitionByDepartment(department.toString());
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }




    @GetMapping("/get-all-not-processed-material-requisition")
    public ResponseEntity getAllNotProcessedMaterialRequisition(){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllNotProcessedMaterialRequisition();
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-not-processed-material-requisition-by-department")
    public ResponseEntity getAllNotProcessedMaterialRequisitionByDepartment(@RequestParam Department department){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllNotProcessedMaterialRequisitionByDepartment(department.toString());
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }


    @GetMapping("/get-all-cancelled-material-requisition")
    public ResponseEntity getAllCancelledMaterialRequisition(){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllCancelledMaterialRequisition();
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-cancelled-material-requisition-by-department")
    public ResponseEntity getAllCancelledMaterialRequisitionByDepartment(@RequestParam Department department){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllCancelledMaterialRequisitionByDepartment(department.toString());
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }


    @GetMapping("/get-all-material-requisition-by-deliveryDate")
    public ResponseEntity getAllMaterialRequisitionByDeliveryDate(@RequestParam Date date){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllMaterialRequisitionByDeliveryDate(date);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-material-requisition-between-deliveryDate")
    public ResponseEntity getAllMaterialRequisitionBetweenDeliveryDate(@RequestParam Date fromDate, @RequestParam Date toDate){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllMaterialRequisitionBetweenDeliveryDate(fromDate, toDate);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }




    @GetMapping("/get-all-material-requisition-by-materialName")
    public ResponseEntity getAllMaterialRequisitionByMaterialName(@RequestParam String materialName){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllMaterialRequisitionByMaterialName(materialName);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-material-requisition-by-staffEmail")
    public ResponseEntity getAllMaterialRequisitionByStaffEmail(@RequestParam String staffEmail){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllMaterialRequisitionByStaffEmail(staffEmail);
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-received-material-requisition-by-department")
    public ResponseEntity getAllReceivedMaterialRequisitionByDepartment(@RequestParam Department department){
        List<MaterialRequisitionResponse> materialRequisitionResponse = materialRequisitionService.getAllReceivedMaterialRequisitionByDepartment(department.toString());
        return new ResponseEntity(materialRequisitionResponse, HttpStatus.OK);
    }




}
