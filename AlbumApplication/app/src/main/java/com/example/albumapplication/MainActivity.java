package com.example.albumapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    ImageView iv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.imageView);
        btn = findViewById(R.id.button);
        //camera needs autopermission

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        //위험권한 모두 허용 ㅡ 전화걸기 etc
        //requestcode 101 : 위험 권한 모두 load --> 메세지 상자 뜨고 허용 눌러주면 된다.
        AutoPermissions.Companion.
                loadAllPermissions(this, 101);

    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //갤러리에 있는데 오픈해서 가겠다.

//값을 돌려받는다
        //requestcode 어디서 가져온건지 구분하는 값
        startActivityForResult(intent, 101);
//값을 가져올때 content resolve를 통해서 가져온다.




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==101){
        if(resultCode==RESULT_OK){

            Uri fileUri= data.getData();
            ContentResolver resolver= getContentResolver();
            try{
//
                InputStream is = resolver.openInputStream(fileUri);
                Bitmap bimap = BitmapFactory.decodeStream(is);
                iv.setImageBitmap(bimap);
                is.close();

                //fileuri가 없을수도 있으니깐
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    }


    //요청한 결과 어떻게 처리하는가
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    ///이 부분 다시하기
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permission denied:" + strings, Toast.LENGTH_LONG)
                .show();
    }

    ///이 부분 다시하기
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permission granted:" + strings, Toast.LENGTH_LONG)
                .show();
    }


}