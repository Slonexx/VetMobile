package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vetmobile.Auth.LoginActivity;
import com.example.vetmobile.DateBase.Adaptery;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.DoctorModel;
import com.example.vetmobile.DateBase.Model.JSONResponse;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.RenderModel;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.Model.TimeModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.example.vetmobile.DateBase.ServiceAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {

   private SharedPreferences sPref;
   private SaveDateSharedPreferences sharedPreferences;
   private int User_id;

   private ConstraintLayout ServiceLayoutLast;
   private ProgressBar ServiceProgressBar;

    private List<AnimailModel> AnimailModel = new ArrayList<>();
    private List<RenderModel> RenderModel = new ArrayList<>();
    private List<ClinicModel> ClinicModel = new ArrayList<>();
    private List<ServiceModel> ServiceModel = new ArrayList<>();
    private List<DoctorModel> DoctorModel = new ArrayList<>();
    private List<TimeModel> TimeModel = new ArrayList<>();

   private RecyclerView recyclerViewService;

   private ImageView btn_service_to_profile, btn_service_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setFindViewById();

        User_id = sharedPreferences.getSPrefUserId(sPref, User_id);
        getUserDate(User_id);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ServiceProgressBar.setVisibility(View.GONE);
                ServiceLayoutLast.setVisibility(View.VISIBLE);
                PutDataIntoRecyclerView(AnimailModel, RenderModel, ClinicModel, ServiceModel, DoctorModel, TimeModel);
            }
        },5000);

    }

    private void PutDataIntoRecyclerView(List<AnimailModel> animailModel, List<RenderModel> renderModel,
                                         List<ClinicModel> clinicModel, List<ServiceModel> serviceModel,
                                         List<DoctorModel> doctorModel, List<TimeModel> timeModel) {
        ServiceAdapter serviceAdapter = new ServiceAdapter(ServiceActivity.this, animailModel, renderModel, clinicModel, serviceModel, doctorModel, timeModel);
        recyclerViewService.setLayoutManager(new LinearLayoutManager(ServiceActivity.this));
        recyclerViewService.setAdapter(serviceAdapter);
    }

    private void getAnimalDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getAnimalShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                AnimailModel model = response.body().getAnimal();
                RenderModel[] Models = model.getRender().toArray(new RenderModel[0]);

                for (RenderModel renderModel : Models){
                    RenderModel.add(renderModel);
                    getTimeDate(renderModel.getTime_Of_Receipts_id());
                    getDoctorDate(renderModel.getDoctor_id());
                    getServiceDate(renderModel.getService_id());
                }

            }
            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {

            }
        });
    }

    private void getClinicDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getClinicShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ClinicModel.add(response.body().getClinic());
            }
            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getServiceDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getServiceShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ServiceModel.add(response.body().getService());

                ServiceModel model = response.body().getService();
                getClinicDate(model.getClinic_id());


            }
            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getDoctorDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getDoctorShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                DoctorModel.add(response.body().getDoctor());
            }
            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getTimeDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getTimeShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                TimeModel.add(response.body().getTime());
            }
            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getUserDate(int id){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();
                AnimailModel[] Models = model.getAnimal().toArray(new AnimailModel[0]);
                 for (AnimailModel animailModel : Models){
                     AnimailModel.add(animailModel);
                     getAnimalDate(animailModel.getId());
                 }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {

            }
        });
    }

    private void setFindViewById() {

         sPref = getSharedPreferences("User", 0);
         sharedPreferences = new SaveDateSharedPreferences();
         User_id = 0;

        ServiceLayoutLast = findViewById(R.id.ServiceLayoutLast);
        ServiceLayoutLast.setVisibility(View.GONE);
        ServiceProgressBar = findViewById(R.id.ServiceProgressBar);
        ServiceProgressBar.setVisibility(View.VISIBLE);

        recyclerViewService = findViewById(R.id.recyclerViewService);

        btn_service_to_profile = findViewById(R.id.button_service_to_profile);
        btn_service_to_main = findViewById(R.id.button_service_to_main);

        btn_service_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_service_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}