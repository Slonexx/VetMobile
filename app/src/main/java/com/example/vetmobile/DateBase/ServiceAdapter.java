package com.example.vetmobile.DateBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.DoctorModel;
import com.example.vetmobile.DateBase.Model.RenderModel;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.DateBase.Model.TimeModel;
import com.example.vetmobile.DateBase.Model.UserModel;
import com.example.vetmobile.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyHolder>{

    private Context context;
    private List<AnimailModel> Animal;
    private List<RenderModel> Render;
    private List<ClinicModel> Clinic;
    private List<ServiceModel> Service;
    private List<DoctorModel> Doctor;
    private List<TimeModel> Time;

    public ServiceAdapter(Context context, List<AnimailModel> animal, List<RenderModel> render,
                            List<ClinicModel> clinic, List<ServiceModel> service, List<DoctorModel> doctor,
                            List<TimeModel> time) {
        this.context = context;
        Animal = animal;
        Render = render;
        Clinic = clinic;
        Service = service;
        Doctor = doctor;
        Time = time;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_service, parent, false);
        return new ServiceAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,@SuppressLint("RecyclerView")  int position) {

        holder.tv_ServiceDate_Name_Clinic.setText(Clinic.get(position).getName_Clinic());


        String TimeString;
        TimeString = "Дата: "+Time.get(position).getReceipt_Date().substring(5) +" в " + Time.get(position).getTime().substring(0,5);
        holder.tv_ServiceDate_Time.setText(TimeString);

        holder.tv_ServiceDate_Doctor.setText(Doctor.get(position).getName_Doctor());


       holder.tv_ServiceDate_Address.setText(Clinic.get(position).getAddress());

        Glide.with(context)
                .load(Clinic.get(position).getPhoto_id())
                .circleCrop()
                .error(R.drawable.ic_vetmobile_big)
                .into(holder.ServiceDate_ClinicImage);


    }

    @Override
    public int getItemCount() {
        return Render.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_ServiceDate_Doctor, tv_ServiceDate_Time, tv_ServiceDate_Name_Clinic, tv_ServiceDate_Address;
        ImageView ServiceDate_ClinicImage;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_ServiceDate_Doctor = itemView.findViewById(R.id.tv_ServiceDate_Doctor);
            tv_ServiceDate_Time = itemView.findViewById(R.id.tv_ServiceDate_Time);
            tv_ServiceDate_Name_Clinic = itemView.findViewById(R.id.tv_ServiceDate_Name_Clinic);
            tv_ServiceDate_Address = itemView.findViewById(R.id.tv_ServiceDate_Address);

            ServiceDate_ClinicImage = itemView.findViewById(R.id.ServiceDate_ClinicImage);
        }
    }

}
