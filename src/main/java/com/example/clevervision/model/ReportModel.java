package com.example.clevervision.model;

import com.example.clevervision.repository.CompletedTravelsRepository;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="report_table")
public class ReportModel {
    @GeneratedValue
    @Id
    int id ;
    LocalTime PassedTravelTime;

    public LocalTime getPassedTravelTime() {
        return PassedTravelTime;
    }

    public void setPassedTravelTime(LocalTime passedTravelTime) {
        PassedTravelTime = passedTravelTime;
    }

    String Reportername;
    int type;
    String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getReportername() {
        return Reportername;
    }

    public void setReportername(String reportername) {
        Reportername = reportername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
