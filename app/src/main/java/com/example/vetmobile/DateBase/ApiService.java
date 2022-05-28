package com.example.vetmobile.DateBase;


import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.JSONResponse;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.LoginRequired;
import com.example.vetmobile.DateBase.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // https://192.168.8.183/API/V1/  User

          @POST("/API/V1/Login")
   public Call<UserModel> userLogin(@Body LoginRequired required);

          @GET("/API/V1/User/{id}")
    public Call<JSONResponseShow> getUserShow(@Path("id") int postId);

           @GET("/API/V1/Clinic")
    public Call<JSONResponse> getClinicList();

           @GET("/API/V1/Clinic/{id}")
    public Call<JSONResponseShow> getClinicShow(@Path("id") int postId);

           @GET("/API/V1/Service/{id}")
    public Call<JSONResponseShow> getServiceShow(@Path("id") int postId);

            @GET("/API/V1/Doctor/{id}")
    public Call<JSONResponseShow> getDoctorShow(@Path("id") int postId);

}
