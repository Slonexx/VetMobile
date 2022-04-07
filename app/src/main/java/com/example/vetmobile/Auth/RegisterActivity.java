package com.example.vetmobile.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vetmobile.MainActivity;
import com.example.vetmobile.R;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setFindViewById();

    }

    private void setFindViewById(){
        btnCreating = findViewById(R.id.btnCreating_acc);
        btnCreating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Аккаунт создан!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                },700);

            }
        });
    }
}