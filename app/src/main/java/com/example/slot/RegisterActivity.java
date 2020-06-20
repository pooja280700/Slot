package com.example.slot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegisterEmailId, etRegisterPassword, etRegisterName, etRegisterPhoneNumber;
    Button btnRegister;
    TextView tvAlreadyRegister, tvRegister;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase Signup;
    DatabaseReference signup;
    private static final String CHANNEL_ID = "Slot Booking";
    public static final String CHANNEL_NAME = "Slot Booking";
    public static final String CHANNEL_DESC = " Slot Booking";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new   NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        etRegisterEmailId = findViewById(R.id.etRegisterEmailId);
        etRegisterName = findViewById(R.id.etRegisterName);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etRegisterPhoneNumber = findViewById(R.id.etRegisterPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        tvAlreadyRegister = findViewById(R.id.tvAlreadyRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        Signup = FirebaseDatabase.getInstance();
        signup = Signup.getReference("Users");



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etRegisterName.getText().toString();
                final String email =etRegisterEmailId.getText().toString();
                final String password = etRegisterPassword.getText().toString();
                final String phonenumber = etRegisterPhoneNumber.getText().toString();

                if(name.length() == 0)
                {
                    etRegisterName.setError("Name is empty");
                    etRegisterName.requestFocus();
                    return;
                }

                if(email.length() == 0)
                {
                    etRegisterEmailId.setError("Email is is empty");
                    etRegisterEmailId.requestFocus();
                    return;
                }

                if(password.length() == 0)
                {
                    etRegisterPassword.setError("Password is empty");
                    etRegisterPassword.requestFocus();
                    return;
                }

                if(password.length() < 6)
                {
                    etRegisterPassword.setError("Password must be greater than 5");
                    etRegisterPassword.requestFocus();
                    return;
                }

                if(phonenumber.length() == 0)
                {
                    etRegisterPhoneNumber.setError("Phone Number is empty");
                    etRegisterPhoneNumber.requestFocus();
                    return;
                }
                if(phonenumber.length() != 10)
                {
                    etRegisterPhoneNumber.setError("Phone number Should contain atleast 10 digits");
                    etRegisterPhoneNumber.requestFocus();
                    return;
                }


                final SignUpdata signUPdata = new SignUpdata(name,email,phonenumber);

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {

                                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();

                                    Signup.getReference("Users").child(firebaseAuth.getUid()).setValue(signUPdata);
                                    Toast.makeText(RegisterActivity.this,
                                            "Registered Successfully!!",Toast.LENGTH_SHORT).show();
                                    displayNotification();


                                    Intent a  = new Intent(RegisterActivity.this,
                                            MainActivity.class);
                                    a.putExtra("Name",name);
                                    startActivity(a);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this,
                                            "invalid info " + task.getException()
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });





            }
        });
        tvAlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b  = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(b);
                finish();

            }
        });
    }

    private void displayNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message_black)
                .setContentTitle("Slot Booking App")
                .setContentText("Booking Successful")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());
    }

}
