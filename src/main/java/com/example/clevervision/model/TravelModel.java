package com.example.clevervision.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="voyage_table")
public class TravelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id ;
    LocalTime HeureDepart;

    LocalTime HeureArrive;

    Integer enRoute ;
    Integer busPosition;

    Integer destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus")
    private BusModel bus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver")
    UsersModel driver;

    @Override
    public String toString() {
        return "VoyageModel{" +
                "id=" + id +
                ", HeureDepart=" + HeureDepart +
                ", HeureArrive=" + HeureArrive +
                ", enRoute=" + enRoute +
                ", busPosition=" + busPosition +
                ", destination=" + destination +
                ", busId=" + bus.getMat() +
                ", driverId=" + driver +
                '}';
    }

    public BusModel getBus() {
        return bus;
    }

    public void setBus(BusModel bus) {
        this.bus = bus;
    }


    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }


    public UsersModel getDriver() {
        return driver;
    }

    public void setDriver(UsersModel driverId) {
        this.driver = driverId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBusPosition() {
        return busPosition;
    }

    public void setBusPosition(Integer busPosition) {
        this.busPosition = busPosition;
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

    public Integer getEnRoute() {
        return enRoute;
    }


    public void setEnRoute(Integer enRoute) {
        this.enRoute = enRoute;
    }

    public LocalTime getHeureArrive() {
        return HeureArrive;
    }

    public void setHeureArrive(LocalTime heureArrive) {
        HeureArrive = heureArrive;
    }



}
