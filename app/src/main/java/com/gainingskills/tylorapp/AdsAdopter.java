package com.gainingskills.tylorapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;

public class AdsAdopter extends RecyclerView.Adapter<AdsAdopter.adViewHolder>{

    Context context;
    ArrayList<AdsModel> models = new ArrayList<>();

    public AdsAdopter(Context context, ArrayList<AdsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public adViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.advertisement_template,parent,false);
        return new adViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(models.get(position).getcName());
        Glide.with(context).load(models.get(position).getcImage()).placeholder(R.drawable.categories).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image = models.get(position).getcImage();
                String price =  models.get(position).getcPrice();
                String name =  models.get(position).getcName();
                String itemId =  models.get(position).getItemId();
                Intent intent = new Intent(context, AdsDetailsActivity.class);
                intent.putExtra("image",image);
                intent.putExtra("price",price);
                intent.putExtra("name",name);
                intent.putExtra("itemId",itemId);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class adViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,delete;
        TextView textView;
        public adViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adsImageId);
            textView = itemView.findViewById(R.id.adsNameId);

        }
    }
}
