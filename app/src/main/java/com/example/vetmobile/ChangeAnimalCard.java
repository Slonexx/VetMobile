package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vetmobile.Auth.LoginActivity;
import com.example.vetmobile.Auth.RegisterActivity;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.Required.AnimalRequired;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAnimalCard extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;
    private int User_id;

    private String Nickname_Animal;
    private String Type_Animal;
    private String Age_Animal;
    private int Animal_id;

    private TextView tv_info_change;
    private TextInputEditText etNickname_Animal, etType_Animal, etAge_Animal;
    private ImageView render_to_service, render_to_main, render_to_profile;
    private Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_animal_card);
        setFindViewById();
        CheckExtra();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNickname_Animal.getText().toString()) ||
                        TextUtils.isEmpty(etNickname_Animal.getText().toString()) ||
                        TextUtils.isEmpty(etAge_Animal.getText().toString())){
                    Toast.makeText(ChangeAnimalCard.this,"Введите данные !", Toast.LENGTH_LONG).show();
                }else {
                    ChangeRequired();
                }
            }
        });




    }

    private void ChangeRequired(){
        AnimalRequired animalRequired = new AnimalRequired();
        animalRequired.setNickname_Animal(etNickname_Animal.getText().toString());
        animalRequired.setType_Animal(etType_Animal.getText().toString());
        animalRequired.setAge_Animal(etAge_Animal.getText().toString());
        if (getIntent().getStringExtra("Date") == "Изменение") {
            Call<JSONResponseShow> call = RetrofitClient.getApiService().setAnimalChange(animalRequired, Animal_id);
            call.enqueue(new Callback<JSONResponseShow>() {
                @Override
                public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangeAnimalCard.this, "Карточка питомца изменина !", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(ChangeAnimalCard.this, ProfileActivity.class));
                            }
                        },500);
                    }
                }

                @Override
                public void onFailure(Call<JSONResponseShow> call, Throwable t) {
                }
            });
        } else {
            animalRequired.setUser_id(String.valueOf(User_id));
            Call<JSONResponseShow> call = RetrofitClient.getApiService().setAnimalCreate(animalRequired);
            call.enqueue(new Callback<JSONResponseShow>() {
                @Override
                public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangeAnimalCard.this, "Карточка питомца создана !", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<JSONResponseShow> call, Throwable t) {
                }
            });
        }

    }

    private void CheckExtra() {
        if (getIntent().getStringExtra("Nickname_Animal") != "") {
            Animal_id = getIntent().getIntExtra("Animal_id", -1);
            Nickname_Animal = getIntent().getStringExtra("Nickname_Animal");
                etNickname_Animal.setText(Nickname_Animal);
            Type_Animal = getIntent().getStringExtra("Type_Animal");
                etType_Animal.setText(Type_Animal);
            Age_Animal = getIntent().getStringExtra("Age_Animal");
                etAge_Animal.setText(Age_Animal);
                tv_info_change.setText("Измениете данные поля");
                btnChange.setText(getIntent().getStringExtra("Date"));
        } else {
            tv_info_change.setText("Заполните данные");
            btnChange.setText(getIntent().getStringExtra("Date"));
        }
    }



    private void setFindViewById(){
        sPref = getSharedPreferences("User", 0);
        sharedPreferences = new SaveDateSharedPreferences();
        User_id = 0;
        User_id = sharedPreferences.getSPrefUserId(sPref, User_id);

        tv_info_change = findViewById(R.id.tv_info_change);
        btnChange = findViewById(R.id.btnChange);
        etNickname_Animal = findViewById(R.id.etNickname_Animal);
        etType_Animal = findViewById(R.id.etType_Animal);
        etAge_Animal = findViewById(R.id.etAge_Animal);

        render_to_service = (ImageView) findViewById(R.id.button_render_to_service);
        render_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeAnimalCard.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        render_to_main = (ImageView) findViewById(R.id.button_render_to_main);
        render_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeAnimalCard.this, MainActivity.class);
                startActivity(intent);
            }
        });
        render_to_profile = (ImageView) findViewById(R.id.button_render_to_profile);
        render_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeAnimalCard.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}