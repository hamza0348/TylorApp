package com.gainingskills.tylorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Splash_Screen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();


        imageView = findViewById(R.id.imageIdSp);
        imageView.animate().
                scaleX(1f)
                .scaleY(1f).
                setDuration(2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mAuth.getCurrentUser() != null) {

                    String uid = mAuth.getUid();
                    String adminUid1 = "75GnlFmRLlYM9jq2fAvtDrlxFqZ2";
                    String adminUid = "v8OKEXhW60OSwSmjpF4PpqXBvez1";
                    if (uid.equals(adminUid) || uid.equals(adminUid1)){

                        Intent intent = new Intent(Splash_Screen.this, TailorMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {

                    startActivity(new Intent(Splash_Screen.this, SignIn_Activity.class));
                    finish();
                }


            }
        }, 3000);

    }
}