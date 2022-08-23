package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ItemAdopter myAdopter;
    private ArrayList<AdsModel> models = new ArrayList<>();
    private DatabaseReference ref;
    private ProgressBar progressBar;


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbarIDUser);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout, mToolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.bringToFront();
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.profileId:
                        startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                        break;


                    case R.id.ordersHesId:
                        startActivity(new Intent(MainActivity.this, UserOrdersHistory.class));
                        break;



                    case R.id.logOutId:
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, SignIn_Activity.class));
                        finish();



                }


                return true;
            }
        });

        progressBar = findViewById(R.id.progress_user);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv_user);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);

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
                    myAdopter = new ItemAdopter(MainActivity.this,models);
                    recyclerView.setAdapter(myAdopter);
                    myAdopter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this, "Error: "+ databaseError, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        models.clear();
        super.onStop();
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isOpen()) {
           drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }
}