package com.ajay.HolidayVilla.repository;

import com.ajay.HolidayVilla.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    public Material findByMaterialName(String materialName);
}
