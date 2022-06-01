package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.Auth.LoginActivity;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.RenderModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;
    private int User_id;

    private int QTAnimals, QTRender;
    private ConstraintLayout ProfileLayoutDate;
    private ProgressBar ProfileProgressBar;

    private ImageView imgProfilePhoto, imgProfileAnimals, imgProfileRender, imgProfileClinic, btn_profile_to_service, btn_profile_to_main;
    private TextView tvProfileName, tvProfilePhone, tvProfileEmail, tvProfileAnimals, tvProfileRender, tvProfileClinic;
    private Button btnProfileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setFindViewById();

        User_id = sharedPreferences.getSPrefUserId(sPref, User_id);
        getUser(User_id);
        getUserDate(User_id);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProfileLayoutDate.setVisibility(View.VISIBLE);
                ProfileProgressBar.setVisibility(View.GONE);

                tvProfileAnimals.setText(String.valueOf(QTAnimals));
                tvProfileRender.setText(String.valueOf(QTRender));
                imgProfileRender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProfileActivity.this, ServiceActivity.class);
                        startActivity(intent);
                    }
                });
            }
        },3500);

    }

    private void getUser(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();

                Glide.with(ProfileActivity.this)
                        .load(model.getPhoto_id())
                        .circleCrop()
                        .error(R.drawable.ic_profile)
                        .into(imgProfilePhoto);

                tvProfileName.setText(model.getName());
                tvProfilePhone.setText(model.getPhone());
                tvProfileEmail.setText(model.getEmail());

            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void getUserDate(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();
                AnimailModel[] Models = model.getAnimal().toArray(new AnimailModel[0]);
                for (AnimailModel animailModel : Models){
                    QTAnimals++;
                    getAnimalDate(animailModel.getId());
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
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
                    QTRender++;
                    //Toast.makeText(ProfileActivity.this, String.valueOf(QTRender), Toast.LENGTH_LONG).show();
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
        QTAnimals = 0;
        QTRender = 0;

        ProfileLayoutDate = findViewById(R.id.ProfileLayoutDate);
        ProfileProgressBar = findViewById(R.id.ProfileProgressBar);
        ProfileLayoutDate.setVisibility(View.GONE);
        ProfileProgressBar.setVisibility(View.VISIBLE);

        imgProfilePhoto = findViewById(R.id.imgProfilePhoto);
        imgProfileAnimals = findViewById(R.id.imgProfileAnimals);
        imgProfileClinic = findViewById(R.id.imgProfileClinic);
        imgProfileRender = findViewById(R.id.imgProfileRender);

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfilePhone = findViewById(R.id.tvProfilePhone);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileAnimals = findViewById(R.id.tvProfileAnimals);
        tvProfileRender = findViewById(R.id.tvProfileRender);
        tvProfileClinic = findViewById(R.id.tvProfileClinic);

        btnProfileEdit = findViewById(R.id.btnProfileEdit);

        btn_profile_to_service = findViewById(R.id.button_profile_to_service);
        btn_profile_to_main = findViewById(R.id.button_profile_to_main);
        btn_profile_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        btn_profile_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}