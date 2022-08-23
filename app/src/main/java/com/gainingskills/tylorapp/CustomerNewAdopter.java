package com.gainingskills.tylorapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerNewAdopter extends FirebaseRecyclerAdapter<CustomerDataHolder,CustomerNewAdopter.HViewHolder> {


    public CustomerNewAdopter(@NonNull FirebaseRecyclerOptions<CustomerDataHolder> options) {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull HViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull CustomerDataHolder model) {
        holder.customerName.setText(model.getName().toUpperCase());
        Glide.with(holder.imageView.getContext()).load(model.getImage()).placeholder(R.drawable.user).into(holder.imageView);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (model.getName().equals("Home")|| model.getName().equals("home") || model.getName().equals("HOME")){

                    Intent intent = new Intent(holder.itemView.getContext(), TailorMainActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                    ((Activity)holder.itemView.getContext()).finish();

                }
                else {

                    String uuid = model.getUid();
                    String phone = model.getPhone();
                    Intent intent = new Intent(holder.itemView.getContext(), CustomerDetailsActivity.class);
                    intent.putExtra("uuid", uuid);
                    intent.putExtra("phone", phone);
                    holder.itemView.getContext().startActivity(intent);

                }
            }
        });
    }

    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_template,parent,false);
        return new HViewHolder(v);
    }

    class HViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView customerName;

        public HViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cImageId);
            customerName = itemView.findViewById(R.id.cNameId);

        }


    }


}
