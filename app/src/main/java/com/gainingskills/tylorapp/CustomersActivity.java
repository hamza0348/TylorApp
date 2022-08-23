package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private CustomerNewAdopter adopter;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        mToolbar = findViewById(R.id.toolbarIDCustomers);
        mToolbar.setTitle("Customers");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.rv_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<CustomerDataHolder> options = new FirebaseRecyclerOptions.Builder<CustomerDataHolder>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User"),CustomerDataHolder.class)
                .build();


        adopter = new CustomerNewAdopter(options);
        adopter.startListening();
        recyclerView.setAdapter(adopter);


    }


    @Override
    protected void onResume() {
        super.onResume();
        adopter.startListening();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar = findViewById(R.id.progressBarCus);
                progressBar.setVisibility(View.GONE);
            }
        },4500);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {




        FirebaseRecyclerOptions<CustomerDataHolder> option = new FirebaseRecyclerOptions.Builder<CustomerDataHolder>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User").orderByChild("name").startAt(s.toUpperCase()).endAt(s+"\uf8ff"),CustomerDataHolder.class)
                .build();

            adopter = new CustomerNewAdopter(option);
            adopter.startListening();
            recyclerView.setAdapter(adopter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomersActivity.this, TailorMainActivity.class));
        finish();
    }
}


