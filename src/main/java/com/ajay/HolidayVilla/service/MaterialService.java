package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.MaterialTransformer;
import com.ajay.HolidayVilla.dto.request.MaterialRequest;
import com.ajay.HolidayVilla.dto.request.MaterialRequisitionRequest;
import com.ajay.HolidayVilla.dto.response.MaterialRequisitionResponse;
import com.ajay.HolidayVilla.dto.response.MaterialResponse;
import com.ajay.HolidayVilla.model.Material;
import com.ajay.HolidayVilla.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;


    public MaterialResponse addMaterial(MaterialRequest materialRequest) {
        Material material = materialRepository.save(MaterialTransformer.materialRequestToMaterial(materialRequest));
        return MaterialTransformer.materialToMaterialResponse(material);
    }

    public MaterialResponse editSupplierEmail(String materialName, String newEmail) {
        Material material = materialRepository.findByMaterialName(materialName);
        material.setSupplierEmail(newEmail);
        return MaterialTransformer.materialToMaterialResponse(material);
    }

    public MaterialResponse editSupplierName(String materialName, String newName) {
        Material material = materialRepository.findByMaterialName(materialName);
        material.setSupplierName(newName);
        return MaterialTransformer.materialToMaterialResponse(material);
    }

    public MaterialResponse editPrice(String materialName, double newPrice) {
        Material material = materialRepository.findByMaterialName(materialName);
        material.setPrice(newPrice);
        return MaterialTransformer.materialToMaterialResponse(material);
    }

}
