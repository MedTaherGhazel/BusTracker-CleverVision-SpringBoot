package com.example.clevervision.repository;

import com.example.clevervision.model.BusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarageRepository  extends JpaRepository<BusModel,Integer> {

    BusModel findFirstByMat(int mat );
    List<BusModel> findAllByDispo(boolean dipo);
}
