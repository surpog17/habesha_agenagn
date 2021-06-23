package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static com.example.habeshaagenagn.Driver_List.EXTRA_PLACE;
import static com.example.habeshaagenagn.Driver_List.EXTRA_URL;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_ADDPHONE;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_DES;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_EMAIL;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_EXP;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_FNAME;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LASTNAME;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LAT;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LONG;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_PHONE;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_WORK;

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
public class Electronics_Descriptio extends AppCompatActivity {
    private static final int ReQUEST_CALL = 1;
    ImageView emailsend;
    public static final String EXTRA_EMAILSEND = "com.example.EXTRA_EMAIL";
    public static final String EXTRA_LATITUDE= "com.example.EXTRA_LATITUDE";
    public static final String EXTRA_LONGTUDE = "com.example.EXTRA_LONGTUDE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_electronics_descriptio);
        final Intent intent = getIntent ();
        emailsend=findViewById (R.id.emailsend);
        final String first = intent.getStringExtra (EXTRA_FNAME);
        final String last = intent.getStringExtra (EXTRA_LASTNAME);
        String work = intent.getStringExtra (EXTRA_WORK);
        final String exp = intent.getStringExtra (EXTRA_EXP);
        final String email = intent.getStringExtra (EXTRA_EMAIL);
        final String phone = intent.getStringExtra (EXTRA_PHONE);
        final String addphone = intent.getStringExtra (EXTRA_ADDPHONE);
        String description = intent.getStringExtra (EXTRA_DES);
        String url=intent.getStringExtra (EXTRA_URL);
        final String lng=intent.getStringExtra (EXTRA_LONG);
        final String lat=intent.getStringExtra (EXTRA_LAT);
        String place=intent.getStringExtra (EXTRA_PLACE);
        TextView fname = findViewById (R.id.firstelectronics);
        fname.setText (first);
        TextView lname = findViewById (R.id.lastelectronics);
        lname.setText (last);
        TextView works = findViewById (R.id.workelectronics);
        works.setText (work);
        TextView exper = findViewById (R.id.experienceelectrioncs);
        exper.setText (exp);
        final TextView emails = findViewById (R.id.emailelectronics);
        emails.setText (email);
        final TextView phoneno = findViewById (R.id.phoneelectronics);
        phoneno.setText (phone);
        final TextView addphoneno = findViewById (R.id.add_elect);
        addphoneno.setText (addphone);
        TextView desc = findViewById (R.id.descriptionelectroincs);
        desc.setText (description);
        TextView longi = findViewById (R.id.lng);
        longi.setText (lng);
        TextView lati = findViewById (R.id.lat);
        lati.setText (lat);
        TextView plc = findViewById (R.id.place_ele);
        plc.setText (place);
        ImageView imagedes=findViewById (R.id.elecimage);
        Picasso.with(this).load(url).fit().centerCrop ().into(imagedes);
        ImageView imagecall = findViewById (R.id.phonecall);
        ImageView imagecallone = findViewById (R.id.callone);
        ImageView imagecalltwo = findViewById (R.id.calltwo);
        final ImageView currentlocation = findViewById (R.id.currentlocation);
        emailsend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String emailsend=phoneno.getText ().toString ();
                intent.putExtra (EXTRA_EMAILSEND,email);
               startActivity (new Intent (Electronics_Descriptio.this,Emailsend.class));
               finish ();
            }
        });

        currentlocation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Electronics_Descriptio.this, MapsActivity.class);
                intent.putExtra(EXTRA_LATITUDE, lat );
                intent.putExtra(EXTRA_LONGTUDE, lng);
                startActivity(intent);

            }
        });
        imagecalltwo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall2();
            }
            private void MakePhoneCall2() {
                if (ContextCompat.checkSelfPermission (Electronics_Descriptio.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Electronics_Descriptio.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + addphone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+first+" "+last,Toast.LENGTH_LONG).show ();
                }
            }
        });
        imagecallone.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall ();
            }
            private void MakePhoneCall() {
                if (ContextCompat.checkSelfPermission (Electronics_Descriptio.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Electronics_Descriptio.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + email;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+first+" "+last,Toast.LENGTH_LONG).show ();
                }
            }

        });
        imagecall.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall();
            }
            private void MakePhoneCall()  {
                if (ContextCompat.checkSelfPermission (Electronics_Descriptio.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Electronics_Descriptio.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + email;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+""+first+""+last,Toast.LENGTH_LONG).show ();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ReQUEST_CALL){
            if (grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText (this,"permission DENIED",Toast.LENGTH_SHORT).show ();
            }
        }
    }


}


