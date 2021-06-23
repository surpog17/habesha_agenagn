package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener  ;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import es.dmoral.toasty.Toasty;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentuserlocationmarker ;
    private static final int Request_User_Location_code=99;
    private double latitide,longitude;
    private  int proximityrad=10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_google_maps);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            checkUserLocationPermission ();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);
    }
    public void onClick(View v) {
        String hospital="hospital",school="school", restaurant="restaurant", atm="atm", bank="bank",supermarket="supermarket";

        Object transferData[]= new Object[2];
        GetNearbyPlaces getNearbyPlaces= new GetNearbyPlaces ();
        switch (v.getId ()) {
            case R.id.search_address:
                EditText addressfield = findViewById (R.id.location_search);
                String address = addressfield.getText ().toString ();
                List<Address> addressList = null;
                 MarkerOptions userMarkerOption = new MarkerOptions ();
                if (!TextUtils.isEmpty (address)) {
                    Geocoder geocoder = new Geocoder (this);
                    try {
                        addressList = geocoder.getFromLocationName (address, 6);

                        if (addressList != null) {


                            for (int i = 0; i < addressList.size (); i++) {
                                Address userAddress = addressList.get (i);
                                LatLng latLng = new LatLng (userAddress.getLatitude (), userAddress.getLongitude ());
                                userMarkerOption.position (latLng);
                                userMarkerOption.title (address);
                                mMap.addMarker (userMarkerOption);
                                mMap.moveCamera (CameraUpdateFactory.newLatLng (latLng));
                                mMap.animateCamera (CameraUpdateFactory.zoomTo (10));
                                userMarkerOption.icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_GREEN));
                                currentuserlocationmarker = mMap.addMarker (userMarkerOption);

                            }
                        } else {
                            Toasty.error (this, "Location not found", Toast.LENGTH_SHORT).show ();
                        }
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
                else {
                    Toasty.info (this,"Please write any location name",Toast.LENGTH_SHORT).show ();
                }
                break;

            case R.id.hospital_nearby:
                String url= getUrl(latitide,longitude,hospital);
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=url;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby Hospitals",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby Hospitals",Toast.LENGTH_SHORT).show ();
                break;

            case R.id.school_nearby:
                String urls= getUrl(latitide,longitude,school);
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=urls;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby School",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby School",Toast.LENGTH_SHORT).show ();
                break;

            case R.id.restaurants_nearby:
                String urlr= getUrl(latitide,longitude,restaurant
                );
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=urlr;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby Restaurants",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby Restaurants",Toast.LENGTH_SHORT).show ();
                break;
            case R.id.atm_nearby:
                String urlatm= getUrl(latitide,longitude,atm
                );
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=urlatm;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby Atm",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby Atm",Toast.LENGTH_SHORT).show ();
                break;
            case R.id.bank_nearby:
                String urlbank= getUrl(latitide,longitude,bank
                );
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=urlbank;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby Bank",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby Bank",Toast.LENGTH_SHORT).show ();
                break;
            case R.id.supermarket_nearby:
                String urlsuper =getUrl(latitide,longitude,supermarket
                );
                mMap.clear ();
                transferData[0]=mMap;
                transferData[1]=urlsuper;
                getNearbyPlaces.execute (transferData);
                Toasty.info (this,"Searching for Nearby supermarket",Toast.LENGTH_SHORT).show ();
                Toasty.info (this,"Showing  Nearby supermarket",Toast.LENGTH_SHORT).show ();
                break;
        }
        }



    private String getUrl(double latitide,double longitude, String nearbyplace){
        StringBuilder googleurl=new StringBuilder ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleurl.append ("location="+latitide+","+longitude);
        googleurl.append ("&radius="+ proximityrad);
        googleurl.append ("&type="+nearbyplace);
        googleurl.append ("&sensor=true");
        googleurl.append ("&key="+"AIzaSyAySuZAK9L3DQOewDhRRzMien1zfYiqArE");
        Log.d ("GoogleMapActivity","url="+googleurl.toString ());
        return googleurl.toString ();



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient ();
            mMap.setMyLocationEnabled (true);
        }

    }
    public boolean checkUserLocationPermission(){
        if (ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale (this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }
            else {
                ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }
            return false;
        }
        else {
            return true;
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_User_Location_code:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient==null){
                            buildGoogleApiClient ();
                        }
                        mMap.setMyLocationEnabled (true);
                    }

                }
                else{
                    Toasty.error (this,"Permission Denied....", Toast.LENGTH_SHORT).show ();
                }
                return;
        }
    }


    protected synchronized void buildGoogleApiClient(){
        googleApiClient=new GoogleApiClient.Builder (this)
                .addConnectionCallbacks (this)
                .addOnConnectionFailedListener (this)
                .addApi (LocationServices.API)
                .build ();
        googleApiClient.connect ();
    }
    @Override
    public void onLocationChanged(Location location) {
        latitide=location.getLatitude ();
        longitude=location.getLongitude ();
    lastlocation=location;
    if (currentuserlocationmarker!=null){
        currentuserlocationmarker.remove ();
    }
    LatLng latLng= new LatLng (location.getLatitude (),location.getLongitude ());
    MarkerOptions markerOptions= new MarkerOptions ();
    markerOptions.position (latLng);
    markerOptions.title ("current location");
    markerOptions.icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_BLUE));
    currentuserlocationmarker= mMap.addMarker (markerOptions);
    mMap.moveCamera (CameraUpdateFactory.newLatLng (latLng));
    mMap.animateCamera (CameraUpdateFactory.zoomBy (12));
    if (googleApiClient!=null){
        LocationServices.FusedLocationApi.removeLocationUpdates (googleApiClient,this);
    }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest= new LocationRequest ();
        locationRequest.setInterval (1100);
        locationRequest.setFastestInterval (1100);
        locationRequest.setPriority (LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates (googleApiClient,locationRequest,this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
