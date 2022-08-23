package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import soup.neumorphism.NeumorphButton;

public class UserProfileActivity extends AppCompatActivity {

    private TextView fullName,userPhone,userEmail,userAddress;
    private CircleImageView profilePic;
    private NeumorphButton updateBtn;
    private ProgressBar progressBar;
    private Toolbar aToolbar;
    private LinearLayout layout;
    String nameF;
    String phoneF;
    String emailF;
    String addressF;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();

        uid = FirebaseAuth.getInstance().getUid();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        aToolbar = findViewById(R.id.ausToolbar);
        aToolbar.setTitle("Profile");
        setSupportActionBar(aToolbar);
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(UserProfileActivity.this, ProfileEditActivity.class);
                intent.putExtra("name",nameF);
                intent.putExtra("phone",phoneF);
                intent.putExtra("email",emailF);
                startActivity(intent);

            }
        });




    }

    private void initViews() {

        fullName = findViewById(R.id.userProFullName);
        userPhone = findViewById(R.id.userProPhoneEd);
        userEmail = findViewById(R.id.userProEmail);
        userAddress = findViewById(R.id.userProAddress);
        profilePic = findViewById(R.id.userProPic);
        updateBtn = findViewById(R.id.updateBtn);
        layout = findViewById(R.id.ll1);



    }





    @Override
    protected void onResume() {
        super.onResume();

        layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        FetchedDataFirebase();


    }

    private void FetchedDataFirebase() {


     DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");

     rootRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists()){

                 nameF = snapshot.child("name").getValue().toString();
                 phoneF = snapshot.child("phone").getValue().toString();
                 emailF = snapshot.child("email").getValue().toString();
                 addressF = snapshot.child("address").getValue().toString();
                 String image = snapshot.child("image").getValue().toString();


                 fullName.setText(nameF);
                 userPhone.setText(phoneF);
                 userEmail.setText(emailF);
                 userAddress.setText(addressF);
                 Glide.with(UserProfileActivity.this).load(image).placeholder(R.drawable.user).into(profilePic);
                 layout.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.GONE);
             }else {
                 Toast.makeText(UserProfileActivity.this, "Update your profile", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
        finish();
    }
}