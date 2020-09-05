package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JoinSuccessActivity extends AppCompatActivity {

    TextView tvFcode;
    Button btnLoginGo;
    String fCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_success);

        Intent intent = getIntent();
        fCode = intent.getStringExtra("fCode");

        tvFcode = findViewById(R.id.joinsuccess_tv_fcode);
        btnLoginGo = findViewById(R.id.joinsuccess_btn_logingo);
        tvFcode.setText(fCode);
        btnLoginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}