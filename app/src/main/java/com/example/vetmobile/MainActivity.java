package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vetmobile.DateBase.Adaptery;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.JSONResponse;
import com.example.vetmobile.DateBase.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ConstraintLayout MainLayoutClinic;
    private ProgressBar MainProgressBarClinic;

    private RecyclerView recyclerView;
    private ImageView btn_main_to_profile, btn_main_to_service;
    private List<ClinicModel> clinicLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFindViewById();
        getClinicData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainProgressBarClinic.setVisibility(View.GONE);
                MainLayoutClinic.setVisibility(View.VISIBLE);
                PutDataIntoRecyclerView(clinicLists);
            }
        },1000);

    }

    private void PutDataIntoRecyclerView(List<ClinicModel> clinicLists) {
        Adaptery adaptery = new Adaptery(MainActivity.this, clinicLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adaptery);
    }

    private void getClinicData(){
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponse> call = apiService.getClinicList();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.code() != 200){return;}

                JSONResponse jsonResponse = response.body();
                clinicLists = new ArrayList<>(Arrays.asList(jsonResponse.getClinic()));


            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }


    private void setFindViewById(){
        MainLayoutClinic = findViewById(R.id.MainLayoutClinic);
        MainProgressBarClinic = findViewById(R.id.MainProgressBarClinic);
        MainLayoutClinic.setVisibility(View.GONE);
        MainProgressBarClinic.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recyclerView);
        btn_main_to_profile = findViewById(R.id.button_main_to_profile);
        btn_main_to_service = findViewById(R.id.button_main_to_service);

        btn_main_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_main_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
    }

}