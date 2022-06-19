package com.example.vetmobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Required.UserRequired;
import com.example.vetmobile.DateBase.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditProfileDateActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;

    private Integer User_id;
    private Button btnEdit;
    private ImageView imageView, render_to_service, render_to_main, render_to_profile;
    private TextInputEditText etName, etPhone, etPassword;
    private ImageView ClickProfileImage;
    private InputStream inputStream;

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

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            File file = new File(uri.getPath());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String now = sdf.format(new Date());
            try {

                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                File secendfile;
                if (uri.getLastPathSegment().endsWith("jpg")){
                     secendfile = getFile(inputStream,getApplicationContext().getFilesDir()+"/"+now+file.getName()+".jpg");
                } else   secendfile = getFile(inputStream,getApplicationContext().getFilesDir()+"/"+now+file.getName()+".png");


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), secendfile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", secendfile.getName(), reqFile);

                ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
                apiService.ChangeImageUser(User_id,body).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("IMAGE", response.headers().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                Glide.with(this)
                        .load(data.getData())
                        .circleCrop()
                        .error(R.drawable.ic_profile)
                        .into(ClickProfileImage);

            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    @Nullable
    public File getFile(@NonNull InputStream inputStream, String filename){
        File file = new File(filename);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            byte [] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer))>0){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return file;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    private void ChangeRequired(){
        UserRequired userRequired = new UserRequired();
        userRequired.setName(etName.getText().toString());
        userRequired.setPhone(etPhone.getText().toString());
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

        sPref = getSharedPreferences("User", 0);
        sharedPreferences = new SaveDateSharedPreferences();
        User_id = 0;

        btnEdit = findViewById(R.id.btnEdit);
            User_id = getIntent().getIntExtra("id", -1);
        imageView = findViewById(R.id.ClickProfileImage);

        ClickProfileImage = findViewById(R.id.ClickProfileImage);
        ClickProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

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