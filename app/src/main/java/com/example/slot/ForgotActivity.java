package com.example.slot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    Button btnResetPassword, btnForgotBack;
    EditText etForgotEmailId;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnForgotBack = findViewById(R.id.btnForgotBack);
        etForgotEmailId = findViewById(R.id.etForgotEmailId);
        firebaseAuth= FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=etForgotEmailId.getText().toString();
                if(email.length() == 0)
                {
                    etForgotEmailId.setError("Please Enter Email Id");
                    etForgotEmailId.requestFocus();
                    return;

                }
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotActivity.this,
                                            "check email", Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(ForgotActivity.this,LoginActivity.class);
                                    startActivity(a);
                                    finish();

                                }
                                else {
                                    Toast.makeText(ForgotActivity.this,
                                            "issue" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }



        });

        btnForgotBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(a);
                finish();
            }
        });


    }
}
