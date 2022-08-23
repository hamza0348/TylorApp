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

public class UserOrdersHistory extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private OrdersUserHesAdopter myAdopter;
    private ArrayList<UserOrderhistoryModel> models = new ArrayList<>();
    private DatabaseReference ref;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String userUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders_history);

        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getUid();
        progressBar = findViewById(R.id.progress_user_history);

        mToolbar = findViewById(R.id.toolbarIDHistory);

        mToolbar.setTitle("Orders");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv_user_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserOrdersHistory.this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ref = FirebaseDatabase.getInstance().getReference().child("CompletedOrders").child(userUid);


    }

    @Override
    protected void onResume() {

        models.clear();
        progressBar.setVisibility(View.VISIBLE);
        super.onResume();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        UserOrderhistoryModel a = snapshot.getValue(UserOrderhistoryModel.class);
                        models.add(0,a);
                        progressBar.setVisibility(View.GONE);


                    }
                    myAdopter = new OrdersUserHesAdopter(UserOrdersHistory.this,models);
                    recyclerView.setAdapter(myAdopter);
                    myAdopter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(UserOrdersHistory.this, "no data found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(UserOrdersHistory.this, "Error: "+ databaseError, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        models.clear();
        super.onStop();
    }


}