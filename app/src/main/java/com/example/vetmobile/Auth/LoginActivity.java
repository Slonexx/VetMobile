package com.example.vetmobile.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.DoctorModel;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.LoginRequired;
import com.example.vetmobile.DateBase.Model.TimeModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.example.vetmobile.MainActivity;
import com.example.vetmobile.R;
import com.example.vetmobile.SaveDateSharedPreferences;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;

    private ConstraintLayout LoginLayout, toComeInUserLayout;

    private Button bSignIn;
    private TextInputEditText Email, Password;
    private TextView btnRegist;

    private String Token;
    private int User_id;

    private ImageView ImageProgile, endProfile;
    private TextView NameProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setFindViewById();

        Token = sharedPreferences.getSPrefToken(sPref, Token);

        if (Token == ""){
            toComeInUserLayout.setVisibility(View.GONE);
            LoginLayout.setVisibility(View.VISIBLE);
            bSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginProcces();
                }
            });
        } else {
            LoginLayout.setVisibility(View.GONE);
            toComeInUserLayout.setVisibility(View.VISIBLE);

            User_id = sharedPreferences.getSPrefUserId(sPref, User_id);
            getUser(User_id);


        }



    }

    private void getUser(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();

                Glide.with(LoginActivity.this)
                        .load(model.getPhoto_id())
                        .circleCrop()
                        .error(R.drawable.ic_profile)
                        .into(ImageProgile);

                NameProfile.setText(model.getName());

                ImageProgile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(LoginActivity.this, "Добро пожаловать "+ model.getName(), Toast.LENGTH_LONG).show();
                            }
                        },700);
                    }
                });

            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }


    private void loginProcces(){
        if(TextUtils.isEmpty(Email.getText().toString()) || TextUtils.isEmpty(Password.getText().toString())){
            Toast.makeText(LoginActivity.this,"Email или Пароль не верны!", Toast.LENGTH_LONG).show();
        } else {
            login();
        }
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

                    UserModel model = response.body().getUser();
                    Token = response.body().getToken();
                    User_id = model.getId();

                    sharedPreferences.setSPrefToken(sPref, Token);
                    sharedPreferences.setSPrefUserId(sPref, User_id);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "Добро пожаловать " + model.getName(), Toast.LENGTH_LONG).show();
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

    private void setFindViewById(){
        sPref = getSharedPreferences("User", 0);
        sharedPreferences = new SaveDateSharedPreferences();
        Token = "";
        User_id = 0;

        LoginLayout = findViewById(R.id.LoginLayout);
        toComeInUserLayout = findViewById(R.id.toComeInUserLayout);

        Email = findViewById(R.id.etLoginEmail);
        Password = findViewById(R.id.etLoginPassword);
        bSignIn = findViewById(R.id.bLogin);
        btnRegist = findViewById(R.id.bt_login_registr);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        ImageProgile = findViewById(R.id.ivProfileImageLogin);
        NameProfile = findViewById(R.id.tvProfileNameLogin);
        endProfile = findViewById(R.id.iv_btn_login_registr);
        endProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toComeInUserLayout.setVisibility(View.GONE);
                LoginLayout.setVisibility(View.VISIBLE);
                loginProcces();
            }
        });

    }

}
