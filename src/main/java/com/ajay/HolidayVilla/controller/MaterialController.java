package com.ajay.HolidayVilla.controller;

import com.ajay.HolidayVilla.dto.request.MaterialRequest;
import com.ajay.HolidayVilla.dto.response.MaterialResponse;
import com.ajay.HolidayVilla.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    @PostMapping("/addMaterial")
    public ResponseEntity addMaterial(@RequestBody MaterialRequest materialRequest){
        MaterialResponse materialResponse = materialService.addMaterial(materialRequest);
        return new ResponseEntity<>(materialResponse, HttpStatus.CREATED);
    }

    @PutMapping("edit-supplier-email")
    public ResponseEntity editSupplierEmail(@RequestParam String materialName, @RequestParam String newEmail){
        MaterialResponse materialResponse = materialService.editSupplierEmail(materialName.toUpperCase(), newEmail);
        return new ResponseEntity(materialResponse, HttpStatus.OK);
    }

    @PutMapping("edit-supplier-name")
    public ResponseEntity editSupplierName(@RequestParam String materialName, @RequestParam String newName){
        MaterialResponse materialResponse = materialService.editSupplierName(materialName.toUpperCase(), newName.toUpperCase());
        return new ResponseEntity(materialResponse, HttpStatus.OK);
    }

    @PutMapping("edit-price")
    public ResponseEntity editPrice(@RequestParam String materialName, @RequestParam double newPrice){
        MaterialResponse materialResponse = materialService.editPrice(materialName.toUpperCase(), newPrice);
        return new ResponseEntity(materialResponse, HttpStatus.OK);
    }
}
