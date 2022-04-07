package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenderServiceActivity extends AppCompatActivity {

    private int getId;
    private String NameClinic, AddressClinic, ImageClinic;

    private List<String> spinerArray = new ArrayList<>();
    private ConstraintLayout VisibleInfo;
    private List<ServiceModel> ServiceModelList;

    private TextView tvNameClinic, tvAddressClinic;
    private ImageView imgImageClinic;


    private ImageView render_to_service, render_to_main, render_to_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_service);

        SetAllPiker();
        SpinerArrayString(getId);


        List<String> type = spinerArray;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.drop_down_item, type);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.SpinerService);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RenderServiceActivity.this, autoCompleteTextView.getText().toString(),Toast.LENGTH_LONG).show();
                VisibleInfo.setVisibility(View.VISIBLE);
            }
        });






    }
    private void SpinerArrayString(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getClinicShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ClinicModel model = response.body().getClinic();
                ServiceModel[] Models = model.getService().toArray(new ServiceModel[0]);
                ServiceModelList = model.getService();
                for (ServiceModel serviceModel : Models){
                    spinerArray.add(serviceModel.getName_Service());
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }
    private  void SetAllPiker(){
        VisibleInfo = (ConstraintLayout) findViewById(R.id.visible_info_render);
        VisibleInfo.setVisibility(View.GONE);

        tvNameClinic = (TextView) findViewById(R.id.TextViev_Name_Clinic_ServiceOnClick);
        tvAddressClinic = (TextView) findViewById(R.id.TextViev_Address_ServiceOnClick);
        imgImageClinic = (ImageView) findViewById(R.id.Image_ServiceOnClick);

        getId = getIntent().getIntExtra("id",0);
        NameClinic = getIntent().getStringExtra("NameClinic");
        AddressClinic = getIntent().getStringExtra("AddressClinic");
        ImageClinic = getIntent().getStringExtra("ImageClinic");

        tvNameClinic.setText(NameClinic);
        tvAddressClinic.setText(AddressClinic);

        Glide.with(this)
                .load(ImageClinic)
                .circleCrop()
                .error(R.drawable.ic_vetmobile_big)
                .into(imgImageClinic);

        render_to_service = (ImageView) findViewById(R.id.button_render_to_service);
        render_to_main = (ImageView) findViewById(R.id.button_render_to_main);
        render_to_profile = (ImageView) findViewById(R.id.button_render_to_profile);

        render_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        render_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        render_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}