package com.example.vetmobile.DateBase.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicModel {

    private int id;
    private String Name_Clinic;
    private String Address;
    private String photo_id;
    @SerializedName("Service")
    private List<ServiceModel> Service;

    public List<ServiceModel> getService() {
        return Service;
    }

    public int getId() {
        return id;
    }

    public String getName_Clinic() {
        return Name_Clinic;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoto_id() {
        return photo_id;
    }


}
