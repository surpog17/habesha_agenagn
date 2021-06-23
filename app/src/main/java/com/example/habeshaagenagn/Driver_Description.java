package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
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

import static com.example.habeshaagenagn.Driver_List.EXTRA_ADDPHONE;
import static com.example.habeshaagenagn.Driver_List.EXTRA_PLACE;
import static com.example.habeshaagenagn.Driver_List.EXTRA_URL;
import static com.example.habeshaagenagn.Driver_List.EXTRA_DES;
import static com.example.habeshaagenagn.Driver_List.EXTRA_EMAIL;
import static com.example.habeshaagenagn.Driver_List.EXTRA_FNAME;
import static com.example.habeshaagenagn.Driver_List.EXTRA_LASTNAME;
import static com.example.habeshaagenagn.Driver_List.EXTRA_LATI;
import static com.example.habeshaagenagn.Driver_List.EXTRA_LONG;
import static com.example.habeshaagenagn.Driver_List.EXTRA_PHONE;
import static com.example.habeshaagenagn.Driver_List.EXTRA_WORK;

public class Driver_Description extends AppCompatActivity {
    private static final int ReQUEST_CALL = 1;
    public static final String EXTRA_EMAILSEND = "com.example.EXTRA_EMAIL";
    public static final String EXTRA_LATITUDE= "com.example.EXTRA_LATITUDE";
    public static final String EXTRA_LONGTUDE = "com.example.EXTRA_LONGTUDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.dirver_description);
        final Intent intent = getIntent ();
        final String first=intent.getStringExtra (EXTRA_FNAME);
        final String last=intent.getStringExtra (EXTRA_LASTNAME);
        String work=intent.getStringExtra (EXTRA_WORK);
        final String email=intent.getStringExtra (EXTRA_EMAIL);
        final String phone=intent.getStringExtra (EXTRA_PHONE);
        final String addphone=intent.getStringExtra (EXTRA_ADDPHONE);
        final String place=intent.getStringExtra (EXTRA_PLACE);
        String description=intent.getStringExtra (EXTRA_DES);
        String url=intent.getStringExtra (EXTRA_URL);
       final String lng=intent.getStringExtra (EXTRA_LONG);
       final String lat=intent.getStringExtra (EXTRA_LATI);
        TextView fname = findViewById (R.id.firstdriver);
        fname.setText (first);
        TextView lname = findViewById (R.id.lastdriver);
        lname.setText (last);
        TextView works = findViewById (R.id.workdriver);
        works.setText (work);
        final TextView emails = findViewById (R.id.emaildirvers);
        emails.setText (email);
        final TextView phoneno = findViewById (R.id.phonedirvers);
        phoneno.setText (phone);
        TextView addphoneno = findViewById (R.id.addphonedirver);
        addphoneno.setText (addphone);
        TextView places = findViewById (R.id.place);
        places.setText (place);
        TextView desc = findViewById (R.id.descriptiondirvers);
        desc.setText (description);
        ImageView imagedes=findViewById (R.id.imagedescriptio);
        TextView longi = findViewById (R.id.lngi);
        longi.setText (lng);
        TextView lati = findViewById (R.id.lati);
        lati.setText (lat);
        Picasso.with(this).load(url).fit().centerCrop ().into(imagedes);
        ImageView imagecall = findViewById (R.id.callone);
        ImageView imagecalltwo = findViewById (R.id.calltwo);
        ImageView imagecalls = findViewById (R.id.phonecall);
        ImageView emailsend = findViewById (R.id.emailsend);
        final ImageView currentlocation = findViewById (R.id.currentlocation);
        emailsend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String emailsend=phoneno.getText ().toString ();
                intent.putExtra (EXTRA_EMAILSEND,email);
                startActivity (new Intent (Driver_Description.this,Emailsend.class));
                finish ();
            }
        });
        currentlocation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Driver_Description.this, MapsActivity.class);
                intent.putExtra(EXTRA_LATITUDE, lat );
                intent.putExtra(EXTRA_LONGTUDE, lng);
                startActivity(intent);

            }
        });
        imagecalls.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall();
            }
            private void MakePhoneCall()  {
                if (ContextCompat.checkSelfPermission (Driver_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Driver_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + phone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+""+first+""+last,Toast.LENGTH_LONG).show ();
                }
            }
        });
        imagecalltwo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall2();
            }
            private void MakePhoneCall2() {
                if (ContextCompat.checkSelfPermission (Driver_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Driver_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + addphone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+first+" "+last,Toast.LENGTH_LONG).show ();
                }
            }
        });
        imagecall.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               MakePhoneCall ();
            }
            private void MakePhoneCall() {
                if (ContextCompat.checkSelfPermission (Driver_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Driver_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + phone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+first+" "+last,Toast.LENGTH_LONG).show ();
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


