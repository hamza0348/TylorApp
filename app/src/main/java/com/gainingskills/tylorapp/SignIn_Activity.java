package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import soup.neumorphism.NeumorphButton;

public class SignIn_Activity extends AppCompatActivity {

    NeumorphButton registerBTN, loginBTN, resetBtn;
    private FirebaseAuth mAuth;
    EditText EmailLg, passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        EmailLg = findViewById(R.id.email);
        passwordEd = findViewById(R.id.layout_password);
        registerBTN = findViewById(R.id.registerbtn);
        loginBTN = findViewById(R.id.loginbtn);
        resetBtn = findViewById(R.id.forgotPassword);
        mAuth = FirebaseAuth.getInstance();



        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginBTN.setClickable(false);
                loginBTN.setText("wait");
                SignInHere();



            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn_Activity.this, ResetPasswordActivity.class));
            }
        });

    }


    private void SignInHere() {

        mAuth = FirebaseAuth.getInstance();


        if (TextUtils.isEmpty(EmailLg.getText())) {

            EmailLg.setError("Enter Email");

        } else if (TextUtils.isEmpty(passwordEd.getText())) {

            passwordEd.setError("Enter Password");

        } else {

            String email = EmailLg.getText().toString().trim();
            String password = passwordEd.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                loginBTN.setClickable(true);
                                loginBTN.setText("Signed in");

                                Toast.makeText(SignIn_Activity.this, "Successfully", Toast.LENGTH_SHORT).show();
                                String uid = mAuth.getUid();
                                String adminUid1 = "admin@gmail.com";
                                String adminUid = "admin1@gmail.com";
                                if (adminUid1.equals(EmailLg.getText().toString()) || adminUid.equals(EmailLg.getText().toString())){

                                    Intent intent = new Intent(SignIn_Activity.this, TailorMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent = new Intent(SignIn_Activity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                loginBTN.setClickable(true);
                                loginBTN.setText("Sign in");
                                Toast.makeText(SignIn_Activity.this, "Error: try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


    }


}