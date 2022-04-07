package com.example.vetmobile.DateBase.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceModel {

    private int id;
    private String Name_Service;
    private String Descriptions;
    private int Clinic_id;
    @SerializedName("Doctor")
    private List<DoctorModel> Doctor;

    public List<DoctorModel> getDoctor() {
        return Doctor;
    }

    public int getId() {
        return id;
    }

    public String getName_Service() {
        return Name_Service;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public int getClinic_id() {
        return Clinic_id;
    }
}
