package com.example.habeshaagenagn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getsinglenearbyPlace(JSONObject googleplaceJson){
        HashMap<String,String> googleplacemap= new HashMap<>();
        String NameofPlace="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";
        try {
            if (!googleplaceJson.isNull ("name")){
                NameofPlace=googleplaceJson.getString ("name");
            }
            if (!googleplaceJson.isNull ("vicinity")){
                vicinity=googleplaceJson.getString ("vicinity");
            }
            latitude=googleplaceJson.getJSONObject ("geometry").getJSONObject ("location").getString ("lat");
            longitude=googleplaceJson.getJSONObject ("geometry").getJSONObject ("location").getString ("lng");
            reference=googleplaceJson.getString ("reference");
            googleplacemap.put ("place_name",NameofPlace);
            googleplacemap.put ("vicinity",vicinity);
            googleplacemap.put ("lat",latitude);
            googleplacemap.put ("lng",longitude);
            googleplacemap.put ("reference",reference);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return googleplacemap;
    }
    private List<HashMap<String ,String >> getAllNearbyplace(JSONArray jsonArray){
        int counter= jsonArray.length ();
        List<HashMap<String ,String>>Nearbyplacelist= new ArrayList<> ();
        HashMap<String,String > Nearbyplacemap=null;
        for (int i=0; i<counter; i++){
            try {
                Nearbyplacemap=getsinglenearbyPlace ((JSONObject) jsonArray.get (i));
                Nearbyplacelist.add (Nearbyplacemap);

            } catch (Exception e) {
                e.printStackTrace ();
            }


        }
        return  Nearbyplacelist;
    }
    public List<HashMap<String ,String >> parse(String jsondata){
        JSONArray jsonArray= null;
        JSONObject jsonObject;

        try {
            jsonObject= new JSONObject (jsondata);
            jsonArray=jsonObject.getJSONArray ("results");
        } catch (JSONException e) {
            e.printStackTrace ();
        }
return  getAllNearbyplace (jsonArray);
    }
}
