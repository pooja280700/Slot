package com.example.slot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;

/*import static com.example.slot.App.CHANNEL_1_ID;*/

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button  btnBookingSubmit, btnChsDate, btnChsTime;
    TextView tvUserName;
    FirebaseAuth firebaseAuth;
    EditText etBookingDate, etBookingTime, etBookingWork;
    FirebaseDatabase Booking;
    FloatingActionButton fabDial;
    private NotificationManagerCompat notificationManager;
    private static final String CHANNEL_ID = "Slot Booking";
    public static final String CHANNEL_NAME = "Slot Booking";
    public static final String CHANNEL_DESC = " Slot Booking";


    DatabaseReference bookingref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new   NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        tvUserName = findViewById(R.id.tvUserName);
        etBookingDate = findViewById(R.id.etBookingDate);
        etBookingWork = findViewById(R.id.etBookingWork);
        etBookingTime = findViewById(R.id.etBookingTime);
        notificationManager = NotificationManagerCompat.from(this);
        btnChsDate = findViewById(R.id.btnChsDate);
        btnChsTime = findViewById(R.id.btnChsTime);
        btnBookingSubmit = findViewById(R.id.btnBookingSubmit);
        firebaseAuth = FirebaseAuth.getInstance();
        Booking = FirebaseDatabase.getInstance();
        fabDial = findViewById(R.id.fabDial);
        bookingref = Booking.getReference("BookingData");

        final String email = firebaseAuth.getCurrentUser().getEmail();
        tvUserName.setText("Welcome " + email);





        btnChsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnChsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        fabDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_DIAL);
                a.setData(Uri.parse("tel:" +"9999999999"));
                startActivity(a);
            }
        });







        btnBookingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String work = etBookingWork.getText().toString();
                String date = etBookingDate.getText().toString();
                String Time = etBookingTime.getText().toString();

                if(work.length() == 0)
                {
                    etBookingWork.setError("Please Enter the Reason!!");
                    etBookingWork.requestFocus();
                    return;

                }

                if(date.length() ==0)
                {
                    etBookingDate.setError("Enter date");
                    etBookingDate.requestFocus();
                    return;
                }

                if(Time.length() ==0)
                {
                    etBookingTime.setError("Enter Time");
                    etBookingTime.requestFocus();
                    return;
                }

                final String[] Token = new String[1];

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if(task.isSuccessful()){

                                    String token = task.getResult().getToken();
                                    Token[0] = token;



                                }

                            }
                        });

                final String email = firebaseAuth.getCurrentUser().getEmail();
                String name = getIntent().getStringExtra("Name");

                BookingData bookingData = new BookingData(name,email, work, date, Time);
                Booking.getReference("BookingData").child(firebaseAuth.getUid()).setValue(bookingData);
                Toast.makeText(MainActivity.this,
                        "Booking Successfully",Toast.LENGTH_LONG).show();
                displayNotification();
                                /*Intent x = new Intent(MainActivity.this,NotificationUserActivity.class);
                startActivity(x);*/

                etBookingWork.setText("");
                etBookingDate.setText("");
                etBookingTime.setText("");


            }
        });

    }




    private void showDatePickerDialog(){
    DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)


    );
    datePickerDialog.show();


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month+1;
        String date = "" +year + "/" + dayOfMonth  + "/" + month;
        etBookingDate.setText(date);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etBookingTime.setText("" +hourOfDay + ":" + minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.aboutus == item.getItemId())
        {
            Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(intent);
        }

        if (R.id.Logout == item.getItemId())
        {
            firebaseAuth.signOut();
            Intent b = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(b);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Do you want to exit");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog a = alertDialog.create();
        a.show();
    }// end of onBackpressedNo

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























































































