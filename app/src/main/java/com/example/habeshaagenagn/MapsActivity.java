package com.example.habeshaagenagn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng;

    private TextView textView;
    private EditText editTextLat;
    private EditText editTextLong;
    private Marker currentuserlocationmarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);



        textView = findViewById(R.id.textView);
        editTextLat = findViewById(R.id.editTextLat);
        editTextLong = findViewById(R.id.editTextong);
        Intent intent = getIntent();
        String lat = intent.getStringExtra(Electronics_Descriptio.EXTRA_LATITUDE);
        String lng = intent.getStringExtra(Electronics_Descriptio.EXTRA_LONGTUDE);
        String longi = intent.getStringExtra(Driver_Description.EXTRA_LONGTUDE);
        String lati = intent.getStringExtra(Driver_Description.EXTRA_LATITUDE);
        String homelongi = intent.getStringExtra(Homeworks_Description.EXTRA_LONGTUDE);
        String homelati = intent.getStringExtra(Homeworks_Description.EXTRA_LATITUDE);
        String shoplongi = intent.getStringExtra(Shopping_Description.EXTRA_LONGTUDE);
        String shopplati = intent.getStringExtra(Shopping_Description.EXTRA_LATITUDE);
        editTextLat.setText (lat);
        editTextLong.setText (lng);
        editTextLong.setText (longi);
        editTextLat.setText (lati);
        editTextLong.setText (homelongi);
        editTextLat.setText (homelati);
        editTextLong.setText (shoplongi);
        editTextLat.setText (shopplati);
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
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    if (currentuserlocationmarker!=null){
                        currentuserlocationmarker.remove ();
                    }
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions= new MarkerOptions ();
                    markerOptions.position (latLng);
                    markerOptions.title ("ME");
                    markerOptions.icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_BLUE));
                    currentuserlocationmarker= mMap.addMarker (markerOptions);
                    mMap.moveCamera (CameraUpdateFactory.newLatLng (latLng));
                    mMap.animateCamera (CameraUpdateFactory.zoomBy (15));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,MIN_DIST,locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }

    public void getLocationDetails(View view){
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        if (!(editTextLong.getText().toString().isEmpty() || editTextLat.getText().toString().isEmpty()))
        {
            latitude = Double.parseDouble(editTextLat.getText().toString());
            longitude = Double.parseDouble(editTextLong.getText().toString());
            latLng = new LatLng(latitude, longitude);
        }
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knonName = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knonName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title("service provider :"  + address + city + state + country + postalCode + knonName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        textView.setText(address + city + state + country + postalCode + knonName);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}