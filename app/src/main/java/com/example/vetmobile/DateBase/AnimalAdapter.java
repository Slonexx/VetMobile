package com.example.vetmobile.DateBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vetmobile.AnimateActivity;
import com.example.vetmobile.ChangeAnimalCard;
import com.example.vetmobile.DateBase.Model.AnimailModel;
import com.example.vetmobile.R;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.HolderAnimal>{
    private Context context;
    private List<AnimailModel> Animal;

    public AnimalAdapter(Context context, List<AnimailModel> animal) {
        this.context = context;
        Animal = animal;
    }


    @NonNull
    @Override
    public AnimalAdapter.HolderAnimal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_animal, parent, false);
        return new AnimalAdapter.HolderAnimal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.HolderAnimal holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_nomerAnimal.setText("№"+String.valueOf(position+1));

        holder.tv_Nickname_Animal.setText(Animal.get(position).getNickname_Animal());
        holder.tv_Type_Animal.setText(Animal.get(position).getType_Animal());

        String age = Animal.get(position).getAge_Animal();
        if ( Integer.parseInt(age) < 5 ) {
            holder.tv_Age_Animal.setText(age +" года");
        }else {holder.tv_Age_Animal.setText(age +" лет");

        }
        holder.btn_edit_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, ChangeAnimalCard.class);
                intent.putExtra("Animal_id", Animal.get(position).getId());
                intent.putExtra("Nickname_Animal", Animal.get(position).getNickname_Animal());
                intent.putExtra("Type_Animal", Animal.get(position).getType_Animal());
                intent.putExtra("Age_Animal", Animal.get(position).getAge_Animal());
                intent.putExtra("Date", "Изменение");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Animal.size();
    }

    public static class HolderAnimal extends RecyclerView.ViewHolder{

        TextView tv_nomerAnimal, tv_Nickname_Animal, tv_Type_Animal, tv_Age_Animal;
        Button btn_edit_animal;


        public HolderAnimal(@NonNull View itemView) {
            super(itemView);

            tv_nomerAnimal = itemView.findViewById(R.id.tv_nomerAnimal);

            tv_Nickname_Animal = itemView.findViewById(R.id.tv_Nickname_Animal);
            tv_Type_Animal = itemView.findViewById(R.id.tv_Type_Animal);
            tv_Age_Animal = itemView.findViewById(R.id.tv_Age_Animal);

            btn_edit_animal = itemView.findViewById(R.id.btn_edit_animal);

        }
    }
}
