package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import soup.neumorphism.NeumorphCardView;

public class TailorMainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    NeumorphCardView cusBtn,adsBtn,postAdsBtn,ordersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_main);
        cusBtn = findViewById(R.id.customersBtn);
        postAdsBtn = findViewById(R.id.postAdsBtn);
        adsBtn = findViewById(R.id.adsBtn);
        ordersBtn = findViewById(R.id.orderTBtnId);
        mToolbar = findViewById(R.id.toolbarIDT);
        navigationView = findViewById(R.id.navigationViewT);
        drawerLayout = findViewById(R.id.drawerT);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(TailorMainActivity.this,drawerLayout, mToolbar, R.string.open,R.string.close);
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



                    case R.id.shareApp:

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Book your tailor";
                        String shareSub = "";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share with friends"));
                        break;

                    case R.id.logOutId:
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        startActivity(new Intent(TailorMainActivity.this, SignIn_Activity.class));
                        finish();



                }


                return true;
            }
        });


        cusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TailorMainActivity.this, CustomersActivity.class));
                finish();
            }
        });

        postAdsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TailorMainActivity.this, PostAdsActivity.class));
            }
        });

        adsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TailorMainActivity.this, AdvertisementActivity.class));
            }
        });

        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TailorMainActivity.this, OrdersActivity.class));
            }
        });

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