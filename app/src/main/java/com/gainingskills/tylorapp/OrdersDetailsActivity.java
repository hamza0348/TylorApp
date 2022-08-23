package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrdersAdopter myAdopter;
    private ArrayList<OrdersModel> models = new ArrayList<>();
    private DatabaseReference ref;
    ProgressBar progressBar;


    Toolbar toolbar;
    CircleImageView cusPicImg;
    TextView custNameTv, custPhoneTv;
    String uid,customerphone,customername,customerImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
        cusPicImg = findViewById(R.id.cusDetailPicOrders);
        custNameTv = findViewById(R.id.cusDetailNameOrders);
        custPhoneTv = findViewById(R.id.cusDetailPhoneOrders);
        recyclerView = findViewById(R.id.rv_orderDetails);
        progressBar = findViewById(R.id.orderDetailsProgressBarId);

        toolbar = findViewById(R.id.toolbarIDOrdersDetails);
        toolbar.setTitle("Order details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        custPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String toNumber = custPhoneTv.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text="));
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        uid = getIntent().getExtras().getString("uid");

          DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
          reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if (snapshot.exists()) {
                      try {
                          customername = snapshot.child("name").getValue().toString();
                          customerphone = snapshot.child("phone").getValue().toString();
                          customerImage = snapshot.child("image").getValue().toString();
                          custNameTv.setText(customername);
                          custPhoneTv.setText(customerphone);
                          Glide.with(OrdersDetailsActivity.this).load(customerImage).into(cusPicImg);
                      }
                      catch (Exception e){
                          e.printStackTrace();
                      }


                  }
                  else {
                      Toast.makeText(OrdersDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrdersDetailsActivity.this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);




    }

    @Override
    protected void onResume() {

        models.clear();
        progressBar.setVisibility(View.VISIBLE);
        super.onResume();
        ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        OrdersModel a = snapshot.getValue(OrdersModel.class);
                        models.add(a);
                        progressBar.setVisibility(View.GONE);


                    }
                    myAdopter = new OrdersAdopter(OrdersDetailsActivity.this,models);
                    recyclerView.setAdapter(myAdopter);
                    myAdopter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(OrdersDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(OrdersDetailsActivity.this, "Error: "+ databaseError, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        models.clear();
        super.onStop();
    }
}