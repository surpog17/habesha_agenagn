package com.example.habeshaagenagn;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {
  private  String googlePlaceData,url;
  private GoogleMap mMap;
  @Override
  protected String doInBackground(Object... objects) {
    mMap=(GoogleMap) objects[0];
    url=(String)objects[1];
    DownloadUrl downloadUrl= new DownloadUrl ();
    try {
      googlePlaceData= downloadUrl.ReadTheURL (url);
    } catch (IOException e) {
      e.printStackTrace ();
    }


    return googlePlaceData;
  }

  @Override
  protected void onPostExecute(String s) {
    List<HashMap<String,String >> nearByPlacesList=null;
    DataParser dataParser=new DataParser ();
    nearByPlacesList=dataParser.parse (s);
    DisplayNearbyPlaces (nearByPlacesList);
  }
  private void DisplayNearbyPlaces(List<HashMap<String,String >> nearByPlacesList){
    for (int i=0;i<nearByPlacesList.size ();i++){
      MarkerOptions markerOptions= new MarkerOptions ();
      HashMap<String ,String > googleNearbyPlaces=nearByPlacesList.get (i);
      String nameofPlace= googleNearbyPlaces.get ("place_name");
      String vicinity= googleNearbyPlaces.get ("vicinity");
      Double lat=Double.parseDouble (googleNearbyPlaces.get ("lat"));
      Double lng=Double.parseDouble (googleNearbyPlaces.get ("lng"));
      LatLng latLng = new LatLng (lat,lng);
      markerOptions.position (latLng);
      markerOptions.title (nameofPlace+":" +vicinity);
      mMap.addMarker (markerOptions);
      mMap.moveCamera (CameraUpdateFactory.newLatLng (latLng));
      mMap.animateCamera (CameraUpdateFactory.zoomTo (10));
      markerOptions.icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_RED));
    }
  }
}
