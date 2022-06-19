package com.example.vetmobile.DateBase.Model;

public class JSONResponseShow {
    private UserModel User;
    private AnimailModel Animal;
    private ClinicModel Clinic;
    private ServiceModel Service;
    private DoctorModel Doctor;
    private TimeModel Time;
    private RenderModel Render;

    public RenderModel getRender() {
        return Render;
    }

    public TimeModel getTime() { return Time; }

    public AnimailModel getAnimal() { return Animal; }

    public UserModel getUser() {
        return User;
    }

    public DoctorModel getDoctor() {
        return Doctor;
    }

    public ServiceModel getService() {
        return Service;
    }

    public ClinicModel getClinic() {
        return Clinic;
    }

}
