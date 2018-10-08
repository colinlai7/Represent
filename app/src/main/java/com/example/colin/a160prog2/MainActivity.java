package com.example.colin.a160prog2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private double lat;
    private double lon;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkPermissions()){
            requestPermissions();
        }
        if (checkPermissions()){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }
        getLastLocation();
        configureZipButton();
        configureLocationButton();
        configureRandomButton();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            }
        });
    }

    private void configureZipButton(){
        // input zip
//        final String zipcode = ((EditText) findViewById(R.id.editText4)).getText().toString();
        Button zipgo = (Button) findViewById(R.id.zipgo);
        zipgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("location", ((EditText) findViewById(R.id.editText4)).getText().toString());
                intent.putExtra("mod", "geocode");
                startActivity(intent);
            }
        });
    }

    private void configureLocationButton(){
        Button locationgo = (Button) findViewById(R.id.locationgo);
        locationgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("location", lat +"," + lon);
                intent.putExtra("mod", "reverse");
                startActivity(intent);
            }
        });
    }

    private void configureRandomButton(){
        Button randomgo = (Button) findViewById(R.id.randomgo);
        randomgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                double start = 35;
                double end = 45;
                double random = new Random().nextDouble();
                double rlat = start + (random * (end - start));
                start = 85;
                end = 120;
                random = new Random().nextDouble();
                double rlon = -1 * (start + (random * (end - start)));
                intent.putExtra("location", rlat + "," + rlon);
                intent.putExtra("mod", "reverse");
                startActivity(intent);
            }

        });
    }
}
