package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.Auth.RegisterActivity;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.DoctorModel;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.RenderModel;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.Model.TimeModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.Required.RegisterRequired;
import com.example.vetmobile.DateBase.Required.RenderRequired;
import com.example.vetmobile.DateBase.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;
    private int User_id;

    private TextView tvNameTicket, tv_ticket_name, tv_ticket_address, tv_ticket_service, tv_ticket_doctor, tv_ticket_time, tvNomerRender;
    private ImageView iv_ticket_clinic, image_vetmobile,
            button_render_to_service, button_render_to_main, button_render_to_profile;

    private int outAnimalId, outClinicId, outServiceId, outDoctorId, outTimeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        SetAllPiker();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                renderRequired();
            }
        },500);



    }

    private void renderRequired() {
        RenderRequired required = new RenderRequired();
        required.setAnimal_card_id(String.valueOf(outAnimalId));
        required.setService_id(String.valueOf(outServiceId));
        required.setDoctor_id(String.valueOf(outDoctorId));
        required.setTime_Of_Receipts_id(String.valueOf(outTimeId));

        Call<JSONResponseShow> call = RetrofitClient.getApiService().setRender(required);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                if (response.isSuccessful()) {
                    RenderModel model = response.body().getRender();
                    tvNomerRender.setText("№ "+ String.valueOf(model.getId()));
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {

            }
        });

    }

    private void SetAllPiker() {

        sPref = getSharedPreferences("User", 0);
            sharedPreferences = new SaveDateSharedPreferences();
            User_id = 0;
            User_id = sharedPreferences.getSPrefUserId(sPref, User_id);
                getUser(User_id);

        image_vetmobile = findViewById(R.id.image_vetmobile);

        Glide.with(TicketActivity.this)
                .load(R.drawable.ic_vetmobile_big)
                .circleCrop()
                .into(image_vetmobile);

        tvNameTicket = findViewById(R.id.tvNameTicket);
        tvNomerRender = findViewById(R.id.tvNomerRender);
        tv_ticket_name = findViewById(R.id.tv_ticket_name);
        tv_ticket_address = findViewById(R.id.tv_ticket_address);
        tv_ticket_service = findViewById(R.id.tv_ticket_service);
        tv_ticket_doctor = findViewById(R.id.tv_ticket_doctor);
        tv_ticket_time = findViewById(R.id.tv_ticket_time);

        iv_ticket_clinic = findViewById(R.id.iv_ticket_clinic);


            outAnimalId = getIntent().getIntExtra("outAnimalId", -1);
            outClinicId = getIntent().getIntExtra("outClinicId", -1);
                getClinic(outClinicId);
            outServiceId = getIntent().getIntExtra("outServiceId", -1);
                getService(outServiceId);
            outDoctorId = getIntent().getIntExtra("outDoctorId", -1);
                getDoctor(outDoctorId);
            outTimeId = getIntent().getIntExtra("outTimeId",-1);
                getTime(outTimeId);


        button_render_to_service = findViewById(R.id.button_render_to_service);
        button_render_to_main = findViewById(R.id.button_render_to_main);
        button_render_to_profile = findViewById(R.id.button_render_to_profile);

            button_render_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
            button_render_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
            button_render_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUser(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();
                tvNameTicket.setText(model.getName());
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getClinic(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getClinicShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ClinicModel model = response.body().getClinic();
                tv_ticket_address.setText(model.getAddress());
                tv_ticket_name.setText(model.getName_Clinic());
                Glide.with(TicketActivity.this)
                        .load(model.getPhoto_id())
                        .circleCrop()
                        .error(R.drawable.ic_vetmobile_big)
                        .into(iv_ticket_clinic);
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getService(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getServiceShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ServiceModel model = response.body().getService();
                tv_ticket_service.setText(model.getName_Service());
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getDoctor(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getDoctorShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                DoctorModel model = response.body().getDoctor();
                tv_ticket_doctor.setText(model.getName_Doctor());
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getTime(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getTimeShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                TimeModel model = response.body().getTime();
                tv_ticket_time.setText(model.getReceipt_Date() + " В " + model.getTime());
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

}