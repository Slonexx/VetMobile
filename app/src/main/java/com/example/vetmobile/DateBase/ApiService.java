package com.example.vetmobile.DateBase;

import com.example.vetmobile.DateBase.Model.JSONResponse;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Required.AnimalRequired;
import com.example.vetmobile.DateBase.Required.LoginRequired;
import com.example.vetmobile.DateBase.Required.RegisterRequired;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.Required.RenderRequired;
import com.example.vetmobile.DateBase.Required.UserRequired;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    // https://192.168.8.183/API/V1/  User

            @POST("/API/V1/Login")
   public Call<UserModel> userLogin(@Body LoginRequired required);

            @POST("/API/V1/Register")
    public Call<UserModel> userRegister(@Body RegisterRequired required);

            @GET("/API/V1/User/{id}")
    public Call<JSONResponseShow> getUserShow(@Path("id") int postId);

            @PUT("/API/V1/User/{id}")
    public Call<JSONResponseShow> setChangeUser(@Body UserRequired required, @Path("id") int postId);

            @Multipart
            @POST("/API/V1/ChangeFileUser/{id}")
    public Call<ResponseBody> ChangeImageUser(@Path("id") int postId, @Part MultipartBody.Part image);




            @GET("/API/V1/Animal/{id}")
    public Call<JSONResponseShow> getAnimalShow(@Path("id") int postId);

            @POST("/API/V1/Animal")
    public Call<JSONResponseShow> setAnimalCreate(@Body AnimalRequired required);

            @PUT("/API/V1/Animal/{id}")
    public Call<JSONResponseShow> setAnimalChange(@Body AnimalRequired required, @Path("id") int postId);


            @GET("/API/V1/Clinic")
    public Call<JSONResponse> getClinicList();

           @GET("/API/V1/Clinic/{id}")
    public Call<JSONResponseShow> getClinicShow(@Path("id") int postId);


           @GET("/API/V1/Service/{id}")
    public Call<JSONResponseShow> getServiceShow(@Path("id") int postId);


            @GET("/API/V1/Doctor/{id}")
    public Call<JSONResponseShow> getDoctorShow(@Path("id") int postId);


            @GET("/API/V1/Time/{id}")
    public Call<JSONResponseShow> getTimeShow(@Path("id") int postId);


            @POST("/API/V1/Render")
    public Call<JSONResponseShow> setRender(@Body RenderRequired required);
}
