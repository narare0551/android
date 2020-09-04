package com.example.androidfamilyappnarae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class signupActivity extends AppCompatActivity {


EditText id, pw, pwconfirm, name, phonenum , bday, nickname, role ,fcode;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        id = findViewById(R.id.idinput);
        pw = findViewById(R.id.pwinput);
        pwconfirm = findViewById(R.id.pwconfirm);
        phonenum = findViewById(R.id.phonenum);
        bday = findViewById(R.id.birthday);
        nickname = findViewById(R.id.nickname);
        role = findViewById(R.id.role);
        fcode = findViewById(R.id.fcode);




     String strid=id.getText().toString();
     String strpw = pw.getText().toString();

            String strpwconfirm =pwconfirm.getText().toString();
        String strname = name.getText().toString();
        String strbday = bday.getText().toString();
        String strnickname = nickname.getText().toString();
        String strrole = role.getText().toString();
        String strfcode = fcode.getText().toString();


        String sendmsg = "join.do";
        new Task(sendmsg).execute(strid, strpw, strpwconfirm,strbday,
                strname, strbday, strnickname,strrole, strfcode
        );
    }
}