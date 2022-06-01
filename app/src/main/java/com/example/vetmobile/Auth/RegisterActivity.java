package com.example.vetmobile.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.Required.LoginRequired;
import com.example.vetmobile.DateBase.Required.RegisterRequired;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.example.vetmobile.MainActivity;
import com.example.vetmobile.R;
import com.example.vetmobile.SaveDateSharedPreferences;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;

    private String Token;
    private int User_id;

    private Button btnCreating_acc;
    private TextInputEditText etRegisterName, etRegisterEmail, etRegisterPhone, etRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setFindViewById();

        btnCreating_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckTextView();
            }
        });
    }

    private void CheckTextView(){
        if(TextUtils.isEmpty(etRegisterName.getText().toString()) || TextUtils.isEmpty(etRegisterEmail.getText().toString())
                ||TextUtils.isEmpty(etRegisterPhone.getText().toString()) || TextUtils.isEmpty(etRegisterPassword.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Введите данные !", Toast.LENGTH_LONG).show();
        } else {
            registration();
        }
    }

    private void registration(){
        RegisterRequired required = new RegisterRequired();
        required.setName(etRegisterName.getText().toString());
        required.setEmail(etRegisterEmail.getText().toString());
        required.setPhone(etRegisterPhone.getText().toString());
        required.setPassword(etRegisterPassword.getText().toString());
        required.setPassword_confirmation(etRegisterPassword.getText().toString());

        Call<UserModel> call = RetrofitClient.getApiService().userRegister(required);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){

                    UserModel model = response.body().getUser();
                    Token = response.body().getToken();
                    User_id = model.getId();

                    sharedPreferences.setSPrefToken(sPref, Token);
                    sharedPreferences.setSPrefUserId(sPref, User_id);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            Toast.makeText(RegisterActivity.this, "Добро пожаловать " + model.getName(), Toast.LENGTH_LONG).show();
                        }
                    },700);
                }else {
                    Toast.makeText(RegisterActivity.this, "Ошибка Сервера 404", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private void setFindViewById(){
        sPref = getSharedPreferences("User", 0);
        sharedPreferences = new SaveDateSharedPreferences();
        Token = "";
        User_id = 0;

        etRegisterName =findViewById(R.id.etRegisterName);
        etRegisterEmail =findViewById(R.id.etRegisterEmail);
        etRegisterPhone =findViewById(R.id.etRegisterPhone);
        etRegisterPassword =findViewById(R.id.etRegisterPassword);

        btnCreating_acc = findViewById(R.id.btnCreating_acc);

    }
}