package com.gainingskills.tylorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import soup.neumorphism.NeumorphButton;

public class ResetPasswordActivity extends AppCompatActivity {



    private EditText email;
    private NeumorphButton resetBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        email = findViewById(R.id.fEmail);
        resetBtn = findViewById(R.id.fReset);
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword();
            }
        });


    }

    private void resetPassword() {

        String gEmail = email.getText().toString().trim();

        if (TextUtils.isEmpty(gEmail)){
            email.setError("Enter email");
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(gEmail).matches()){

            email.setError("Please provide valid email");
            email.requestFocus();
            return;

        }

        else {
            mAuth.sendPasswordResetEmail(gEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(ResetPasswordActivity.this, "Check your Email to reset your password", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ResetPasswordActivity.this, SignIn_Activity.class));
                        finish();

                    }
                    else {

                        Toast.makeText(ResetPasswordActivity.this, "try again! something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }


    }
}