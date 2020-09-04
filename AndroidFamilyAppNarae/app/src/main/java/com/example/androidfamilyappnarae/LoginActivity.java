package com.example.androidfamilyappnarae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
EditText idinput, pwinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idinput =findViewById(R.id.idinput);
        pwinput=findViewById(R.id.pwinput);

        String strid = idinput.getText().toString();
        String strpw = pwinput.getText().toString();

        String sendmsg = "login.do";
        new Task(sendmsg).execute(strid, strpw);
    }
}