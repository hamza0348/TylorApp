package com.gainingskills.tylorapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import soup.neumorphism.NeumorphButton;

public class OrdersAdopter extends RecyclerView.Adapter<OrdersAdopter.ViewHolder>{

  Context context;
  ArrayList<OrdersModel> models;

    public OrdersAdopter(Context context, ArrayList<OrdersModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(models.get(position).getItemname());
        holder.price.setText(models.get(position).getItemprice());
        Glide.with(context).load(models.get(position).getItemimage()).into(holder.imageView);

        holder.completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(models.get(position).getUid());
                    databaseReference.child(models.get(position).getPushId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            models.remove(position);
                            notifyItemRemoved(position);
                            notifyItemChanged(position,models.size());
                        }
                    });


                    DatabaseReference root = FirebaseDatabase.getInstance().getReference("CompletedOrders");
                    String iName = models.get(position).getItemname();
                    String iPrice = models.get(position).getItemprice();
                    String uid = models.get(position).getUid();
                    String iImage = models.get(position).getItemimage();
                    String itemId = models.get(position).getItemId();

                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("name", iName);
                    map1.put("price", iPrice);
                    map1.put("uid", uid);
                    map1.put("img", iImage);
                    map1.put("itemId", itemId);

                    root.child(uid).child(itemId).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "contact", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Error: "+e, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView price,name;
        NeumorphButton completedBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.orderedItemImageId);
            price = itemView.findViewById(R.id.orderedItemPriceId);
            name = itemView.findViewById(R.id.orderedItemNameId);
            completedBtn = itemView.findViewById(R.id.orderedItemBtnId);

        }
    }
}
