package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ServiceActivity extends AppCompatActivity {

    ImageView btn_service_to_profile, btn_service_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btn_service_to_profile = findViewById(R.id.button_service_to_profile);
        btn_service_to_main = findViewById(R.id.button_service_to_main);

        btn_service_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_service_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}