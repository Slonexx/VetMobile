package com.example.vetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vetmobile.Auth.LoginActivity;
import com.example.vetmobile.Auth.RegisterActivity;
import com.example.vetmobile.DateBase.ApiService;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.DoctorModel;
import com.example.vetmobile.DateBase.Model.JSONResponseShow;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.Model.TimeModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.DateBase.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenderServiceActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private SaveDateSharedPreferences sharedPreferences;
    private int User_id;

    private int getId;
    private String NameClinic, AddressClinic, ImageClinic;

    private List<Integer> AnimalId = new ArrayList<>();
    private List<String> spinerAnimalNickname = new ArrayList<>();
    private List<String> AnimmalInfoCard = new ArrayList<>();
    private List<String> spinerServiceName = new ArrayList<>();
    private List<Integer> ServiceId = new ArrayList<>();
    private List<String> ServiceDescriptions = new ArrayList<>();
    private List<Integer> DoctorId = new ArrayList<>();
    private List<String> DoctorName_Doctor = new ArrayList<>();
    private List<String> DoctorSpeciality = new ArrayList<>();
    private List<Integer> TimeId = new ArrayList<>();
    private List<String> TimeDate = new ArrayList<>();
    private String TimeString = "";

    private int outAnimalId, outServiceId, outDoctorId, outTimeId;

    private ImageView btn_info_service, btn_info_doctor, iv_button_info_animal;
    private ConstraintLayout VisibleInfoService, VisibleInfoDoctor, visible_info_Animal;


    private TextView tvNameClinic, tvAddressClinic;
    private ImageView imgImageClinic;

    private ImageView render_to_service, render_to_main, render_to_profile;

    private Button btnRenderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_service);
        SetAllPiker();

        User_id = sharedPreferences.getSPrefUserId(sPref, User_id);

        getUser(User_id);
        setAnimalAdapter();
        getServiceData(getId);
        setServiceAdapter();

        btnRenderService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outAnimalId == 0 || outDoctorId == 0 || outServiceId == 0 || outTimeId == 0){
                    Toast.makeText(RenderServiceActivity.this, "Выберите всё инфомацию", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(RenderServiceActivity.this, TicketActivity.class);
                    intent.putExtra("outAnimalId", outAnimalId);
                    intent.putExtra("outClinicId", getId);
                    intent.putExtra("outServiceId", outServiceId);
                    intent.putExtra("outDoctorId", outDoctorId);
                    intent.putExtra("outTimeId", outTimeId);
                    startActivity(intent);
                }
            }
        });

    }



    private void setTimeAdapter(){
        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, R.layout.drop_down_item, TimeDate);
        AutoCompleteTextView aCTV_Time = findViewById(R.id.SpinerTime);
        aCTV_Time.setAdapter(adapterTime);
        aCTV_Time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                outTimeId = TimeId.get(i);
            }
        });
    }

    private void getTimeDate(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getDoctorShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                DoctorModel model = response.body().getDoctor();
                TimeModel[] Models = model.getTime().toArray(new TimeModel[0]);
                for (TimeModel modelTime: Models){
                    TimeId.add(modelTime.getId());
                    TimeString = "Дата: "+modelTime.getReceipt_Date().substring(5,10) +" в " + modelTime.getTime().substring(0,5);
                    TimeDate.add(TimeString);
                }
                setTimeAdapter();

            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void setDoctorAdapter(){
        ArrayAdapter<String> adapterDoctor = new ArrayAdapter<>(this, R.layout.drop_down_item, DoctorName_Doctor);
        AutoCompleteTextView aCTV_Doctor = findViewById(R.id.SpinerDoctor);
        aCTV_Doctor.setAdapter(adapterDoctor);
        aCTV_Doctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getTimeDate(DoctorId.get(i));
                btn_info_doctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RenderServiceActivity.this).create();
                        alertDialog.setTitle("Информация о Докторе");
                        alertDialog.setMessage(DoctorSpeciality.get(i));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which){
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                VisibleInfoDoctor.setVisibility(View.VISIBLE);
                outDoctorId = DoctorId.get(i);
            }
        });
    }

    private void getDoctorData(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getServiceShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ServiceModel model = response.body().getService();
                DoctorModel[] Models = model.getDoctor().toArray(new DoctorModel[0]);
                for (DoctorModel doctorModel: Models){
                    DoctorId.add(doctorModel.getId());
                    DoctorName_Doctor.add(doctorModel.getName_Doctor());
                    DoctorSpeciality.add(doctorModel.getSpeciality());
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void setServiceAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.drop_down_item, spinerServiceName);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.SpinerService);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getDoctorData(ServiceId.get(i));
                setDoctorAdapter();

                btn_info_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RenderServiceActivity.this).create();
                        alertDialog.setTitle("Информация о услуге");
                        alertDialog.setMessage(ServiceDescriptions.get(i));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which){
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                VisibleInfoService.setVisibility(View.VISIBLE);
                outServiceId = ServiceId.get(i);
            }
        });
    }

    private void getServiceData(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getClinicShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                ClinicModel model = response.body().getClinic();
                ServiceModel[] Models = model.getService().toArray(new ServiceModel[0]);
                for (ServiceModel serviceModel : Models){
                    ServiceId.add(serviceModel.getId());
                    spinerServiceName.add(serviceModel.getName_Service());
                    ServiceDescriptions.add(serviceModel.getDescriptions());
                }
            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private void setAnimalAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.drop_down_item, spinerAnimalNickname);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.SpinerAnimal);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                iv_button_info_animal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RenderServiceActivity.this).create();
                        alertDialog.setTitle("Информация о карточки животного");
                        alertDialog.setMessage(AnimmalInfoCard.get(i));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which){
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                visible_info_Animal.setVisibility(View.VISIBLE);
                outAnimalId = AnimalId.get(i);
            }
        });
    }

    private void getUser(int id)
    {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JSONResponseShow> call = apiService.getUserShow(id);
        call.enqueue(new Callback<JSONResponseShow>() {
            @Override
            public void onResponse(Call<JSONResponseShow> call, Response<JSONResponseShow> response) {
                UserModel model = response.body().getUser();
                AnimailModel[] Models = model.getAnimal().toArray(new AnimailModel[0]);
                for (AnimailModel animalModel : Models){
                    spinerAnimalNickname.add(animalModel.getNickname_Animal());

                    String age = animalModel.getAge_Animal();
                    if (Integer.parseInt(age)>4)
                        age = animalModel.getAge_Animal() + " лет";
                    else age = animalModel.getAge_Animal() + " года";

                    AnimalId.add(animalModel.getId());
                    AnimmalInfoCard.add("Кличка животного: " + animalModel.getNickname_Animal() +"\n" +
                                        "Возраст животного: " + age + "\n" +
                                        "Вид животного: " + animalModel.getType_Animal());
                }

            }

            @Override
            public void onFailure(Call<JSONResponseShow> call, Throwable t) {
            }
        });
    }

    private  void SetAllPiker(){
        sPref = getSharedPreferences("User", 0);
        sharedPreferences = new SaveDateSharedPreferences();
        User_id = 0;
        outAnimalId = 0;
        outServiceId = 0;
        outDoctorId = 0;
        outTimeId = 0;

        visible_info_Animal = (ConstraintLayout) findViewById(R.id.visible_info_Animal);
        VisibleInfoService = (ConstraintLayout) findViewById(R.id.visible_info_Service);
        VisibleInfoDoctor = (ConstraintLayout) findViewById(R.id.visible_info_Doctor);
        VisibleInfoService.setVisibility(View.GONE);
        VisibleInfoDoctor.setVisibility(View.GONE);
        visible_info_Animal.setVisibility(View.GONE);

        btnRenderService = findViewById(R.id.btnRenderService);

        iv_button_info_animal = (ImageView) findViewById(R.id.iv_button_info_animal);
        btn_info_service = (ImageView) findViewById(R.id.iv_button_info_service);
        btn_info_doctor = (ImageView) findViewById(R.id.iv_button_info_doctor);

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
        render_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        render_to_main = (ImageView) findViewById(R.id.button_render_to_main);
        render_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        render_to_profile = (ImageView) findViewById(R.id.button_render_to_profile);
        render_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RenderServiceActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}