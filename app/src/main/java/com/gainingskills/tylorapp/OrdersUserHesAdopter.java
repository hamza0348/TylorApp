package com.gainingskills.tylorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrdersUserHesAdopter extends RecyclerView.Adapter<OrdersUserHesAdopter.HViewHolder>{

    Context context;
    ArrayList<UserOrderhistoryModel> models = new ArrayList<>();

    public OrdersUserHesAdopter(Context context, ArrayList<UserOrderhistoryModel> models) {
        this.context = context;
        this.models = models;
    }


    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_template_orders,parent,false);
        return new HViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HViewHolder holder, int position) {

        holder.name.setText(models.get(position).getName());
        holder.price.setText(models.get(position).getPrice());
        Glide.with(context).load(models.get(position).getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView price,name;
        public HViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.orderedHesImageId);
            price = itemView.findViewById(R.id.orderedHesPriceId);
            name = itemView.findViewById(R.id.orderedHesNameId);
        }
    }
}
