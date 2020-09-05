package com.example.androidfamilyappnarae.ui.dailyquestionbox;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidfamilyappnarae.R;
import com.example.androidfamilyappnarae.ui.gallery.GalleryViewModel;

public class dailyquestionFragment  extends Fragment {

    private GalleryViewModel dailyquestionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyquestionViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.answeringdailyquestion_activity, container, false);
       //fragment에 있는 3개의 이미지를 아이디를 가지고 받아온다.
        final ImageView imgletter = root.findViewById(R.id.imgletter);
       final ImageView imgcamera =root.findViewById(R.id.imgcamera);
       final ImageView imgcassette = root.findViewById(R.id.imgcassette);
       //fragment에 있는 text를 아이디를 가지고 받아온다.
        final TextView textView = root.findViewById(R.id.txttodayquestion);
        dailyquestionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
}
