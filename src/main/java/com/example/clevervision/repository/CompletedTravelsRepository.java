package com.example.clevervision.repository;

import com.example.clevervision.model.CompletedTravelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedTravelsRepository extends JpaRepository<CompletedTravelModel,Integer> {
    CompletedTravelModel findById(int id);
}
