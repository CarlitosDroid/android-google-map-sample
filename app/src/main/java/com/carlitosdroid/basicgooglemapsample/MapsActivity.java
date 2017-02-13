package com.carlitosdroid.basicgooglemapsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.carlitosdroid.basicgooglemapsample.listener.OnClickLocationListener;
import com.carlitosdroid.basicgooglemapsample.util.PermissionUtils;
import com.carlitosdroid.basicgooglemapsample.view.dialog_fragment.LocationNeededDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, OnClickLocationListener, View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_PERMISSION_REQUEST_CODE = 3;

    private GoogleMap mMap;
    private FloatingActionButton fabLocationPermission;
    private FloatingActionButton fabRecordAudioPermission;

    private boolean mShowPermissionDeniedDialog = false;

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fabLocationPermission = (FloatingActionButton) findViewById(R.id.fabLocationPermission);
        fabRecordAudioPermission = (FloatingActionButton) findViewById(R.id.fabRecordAudioPermission);

        fabLocationPermission.setOnClickListener(this);
        fabRecordAudioPermission.setOnClickListener(this);
        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION_REQUEST_CODE);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private boolean checkReady() {
        if (mMap == null) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    if(!mMap.isMyLocationEnabled()){
                        mMap.setMyLocationEnabled(true);
                    }
                    fabLocationPermission.setVisibility(View.GONE);
                }
            } else if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    fabRecordAudioPermission.setImageResource(R.drawable.ic_mic_white_24dp);
                    fabRecordAudioPermission.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_yellow_500)));
                }
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            fabRecordAudioPermission.setImageResource(R.drawable.ic_mic_white_24dp);
            fabRecordAudioPermission.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_yellow_500)));
        }

        if (!checkReady()) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(!mMap.isMyLocationEnabled()){
                mMap.setMyLocationEnabled(true);
            }
            fabLocationPermission.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowLocationPermission() {
        startInstalledAppDetailsActivity();
    }

    private void startInstalledAppDetailsActivity() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 0);
    }

    private void updateMyLocation() {
        if (!checkReady()) {
            return;
        }

        // Enable the location layer. Request the location permission if needed.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(!mMap.isMyLocationEnabled()){
                mMap.setMyLocationEnabled(true);
            }
            fabLocationPermission.setVisibility(View.GONE);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display a dialog with rationale.
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION_REQUEST_CODE);
            } else {
                LocationNeededDialogFragment.newInstance()
                        .show(getSupportFragmentManager(), "dialog");
            }
        }
    }

    private void startRecordAudio() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "EMEPZAMOS", Toast.LENGTH_SHORT).show();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)) {
                // Display a dialog with rationale.
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION_REQUEST_CODE);
            } else {

                LocationNeededDialogFragment.newInstance()
                        .show(getSupportFragmentManager(), "dialog");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabLocationPermission:
                updateMyLocation();
                break;
            case R.id.fabRecordAudioPermission:
                startRecordAudio();
                break;
        }
    }
}
