package com.gainingskills.tylorapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemAdopter extends RecyclerView.Adapter<ItemAdopter.ItemViewHolder> {

    Context context;
    ArrayList<AdsModel> models = new ArrayList<>();

    public ItemAdopter(Context context, ArrayList<AdsModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_template,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(models.get(position).getcName());
        Glide.with(context).load(models.get(position).getcImage()).placeholder(R.drawable.categories).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image = models.get(position).getcImage();
                String price =  models.get(position).getcPrice();
                String name =  models.get(position).getcName();
                String itemId = models.get(position).getItemId();
                Intent intent = new Intent(context, ItemDetailsActivity.class);
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

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImageId);
            textView = itemView.findViewById(R.id.itemNameId);
        }
    }

}
