package com.example.clevervision.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="CompletedTravels_table")
public class CompletedTravelModel {
@Id
    Integer id ;
    LocalTime HeureDepart;

    LocalTime HeureArrive;

    Integer destination;

     Integer busMat;
     Integer driverId;
public CompletedTravelModel(){

}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHeureDepart() {
        return HeureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        HeureDepart = heureDepart;
    }

    public LocalTime getHeureArrive() {
        return HeureArrive;
    }

    public void setHeureArrive(LocalTime heureArrive) {
        HeureArrive = heureArrive;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getBusMat() {
        return busMat;
    }

    public void setBusMat(Integer busMat) {
        this.busMat = busMat;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }
}
