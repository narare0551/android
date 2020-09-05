package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceAnswerActivity extends AppCompatActivity {

    Button btnCamera, btnAudio, btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_answer);

        btnCamera = findViewById(R.id.choiceanswer_btn_camerago);
        btnAudio = findViewById(R.id.choiceanswer_btn_audiogo);
        btnPost = findViewById(R.id.choiceanswer_btn_postgo);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnswerCameraActivity.class);
                //회원아이디,가족코드,q_no 담아가야함
                startActivity(intent);
            }
        });

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnswerAudioActivity.class);
                //회원아이디,가족코드,q_no 담아가야함
                startActivity(intent);
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnswerPostActivity.class);
                //회원아이디,가족코드,q_no 담아가야함
                startActivity(intent);
            }
        });
    }
}