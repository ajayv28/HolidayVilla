package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.Enum.RequisitionStatus;
import com.ajay.HolidayVilla.dto.request.MaterialRequisitionRequest;
import com.ajay.HolidayVilla.dto.response.MaterialRequisitionResponse;
import com.ajay.HolidayVilla.model.MaterialRequisition;

import java.util.UUID;

public class MaterialRequisitionTransformer {

    public static MaterialRequisition materialRequisitionRequestToMaterialRequisition(MaterialRequisitionRequest materialRequisitionRequest){
        return MaterialRequisition.builder()
                .requisitionMaterial(materialRequisitionRequest.getRequisitionMaterial())
                .requisitionId(String.valueOf(UUID.randomUUID()))
                .expectingDeliveryDate(materialRequisitionRequest.getExpectingDeliveryDate())
                .requisitionQuantity(materialRequisitionRequest.getRequisitionQuantity())
                .department(materialRequisitionRequest.getDepartment())
                .requisitionStatus(RequisitionStatus.REQUISITION_RECEIVED)
                .build();
    }

    public static MaterialRequisitionResponse materialRequisitionToMaterialRequisitionResponse(MaterialRequisition materialRequisition){
        return MaterialRequisitionResponse.builder()
                .materialResponse(MaterialTransformer.materialToMaterialResponse(materialRequisition.getRequisitionMaterial()))
                .dateOfRequisition(materialRequisition.getDateOfRequisition())
                .requisitionId(materialRequisition.getRequisitionId())
                .department(materialRequisition.getDepartment())
                .expectingDeliveryDate(materialRequisition.getExpectingDeliveryDate())
                .requisitionQuantity(materialRequisition.getRequisitionQuantity())
                .staffResponse(StaffTransformer.staffToStaffResponse(materialRequisition.getRequisitionStaff()))
                .transactionResponse(TransactionTransformer.transactionToTransactionResponse(materialRequisition.getTransaction()))
                .build();
    }
}
