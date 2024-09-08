package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.dto.request.MaterialRequest;
import com.ajay.HolidayVilla.dto.response.MaterialResponse;
import com.ajay.HolidayVilla.model.Material;

public class MaterialTransformer {

    public static Material materialRequestToMaterial(MaterialRequest materialRequest){
        return Material.builder()
                .supplierName(materialRequest.getSupplierName().toUpperCase())
                .materialName(materialRequest.getMaterialName().toUpperCase())
                .price(materialRequest.getPrice())
                .supplierEmail(materialRequest.getSupplierEmail().toLowerCase())
                .build();
    }

    public static MaterialResponse materialToMaterialResponse(Material material){
        return MaterialResponse.builder()
                .supplierEmail(material.getSupplierEmail())
                .materialName(material.getMaterialName())
                .price(material.getPrice())
                .supplierName(material.getSupplierName())
                .build();
    }
}
