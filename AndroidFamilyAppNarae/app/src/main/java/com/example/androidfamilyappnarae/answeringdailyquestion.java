package com.example.androidfamilyappnarae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidfamilyappnarae.ui.main.AnsweringdailyquestionFragment;

public class answeringdailyquestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answeringdailyquestion_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AnsweringdailyquestionFragment.newInstance())
                    .commitNow();
        }
    }
}