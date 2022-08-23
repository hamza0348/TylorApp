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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class ProfileEditActivity extends AppCompatActivity {


    private EditText fullName,userPhone,userEmail,userAddress;
    private CircleImageView profilePic;
    private NeumorphButton updateBtn;
    private ProgressBar progressBar;
    private Toolbar aToolbar;
    private LinearLayout layout;
    String userName;
    String userPhoneN;
    String emailUser;
    String userAdd;

    String collar;
    String chest;
    String waist;
    String seat;
    String frontWidth;
    String firstBtn;
    String sleeveLength;
    String shortSleeve;
    String shortSleeveOpen;
    String biceps;
    String forearm;
    String armhole;
    String shoulder;
    String backWidth;
    String shirtLength;
    String cuff;

    private final int PICK_IMAGE_REQUEST = 22;
    private Uri imageUri;

    String uid;
    private String defaultPic = "data:image/png;base64,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";
    private String uploadPicLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();

        uid = FirebaseAuth.getInstance().getUid();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        aToolbar = findViewById(R.id.ausToolbar);
        aToolbar.setTitle("Edit profile");
        setSupportActionBar(aToolbar);
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //show();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
                updateBtn.setClickable(false);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateBtn.setClickable(false);
                if (TextUtils.isEmpty(fullName.getText())){
                    fullName.setError("Enter full name");
                }
                else if (TextUtils.isEmpty(userEmail.getText())){
                    userEmail.setError("Enter email");
                }
                else if (TextUtils.isEmpty(userAddress.getText())){
                    userAddress.setError("Enter your address");
                }
                else if (TextUtils.isEmpty(userPhone.getText())){
                    userPhone.setError("Enter phone number");
                }
                else {
                    layout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    profilePic.setVisibility(View.GONE);
                    userPhone.setVisibility(View.GONE);
                    userAddress.setVisibility(View.GONE);
                    fullName.setVisibility(View.GONE);
                    updateBtn.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadMain();
                            progressBar.setVisibility(View.VISIBLE);
                            //show();
                        }
                    },5000);
                }



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



    private void SelectImage() {
        updateBtn.setClickable(false);
        // Defining Implicit Intent to mobile gallery

        try {
            if (ActivityCompat.checkSelfPermission(ProfileEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
            }
            else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);

                if (PICK_IMAGE_REQUEST == 22){
                    updateBtn.setClickable(true);
                }
            }
        }catch (Exception e){
            Toast.makeText(this, "Allow permission", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.checkSelfPermission(ProfileEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
            }
            else {

            }
        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();





            try {
                Bitmap original = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                original.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                profilePic.setImageBitmap(original);
                byte[] imageByte = stream.toByteArray();
                uploadingPic(imageByte);
            } catch (IOException e) {
                e.printStackTrace(); }



        }

//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data); }


    }

    private void uploadingPic(byte[] imageByte) {



        if (imageUri != null){



            uploadToFirebase(imageByte);


        }
        else {
            Toast.makeText(ProfileEditActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
        }


    }

    private void uploadToFirebase(byte[] uri) {


        StorageReference reference = FirebaseStorage.getInstance().getReference("/pictures");
        StorageReference fileRef = reference.child(System.currentTimeMillis()+ ".JPEG");
        fileRef.putBytes(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        uploadPicLink =uri.toString();
                        updateBtn.setClickable(true);


                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileEditActivity.this, "Failed select picture again "+e, Toast.LENGTH_LONG).show();

                profilePic.setVisibility(View.VISIBLE);
                userEmail.setVisibility(View.VISIBLE);
                userAddress.setVisibility(View.VISIBLE);
                fullName.setVisibility(View.VISIBLE);
                userPhone.setVisibility(View.VISIBLE);
                updateBtn.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                updateBtn.setClickable(true);
            }
        });


    }

    private void uploadMain() {


         userName = fullName.getText().toString().trim();
         userPhoneN = userPhone.getText().toString().trim();
         emailUser = userEmail.getText().toString().trim();
         userAdd = userAddress.getText().toString().trim();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name", userName);
        map1.put("phone", userPhoneN);
        map1.put("email", emailUser);
        map1.put("address", userAdd);
        map1.put("uid", uid);

        if (uploadPicLink != null) {
            map1.put("image", uploadPicLink);
        } else {
            map1.put("image", defaultPic);
        }


        databaseReference.child(uid).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(uid).child("measurement");
                HashMap<String, Object> map = new HashMap<>();
                map.put("collar", collar);
                map.put("chest", chest);
                map.put("waist", waist);
                map.put("seat", seat);
                map.put("frontWidth", frontWidth);
                map.put("firstBtn", firstBtn);
                map.put("sleeveLength", sleeveLength);
                map.put("shortSleeve", shortSleeve);
                map.put("shortSleeveOpen", shortSleeveOpen);
                map.put("biceps", biceps);
                map.put("forearm", forearm);
                map.put("armhole", armhole);
                map.put("shoulder", shoulder);
                map.put("backWidth", backWidth);
                map.put("shirtLength", shirtLength);
                map.put("cuff", cuff);
                map.put("uid", uid);

                databaseReference1.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(ProfileEditActivity.this, "Your Profiles is successFully updated.", Toast.LENGTH_LONG).show();
                        updateBtn.setText("Update");
                        updateBtn.setClickable(false);
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(ProfileEditActivity.this, UserProfileActivity.class));
                        finish();

                    }
                });

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        userName = getIntent().getExtras().getString("name").toString();
        userPhoneN = getIntent().getExtras().getString("email").toString();
        emailUser = getIntent().getExtras().getString("phone").toString();

        userEmail.setText(emailUser);
        userPhone.setText(userPhoneN);
        fullName.setText(userName);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                     collar= snapshot.child("measurement").child(uid).child("collar").getValue().toString();
                     chest= snapshot.child("measurement").child(uid).child("chest").getValue().toString();
                     waist= snapshot.child("measurement").child(uid).child("waist").getValue().toString();
                     seat= snapshot.child("measurement").child(uid).child("seat").getValue().toString();
                     frontWidth= snapshot.child("measurement").child(uid).child("frontWidth").getValue().toString();
                     firstBtn= snapshot.child("measurement").child(uid).child("firstBtn").getValue().toString();
                     sleeveLength = snapshot.child("measurement").child(uid).child("sleeveLength").getValue().toString();
                     shortSleeve= snapshot.child("measurement").child(uid).child("shortSleeve").getValue().toString();
                     shortSleeveOpen= snapshot.child("measurement").child(uid).child("shortSleeveOpen").getValue().toString();
                     biceps= snapshot.child("measurement").child(uid).child("biceps").getValue().toString();snapshot.child("image").getValue().toString();
                     forearm= snapshot.child("measurement").child(uid).child("forearm").getValue().toString();
                     armhole= snapshot.child("measurement").child(uid).child("armhole").getValue().toString();
                     shoulder= snapshot.child("measurement").child(uid).child("shoulder").getValue().toString();
                     backWidth= snapshot.child("measurement").child(uid).child("backWidth").getValue().toString();
                     shirtLength= snapshot.child("measurement").child(uid).child("shirtLength").getValue().toString();
                     cuff= snapshot.child("measurement").child(uid).child("cuff").getValue().toString();


                }
                else {
                    Toast.makeText(ProfileEditActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}