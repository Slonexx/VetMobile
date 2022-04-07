package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

    RecyclerView recyclerView;
    ImageView btn_main_to_profile, btn_main_to_service;
    List<ClinicModel> clinicLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btn_main_to_profile = findViewById(R.id.button_main_to_profile);
        btn_main_to_service = findViewById(R.id.button_main_to_service);
        clinicLists = new ArrayList<>();

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Call<JSONResponse> call = apiService.getClinicList();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.code() != 200){return;}

                JSONResponse jsonResponse = response.body();
                clinicLists = new ArrayList<>(Arrays.asList(jsonResponse.getClinic()));
                PutDataIntoRecyclerView(clinicLists);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });


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

    private void PutDataIntoRecyclerView(List<ClinicModel> clinicLists) {
        Adaptery adaptery = new Adaptery(MainActivity.this, clinicLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adaptery);
    }
}