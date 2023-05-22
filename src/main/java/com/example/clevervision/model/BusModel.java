package com.example.clevervision.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Bus_table")
public class BusModel {
    @Id
    int mat ;
    String marque ;
    Boolean dispo ;
    @Override
    public String toString() {
        return "BusModel{" +
                "mat=" + mat +
                ", marque='" + marque + '\'' +
                ", dispo=" + dispo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusModel busModel = (BusModel) o;
        return mat == busModel.mat && Objects.equals(marque, busModel.marque) && Objects.equals(dispo, busModel.dispo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mat, marque, dispo);
    }

    public int getMat() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat = mat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Boolean getDispo() {
        return dispo;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }
}
