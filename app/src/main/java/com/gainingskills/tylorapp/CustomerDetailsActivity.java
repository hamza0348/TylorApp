package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import soup.neumorphism.NeumorphButton;

public class CustomerDetailsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DatabaseReference ref;
    String uid;
    CircleImageView imageView;
    TextView name,phone,address;
    NeumorphButton updateBtn;
    EditText collarEd,chestEd,waistEd,seatEd,frontWidthEd,firstBtnEd,sleeveLengthEd,shortSleeveED,shortSleeveOpenEd,bicepsEd,forearmEd,armholeEd,shoulderEd,backWidthEd,shirtLengthEd,cuffEd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();

        mToolbar.setTitle("Details");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        uid = getIntent().getExtras().getString("uuid");
        ref = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){

                  String cName = snapshot.child("name").getValue().toString();
                  String cPhone = snapshot.child("phone").getValue().toString();
                  String cAddress = snapshot.child("address").getValue().toString();
                  String cImage = snapshot.child("image").getValue().toString();

                  String collar= snapshot.child("measurement").child(uid).child("collar").getValue().toString();
                  String chest=  snapshot.child("measurement").child(uid).child("chest").getValue().toString();
                  String waist= snapshot.child("measurement").child(uid).child("waist").getValue().toString();
                  String seat= snapshot.child("measurement").child(uid).child("seat").getValue().toString();
                  String frontWidth= snapshot.child("measurement").child(uid).child("frontWidth").getValue().toString();
                  String firstBtn= snapshot.child("measurement").child(uid).child("firstBtn").getValue().toString();
                  String sleeveLength = snapshot.child("measurement").child(uid).child("sleeveLength").getValue().toString();
                  String shortSleeve= snapshot.child("measurement").child(uid).child("shortSleeve").getValue().toString();
                  String shortSleeveOpen= snapshot.child("measurement").child(uid).child("shortSleeveOpen").getValue().toString();
                  String biceps= snapshot.child("measurement").child(uid).child("biceps").getValue().toString();snapshot.child("image").getValue().toString();
                  String forearm= snapshot.child("measurement").child(uid).child("forearm").getValue().toString();
                  String armhole= snapshot.child("measurement").child(uid).child("armhole").getValue().toString();
                  String shoulder= snapshot.child("measurement").child(uid).child("shoulder").getValue().toString();
                  String backWidth= snapshot.child("measurement").child(uid).child("backWidth").getValue().toString();
                  String shirtLength= snapshot.child("measurement").child(uid).child("shirtLength").getValue().toString();
                  String cuff= snapshot.child("measurement").child(uid).child("cuff").getValue().toString();

                  name.setText(cName);
                  phone.setText(cPhone);
                  address.setText(cAddress);
                  collarEd.setText(collar);
                  chestEd.setText(chest);
                  waistEd.setText(waist);
                  seatEd.setText(seat);
                  frontWidthEd.setText(frontWidth);
                  firstBtnEd.setText(firstBtn);
                  sleeveLengthEd.setText(sleeveLength);
                  shortSleeveED.setText(shortSleeve);
                  shortSleeveOpenEd.setText(shortSleeveOpen);
                  bicepsEd.setText(biceps);
                  forearmEd.setText(forearm);
                  armholeEd.setText(armhole);
                  shoulderEd.setText(shoulder);
                  backWidthEd.setText(backWidth);
                  shirtLengthEd.setText(shirtLength);
                  cuffEd.setText(cuff);
                  Glide.with(CustomerDetailsActivity.this).load(cImage).placeholder(R.drawable.user).into(imageView);


              }
              else {
                  Toast.makeText(CustomerDetailsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBtn.setClickable(false);
                updateBtn.setText("wait");
                String collar=    collarEd.getText().toString();
                String chest=     chestEd.getText().toString();
                String waist= waistEd.getText().toString();
                String seat= seatEd.getText().toString();
                String frontWidth= frontWidthEd.getText().toString();
                String firstBtn= firstBtnEd.getText().toString();
                String sleeveLength = sleeveLengthEd.getText().toString();
                String shortSleeve= shortSleeveED.getText().toString();
                String shortSleeveOpen= shortSleeveOpenEd.getText().toString();
                String biceps= bicepsEd.getText().toString();
                String forearm= forearmEd.getText().toString();
                String armhole= armholeEd.getText().toString();
                String shoulder= shoulderEd.getText().toString();
                String backWidth= backWidthEd.getText().toString();
                String shirtLength= shirtLengthEd.getText().toString();
                String cuff= cuffEd.getText().toString();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("measurement");
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("collar", collar+" collar");
                map1.put("chest", chest+" chest");
                map1.put("waist", waist+" waist");
                map1.put("seat", seat+" seat");
                map1.put("frontWidth", frontWidth+" frontWidth");
                map1.put("firstBtn", firstBtn+" firstBtn");
                map1.put("sleeveLength", sleeveLength+" sleeveLength");
                map1.put("shortSleeve", shortSleeve+" shortSleeve");
                map1.put("shortSleeveOpen", shortSleeveOpen+" shortSleeveOpen");
                map1.put("biceps", biceps+" biceps");
                map1.put("forearm", forearm+" forearm");
                map1.put("armhole", armhole+" armhole");
                map1.put("shoulder", shoulder+" shoulder");
                map1.put("backWidth", backWidth+" backWidth");
                map1.put("shirtLength", shirtLength+" shirtLength");
                map1.put("cuff", cuff+" cuff");
                map1.put("uid", uid);



                databaseReference.child(uid).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(CustomerDetailsActivity.this, "Your Profiles is successFully updated.", Toast.LENGTH_LONG).show();
                        updateBtn.setText("Update");
                        updateBtn.setClickable(false);

                    }
                });



            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String toNumber = phone.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text="));
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbarIDCustomersDetails);
        imageView = findViewById(R.id.cusDetailPic);
        name = findViewById(R.id.cusDetailName);
        phone = findViewById(R.id.cusDetailPhone);
        address = findViewById(R.id.customDetailAddress);
        updateBtn = findViewById(R.id.customerUpdateBtnId);
        collarEd = findViewById(R.id.collarId);
        chestEd = findViewById(R.id.chestId);
        waistEd = findViewById(R.id.waistId);
        seatEd = findViewById(R.id.seatId);
        frontWidthEd = findViewById(R.id.fWidthId);
        firstBtnEd = findViewById(R.id.firstBtnId);
        sleeveLengthEd = findViewById(R.id.sleeveLengthId);
        shortSleeveED = findViewById(R.id.shortSleeveId);
        shortSleeveOpenEd = findViewById(R.id.shortSleeveOpenId);
        bicepsEd = findViewById(R.id.bicepsId);
        forearmEd = findViewById(R.id.forearmId);
        armholeEd = findViewById(R.id.armHoleId);
        shoulderEd = findViewById(R.id.shoulderId);
        backWidthEd = findViewById(R.id.backWidthId);
        shirtLengthEd = findViewById(R.id.shirtLengthId);
        cuffEd = findViewById(R.id.cuffId);


    }

}