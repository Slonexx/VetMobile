package com.example.vetmobile.DateBase.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorModel {

    private int id;
    private String Name_Doctor;
    private String Speciality;
    private String photo_id;
    private String Service_id;
    @SerializedName("Time")
    private List<TimeModel> Time;

    public List<TimeModel> getTime() {
        return Time;
    }

    public int getId() {
        return id;
    }

    public String getName_Doctor() {
        return Name_Doctor;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public String getService_id() {
        return Service_id;
    }
}
