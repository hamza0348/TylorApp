package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdvertisementActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private AdsAdopter myAdopter;
    private ArrayList<AdsModel> models = new ArrayList<>();
    private DatabaseReference ref;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        progressBar = findViewById(R.id.progress_advertisement);

        mToolbar = findViewById(R.id.toolbarIDADs);

        mToolbar.setTitle("Advertisement");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv_Ads);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AdvertisementActivity.this,2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Advertisement");


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
                        AdsModel a = snapshot.getValue(AdsModel.class);
                        models.add(0,a);
                        progressBar.setVisibility(View.GONE);


                    }
                    myAdopter = new AdsAdopter(AdvertisementActivity.this,models);
                    recyclerView.setAdapter(myAdopter);
                    myAdopter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(AdvertisementActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AdvertisementActivity.this, "Error: "+ databaseError, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        models.clear();
        super.onStop();
    }


}