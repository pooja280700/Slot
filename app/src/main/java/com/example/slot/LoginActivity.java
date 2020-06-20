package com.example.slot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmailId, etLoginPassword;
    Button btnLoginSubmit;
    TextView tvNewUser,tvForgotPassword;
    FirebaseAuth firebaseAuth;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmailId = findViewById(R.id.etLoginEmailId);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvNewUser = findViewById(R.id.tvNewUser);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        firebaseAuth = FirebaseAuth.getInstance();





        user = firebaseAuth.getCurrentUser();
        if (user != null)
        {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(a);
            }
        });

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View v) {


                String email = etLoginEmailId.getText().toString();
                String password = etLoginPassword.getText().toString();

                if(email.length() == 0)
                {
                    etLoginEmailId.setError("Email is is empty");
                    etLoginEmailId.requestFocus();
                    return;
                }

                if(password.length() == 0)
                {
                    etLoginPassword.setError("Password is empty");
                    etLoginPassword.requestFocus();
                    return;
                }

                if(password.length() < 6)
                {
                    etLoginPassword.setError("Password must be greater than 5");
                    etLoginPassword.requestFocus();
                    return;
                }


                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent b = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(b);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "invalid" + task.getException()
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });







               /* Intent a = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(a);*/

                }


        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(a);
                finish();
            }
        });
    }
}
