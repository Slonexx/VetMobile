package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Required.UserRequired;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditProfileDateActivity extends AppCompatActivity {

    private Integer User_id;
    private Button btnEdit;
    private ImageView imageView, render_to_service, render_to_main, render_to_profile;
    private TextInputEditText etName, etPhone, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile_date);
        setFindViewById();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString()) ||
                        TextUtils.isEmpty(etPhone.getText().toString()) ||
                        TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(UserEditProfileDateActivity.this,"Введите данные !", Toast.LENGTH_LONG).show();
                }else {
                    ChangeRequired();
                }
            }
        });
    }

    private void ChangeRequired(){
        UserRequired userRequired = new UserRequired();
        userRequired.setName(etName.getText().toString());
        userRequired.setPhone(etPhone.getText().toString());
        userRequired.setPassword(etPassword.getText().toString());
        Call<JSONResponseShow> call = RetrofitClient.getApiService().setChangeUser(userRequired, User_id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserEditProfileDateActivity.this, "Данные изменились!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {

            }
        });
    }

    private void setFindViewById() {

        btnEdit = findViewById(R.id.btnEdit);
            User_id = getIntent().getIntExtra("id", -1);
        imageView = findViewById(R.id.imageView);

        Glide.with(this)
                .load(getIntent().getStringExtra("Glide"))
                .circleCrop()
                .error(R.drawable.ic_profile)
                .into(imageView);

        etName = findViewById(R.id.etName);
            etName.setText(getIntent().getStringExtra("name"));
        etPhone = findViewById(R.id.etPhone);
            etPhone.setText(getIntent().getStringExtra("phone"));
        etPassword = findViewById(R.id.etPassword);
            etPassword.setText(getIntent().getStringExtra("password"));

        render_to_service = (ImageView) findViewById(R.id.button_render_to_service);
        render_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEditProfileDateActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        render_to_main = (ImageView) findViewById(R.id.button_render_to_main);
        render_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEditProfileDateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        render_to_profile = (ImageView) findViewById(R.id.button_render_to_profile);
        render_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEditProfileDateActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}