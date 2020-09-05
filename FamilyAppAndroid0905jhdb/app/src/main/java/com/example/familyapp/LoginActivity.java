package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    TextView tvLogin;
    Button btnLogin;
    EditText edId, edPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edId = findViewById(R.id.login_id);
        edPw = findViewById(R.id.login_pw);
        tvLogin = findViewById(R.id.tvLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edId.getText().toString();
                String pw = edPw.getText().toString();
                if(id.equals("")){
                    tvLogin.setText("아이디를 입력 해주세요.");
                }else if(pw.equals("")) {
                    tvLogin.setText("비밀번호를 입력 해주세요.");
                }else{
                    edId.getText().toString();
                    edPw.getText().toString();

                    try {
                        String sendmsg = "login.do";
                        String result = new Task(sendmsg).execute(id,pw).get();
                        if(result.equals("1")){
                            //로그인 성공시 코드
                            //id, f_code 담아가기
                            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                            intent.putExtra("userId", id);
                            startActivity(intent);
                            //tvLogin.setText("로그인 성공.");
                        }else if(result.equals("0")){
                            tvLogin.setText("비밀번호 에러.");
                        }else{
                            tvLogin.setText("아이디 에러.");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}