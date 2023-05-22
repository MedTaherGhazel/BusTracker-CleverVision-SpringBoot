package com.example.clevervision.repository;

import com.example.clevervision.model.BusModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.TravelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelsRepository extends JpaRepository<TravelModel,Integer> {
TravelModel findFirstByEnRoute(int x);
    TravelModel findFirstById(int x);
    List<TravelModel> findAllByDriverId(int id);

    List<TravelModel> findAllByEnRoute(int n);

    void  deleteByIdAndBusAndDriver(int voyId , BusModel busId , UsersModel driverId);

}
