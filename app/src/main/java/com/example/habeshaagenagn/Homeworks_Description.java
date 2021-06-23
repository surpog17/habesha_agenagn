package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

import static com.example.habeshaagenagn.Homeworks_list.EXTRA_PLACE;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_ADDPHONE;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_COMPANYNAME;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_DES;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_EMAIL;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_LAT;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_LONG;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_PHONE;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_SERVICETYPE;
import static com.example.habeshaagenagn.Homeworks_list.EXTRA_URL;

public class Homeworks_Description extends AppCompatActivity {
    private static final int ReQUEST_CALL = 1;
    ImageView emailsend;
    public static final String EXTRA_EMAILSEND = "com.example.EXTRA_EMAIL";
    public static final String EXTRA_LATITUDE= "com.example.EXTRA_LATITUDE";
    public static final String EXTRA_LONGTUDE = "com.example.EXTRA_LONGTUDE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.homeworks_description);
        final Intent intent = getIntent ();
        emailsend=findViewById (R.id.emailsend);;
        String service = intent.getStringExtra (EXTRA_SERVICETYPE);
        final String company = intent.getStringExtra (EXTRA_COMPANYNAME);
        final String email = intent.getStringExtra (EXTRA_EMAIL);
        final String phone = intent.getStringExtra (EXTRA_PHONE);
        final String addphone = intent.getStringExtra (EXTRA_ADDPHONE);
        final String description = intent.getStringExtra (EXTRA_DES);
        final String url=intent.getStringExtra (EXTRA_URL);
        final String lng=intent.getStringExtra (EXTRA_LONG);
        final String lat=intent.getStringExtra (EXTRA_LAT);
        String place=intent.getStringExtra (EXTRA_PLACE);
        TextView companyname = findViewById (R.id.companyname);
        companyname.setText (service);
        TextView servicetypes = findViewById (R.id.service_type);
        servicetypes.setText (company);
        final TextView emails = findViewById (R.id.homeemail);
        emails.setText (phone);
        final TextView phoneno = findViewById (R.id.phone_home);
        phoneno.setText (email);
        final TextView addphoneno = findViewById (R.id.add_home);
        addphoneno.setText (addphone);
        TextView desc = findViewById (R.id.home_description);
        desc.setText (url);
        TextView longi = findViewById (R.id.lng);
        longi.setText (place);
        TextView lati = findViewById (R.id.lat);
        lati.setText (lng);
        TextView plc = findViewById (R.id.place_home);
        plc.setText (lat);
        final ImageView imagedes=findViewById (R.id.homeimage);
        Picasso.with(this).load(description).fit().centerCrop ().into(imagedes);
        ImageView imagecall = findViewById (R.id.phonecall);
        ImageView imagecallone = findViewById (R.id.callone);
        ImageView imagecalltwo = findViewById (R.id.calltwo);
        final ImageView currentlocation = findViewById (R.id.currentlocation);
        emailsend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String emailsend=phoneno.getText ().toString ();
                intent.putExtra (EXTRA_EMAILSEND,email);
                startActivity (new Intent (Homeworks_Description.this,Emailsend.class));
                finish ();
            }
        });

        currentlocation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homeworks_Description.this, MapsActivity.class);
                intent.putExtra(EXTRA_LATITUDE,lng);
                intent.putExtra(EXTRA_LONGTUDE,url );
                startActivity(intent);

            }
        });
        imagecalltwo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall2();
            }
            private void MakePhoneCall2() {
                if (ContextCompat.checkSelfPermission (Homeworks_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Homeworks_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + addphone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+company,Toast.LENGTH_LONG).show ();
                }
            }
        });
        imagecallone.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall ();
            }
            private void MakePhoneCall() {
                if (ContextCompat.checkSelfPermission (Homeworks_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Homeworks_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + email;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+company,Toast.LENGTH_LONG).show ();
                }
            }

        });
        imagecall.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall();
            }
            private void MakePhoneCall()  {
                if (ContextCompat.checkSelfPermission (Homeworks_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Homeworks_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + email;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+""+company,Toast.LENGTH_LONG).show ();
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


