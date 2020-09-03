package com.example.googlemapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AppComponentFactory;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

//일부분만 보게 fragment 자원 절약
//현재 보이는 내용만 fragment로 보여줌
public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    private GoogleMap mMap;
    Button btn;
    SupportMapFragment mapFragment;
    MarkerOptions myLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

            }
        });
        try {
            MapsInitializer.initialize(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }


        });
        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
//위치 받아오기
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {

                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                String message = "내 위치 -> Latitude: " + latitude + "\nLongtitude:" + longtitude;
                Log.d("Map", message);
            }
            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "내 위치 확인 요청함  ",
                    Toast.LENGTH_SHORT).show();

        } catch
        (SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {

            Double latitude = location.getLatitude();
            Double longtitude = location.getLongitude();
            String message = "내 위치 -> Latitude: " + latitude + "\nLongtitude:" + longtitude;
            Log.d("Map", message);
            showCurrentLocation(latitude, longtitude);
 

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {


        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }
    }

    //한번만 보는 것이 아니라 위치 업데이트 할때, 버튼 누를때 등
    private void showCurrentLocation(Double latitude, Double longtitude) {
        LatLng curPoint = new LatLng(latitude, longtitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        showMarker(curPoint);
    }

    private void showMarker(LatLng curPoint) {
        //한번도 만들어진 적이 없다면 marker를 만들면 된다.
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);


            myLocationMarker.title("내 위치\n ");

            myLocationMarker.snippet("GPS로 확인한 위치");

            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));

            mMap.addMarker(myLocationMarker);

        } else {

            myLocationMarker.position(curPoint);
            mMap.addMarker(myLocationMarker);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permissions denied:" + strings.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permissions granted:" + strings.length,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        //seoul : 37.5665° N, 126.9780° E
//        LatLng sydney = new LatLng(-34, 151);
//        //marker에 타이틀
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        //카메라로 어디를 보는지 ( sydney 위치)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
}
