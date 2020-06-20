package com.example.slot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    TextView tv1, tvMultilineText;
    Button btnAboutUsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tv1 = findViewById(R.id.tv1);
        tvMultilineText = findViewById(R.id.tvMultilineText);
        btnAboutUsBack = findViewById(R.id.btnAboutUsBack);

        btnAboutUsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
