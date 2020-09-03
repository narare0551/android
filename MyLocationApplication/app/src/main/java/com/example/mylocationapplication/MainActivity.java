package com.example.mylocationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity
        implements AutoPermissionsListener {
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this,101);
    }

    private void startLocationService(){
        LocationManager manager=
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try{
            Location location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location !=null){
                double latitude=location.getLatitude();
                double longitude=location.getLongitude();
                String message="최근 위치-> Latitude:"+latitude
                        +"\nLongitude:"+longitude;
                tv.setText(message);
            }

            GPSListener listener=new GPSListener();
            long minTime=10000;
            float minDistance=0;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    minTime,minDistance,listener);
            Toast.makeText(getApplicationContext(),"내 위치확인 요청",
                    Toast.LENGTH_LONG).show();

        }catch (SecurityException ex){
            ex.printStackTrace();
        }

    }

    class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Double latitude=location.getLatitude();
            Double longitude=location.getLongitude();
            String message="내 위치 ->Latitude:"+latitude+
                    "\nLongitude:"+longitude;
            tv.setText(message);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this,"permissions denied:"+strings.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this,"permissions granted:"+strings.length,
                Toast.LENGTH_LONG).show();

    }
}