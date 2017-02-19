package com.carlitosdroid.basicgooglemapsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.carlitosdroid.basicgooglemapsample.listener.OnClickLocationListener;
import com.carlitosdroid.basicgooglemapsample.util.GeneralUtils;
import com.carlitosdroid.basicgooglemapsample.view.dialog_fragment.LocationNeededDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, OnClickLocationListener,
        View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_PERMISSION_REQUEST_CODE = 3;

    private GoogleMap mMap;
    private FloatingActionButton fabLocationPermission;
    private FloatingActionButton fabRecordAudioPermission;
    private FloatingActionButton fabTest;

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO};


    private static final LatLng STREET6 = new LatLng(-12.042841, -77.036074);

    private static final LatLng STREET4 = new LatLng(-12.005730, -77.013797);

    private static final LatLng STREET5 = new LatLng(-12.038683, -76.975863);

    private static final LatLng STREET2 = new LatLng(-11.958045, -77.004748);

    private static final LatLng STREET1 = new LatLng(-11.983669, -77.006505);

    private static final LatLng STREET3 = new LatLng(-11.947511, -76.985202);


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
        fabTest = (FloatingActionButton) findViewById(R.id.fabTest);

        fabLocationPermission.setOnClickListener(this);
        fabRecordAudioPermission.setOnClickListener(this);
        fabTest.setOnClickListener(this);
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

        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Uses a colored icon.
        mMap.addMarker(new MarkerOptions()
                .position(STREET1)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        // Uses a custom icon with the info window popping out of the center of the icon.
        mMap.addMarker(new MarkerOptions()
                .position(STREET2)
                .title("Sydney")
                .snippet("Population: 4,627,300")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_favorite_red_500_48dp))
                .infoWindowAnchor(0.5f, 0.5f));

        // Creates a draggable marker. Long press to drag.
        mMap.addMarker(new MarkerOptions()
                .position(STREET3)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .draggable(true));
        mMap.setContentDescription("Map with lots of markers.");

        // Creates a draggable marker. Long press to drag.
        mMap.addMarker(new MarkerOptions()
                .position(STREET4)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.addMarker(new MarkerOptions()
                .position(STREET5)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));

        // Vector drawable resource as a marker icon.
        mMap.addMarker(new MarkerOptions()
                .position(STREET6)
                .icon(GeneralUtils.vectorToBitmap(this, R.drawable.ic_android_white_48dp, ContextCompat.getColor(this, R.color.md_teal_500)))
                .title("Alice Springs"));
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
                    if (!mMap.isMyLocationEnabled()) {
                        mMap.setMyLocationEnabled(true);
                    }
                    fabLocationPermission.setVisibility(View.GONE);
                }
            } else if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    fabRecordAudioPermission.setImageResource(R.drawable.ic_mic_white_24dp);
                    fabRecordAudioPermission.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_deep_orange_500)));
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
            fabRecordAudioPermission.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_deep_orange_500)));
        }

        if (!checkReady()) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (!mMap.isMyLocationEnabled()) {
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
            if (!mMap.isMyLocationEnabled()) {
                mMap.setMyLocationEnabled(true);
            }
            fabLocationPermission.setVisibility(View.GONE);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display a dialog with rationale.
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION_REQUEST_CODE);
            } else {
                showDialogPermissionSettings("Active los permisos de localización");
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
                showDialogPermissionSettings("Active los permisos de grabar voz");
            }
        }
    }

    private void showDialogPermissionSettings(String message) {
        LocationNeededDialogFragment.newInstance(message)
                .show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabLocationPermission:
                updateMyLocation();
                break;
            case R.id.fabRecordAudioPermission:
                startRecordAudio();
                break;
            case R.id.fabTest:
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(STREET1)
                        .include(STREET2)
                        .include(STREET4)
                        .include(STREET5)
                        .include(STREET6)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                break;
        }
    }
}
