package com.example.vetmobile.DateBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vetmobile.DateBase.Model.ClinicModel;
import com.example.vetmobile.DateBase.Model.ServiceModel;
import com.example.vetmobile.R;
import com.example.vetmobile.RenderServiceActivity;

import java.util.Arrays;
import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context context;
    private List<ClinicModel> Clinic;

    public Adaptery(Context context, List<ClinicModel> clinic) {
        this.context = context;
        Clinic = clinic;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_main_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

      holder.Name_Clinic.setText(Clinic.get(position).getName_Clinic());
      holder.Address.setText(Clinic.get(position).getAddress());

          Glide.with(context)
                  .load(Clinic.get(position).getPhoto_id())
                  .circleCrop()
                  .error(R.drawable.ic_vetmobile_big)
                  .into(holder.photo_id);
    holder.itemView.setOnClickListener(new View.OnClickListener() {

        final int id = Clinic.get(position).getId();
        final String NameClinic = Clinic.get(position).getName_Clinic();
        final String AddressClinic = Clinic.get(position).getAddress();
        final String ImageClinic = Clinic.get(position).getPhoto_id();

        @Override
        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Intent intent = new Intent(activity, RenderServiceActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("NameClinic", NameClinic);
            intent.putExtra("AddressClinic", AddressClinic);
            intent.putExtra("ImageClinic", ImageClinic);

            activity.startActivity(intent);
        }
    });

    }

    @Override
    public int getItemCount() {
        return Clinic.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView Name_Clinic;
        TextView Address;
        ImageView photo_id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.TextViev_id);
            Name_Clinic = itemView.findViewById(R.id.TextViev_Name_Clinic);
            Address = itemView.findViewById(R.id.TextViev_Address);

            photo_id = itemView.findViewById(R.id.Image_photo_id);
        }
    }
}
