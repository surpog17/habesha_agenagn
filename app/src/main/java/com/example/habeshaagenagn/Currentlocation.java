package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import es.dmoral.toasty.Toasty;

public class Currentlocation extends FragmentActivity implements OnMapReadyCallback {
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.currentlocation);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient (this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;

        }
        Task<Location> task= fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener (new OnSuccessListener<Location> () {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentlocation =location;
                    Toasty.info (getApplicationContext (), currentlocation.getLatitude ()
                            +""+currentlocation.getLongitude (),Toast.LENGTH_SHORT).show ();
                    SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager ()
                            .findFragmentById (R.id.google_map);
                    supportMapFragment.getMapAsync (Currentlocation.this);

                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentlocation.getLatitude (),currentlocation.getLongitude());
        googleMap.addMarker(new MarkerOptions ()
                .position(latLng)
                .draggable(true)
                .title(""+currentlocation.getLatitude ()+currentlocation.getLongitude ()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation ();

                }
                break;
        }
    }

}
