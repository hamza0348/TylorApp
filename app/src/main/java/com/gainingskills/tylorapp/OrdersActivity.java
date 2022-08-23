package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UidAdopter myAdopter;
    private ArrayList<uidModel> models = new ArrayList<>();
    private DatabaseReference ref;
    private ProgressBar progressBar;
    private Toolbar mToolbar;

    String uid;
    String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        mToolbar = findViewById(R.id.toolbarIDOrders);
        progressBar = findViewById(R.id.progress_Orders);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv_Orders);
        mToolbar.setTitle("Orders");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrdersActivity.this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


    }


    @Override
    protected void onResume() {

        models.clear();
        progressBar.setVisibility(View.VISIBLE);
        super.onResume();

        ref = FirebaseDatabase.getInstance().getReference().child("UserOrders");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot snapshotss : dataSnapshot1.getChildren()) {
                        uidModel a = snapshotss.getValue(uidModel.class);
                        models.add(0, a);
                        progressBar.setVisibility(View.GONE);

                    }
                    myAdopter = new UidAdopter(OrdersActivity.this, models);
                    recyclerView.setAdapter(myAdopter);
                    myAdopter.notifyDataSetChanged();
                } else {
                    Toast.makeText(OrdersActivity.this, "no orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(OrdersActivity.this, "Error: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onStop() {
        models.clear();
        super.onStop();
    }


}