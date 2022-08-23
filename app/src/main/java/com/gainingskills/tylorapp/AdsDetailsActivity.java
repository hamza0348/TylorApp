package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soup.neumorphism.NeumorphButton;

public class AdsDetailsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    TextView name,price;
    ImageView imageView;
    NeumorphButton deleteBtn;
    String itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_details);
        imageView = findViewById(R.id.adsDetailPic);
        name = findViewById(R.id.adsDetailName);
        deleteBtn = findViewById(R.id.deleteBtnAds);
        price = findViewById(R.id.adsDetailPrice);
        mToolbar = findViewById(R.id.toolbarIDAdsDetails);
        mToolbar.setTitle("Details");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String cName = getIntent().getExtras().getString("name").toString();
        String cPrice = getIntent().getExtras().getString("price").toString();
        String cImage = getIntent().getExtras().getString("image").toString();
        itemId = getIntent().getExtras().getString("itemId").toString();
        name.setText(cName);
        price.setText(cPrice);
        Glide.with(AdsDetailsActivity.this).load(cImage).placeholder(R.drawable.categories).into(imageView);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBtn.setText("wait");
                deleteBtn.setClickable(false);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Advertisement");
                ref.child(itemId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(AdsDetailsActivity.this, AdvertisementActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        deleteBtn.setText("Delete");
                        deleteBtn.setClickable(true);
                        Toast.makeText(AdsDetailsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}