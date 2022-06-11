package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vetmobile.DateBase.Adaptery;
import com.example.vetmobile.DateBase.AnimalAdapter;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.JSONResponse;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;
    private int User_id;

    private RecyclerView AnimalRecyclerView;
    List<AnimailModel> animailModels = new ArrayList<>();
    private ImageView iv_info_new_animal_card, btn_profile_to_service, btn_profile_to_main, button_profile_to_profile;
    private Button btn_new_animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        setFindViewById();
        User_id = sharedPreferences.getSPrefUserId(sPref, User_id);
        getAnimals(User_id);

        iv_info_new_animal_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(AnimalActivity.this).create();
                alertDialog.setTitle("Информация");
                alertDialog.setMessage("Создает новую карточку животного в которой будет хрониться инфомация о нем и назначение");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which){
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btn_new_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AnimalActivity.this, "Новая карта", Toast.LENGTH_LONG).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PutDataIntoRecyclerView(animailModels);
            }
        },1000);


    }

    private void PutDataIntoRecyclerView(List<AnimailModel> animal) {
        AnimalAdapter animalAdapter = new AnimalAdapter(AnimalActivity.this, animal);
        AnimalRecyclerView.setLayoutManager(new LinearLayoutManager(AnimalActivity.this));
        AnimalRecyclerView.setAdapter(animalAdapter);
    }

    private void getAnimals(int id){

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
         call.enqueue(new Callback<JSONResponseShow>() {
             @Override
             public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                 UserModel model = response.body().getUser();
                 AnimailModel[] Models = model.getAnimal().toArray(new AnimailModel[0]);
                 for (AnimailModel animal : Models){
                     animailModels.add(animal);
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

        btn_new_animal = findViewById(R.id.btn_new_animal);
        iv_info_new_animal_card = findViewById(R.id.iv_info_new_animal_card);
        AnimalRecyclerView = findViewById(R.id.AnimalRecyclerView);

        btn_profile_to_service = findViewById(R.id.button_profile_to_service);
        btn_profile_to_main = findViewById(R.id.button_profile_to_main);
        button_profile_to_profile = findViewById(R.id.button_profile_to_profile);
        btn_profile_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimalActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        btn_profile_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button_profile_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimalActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}