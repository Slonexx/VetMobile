package com.example.vetmobile.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.LoginRequired;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.example.vetmobile.MainActivity;
import com.example.vetmobile.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    Button bSignIn;
    TextInputEditText Email, Password;
    TextView btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setFindViewById();

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(TextUtils.isEmpty(Email.getText().toString()) || TextUtils.isEmpty(Password.getText().toString())){
                  Toast.makeText(LoginActivity.this,"Email / Пароль не верны!", Toast.LENGTH_LONG).show();
              }
              else {
                  login();
              }


            }
        });



    }

    private void setFindViewById(){
        bSignIn = findViewById(R.id.bLogin);
        Email = findViewById(R.id.etLoginEmail);
        Password = findViewById(R.id.etLoginPassword);
        btnRegist = findViewById(R.id.bt_login_registr);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(){
        LoginRequired loginRequired = new LoginRequired();
        loginRequired.setEmail(Email.getText().toString());
        loginRequired.setPassword(Password.getText().toString());
        Call<UserModel> userModelCall = RetrofitClient.getApiService().userLogin(loginRequired);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    },700);
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }


}
