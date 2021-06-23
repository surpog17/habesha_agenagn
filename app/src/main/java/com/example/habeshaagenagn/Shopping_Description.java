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

import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_IMAGE;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_MALLNAME;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_ADDRESS;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_PHONE;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_LATI;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_LONGI;
import static com.example.habeshaagenagn.Shoppingcenter_list.EXTRA_WORKINGHOUR;

public class Shopping_Description extends AppCompatActivity {
    private static final int ReQUEST_CALL = 1;
    public static final String EXTRA_LATITUDE= "com.example.EXTRA_LATITUDE";
    public static final String EXTRA_LONGTUDE = "com.example.EXTRA_LONGTUDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.shopping_description);
        final Intent intent = getIntent ();
        final String mallname=intent.getStringExtra (EXTRA_MALLNAME);
        final String address=intent.getStringExtra (EXTRA_ADDRESS);
        String workhour=intent.getStringExtra (EXTRA_WORKINGHOUR);
        final String phone=intent.getStringExtra (EXTRA_PHONE);
        String image=intent.getStringExtra (EXTRA_IMAGE);
        final String lng=intent.getStringExtra (EXTRA_LONGI);
        final String lat=intent.getStringExtra (EXTRA_LATI);
        TextView mall = findViewById (R.id.mall_name);
        mall.setText (mallname);
        TextView addresss = findViewById (R.id.address);
        addresss.setText (address);
        TextView worktime = findViewById (R.id.worktime);
        worktime.setText (phone);
        final TextView phoneno = findViewById (R.id.phone_number);
        phoneno.setText (workhour);
        TextView longi = findViewById (R.id.lngi);
        longi.setText (lng);
        TextView lati = findViewById (R.id.lati);
        lati.setText (lat);
        ImageView imagedes=findViewById (R.id.shopping_image);
        Picasso.with(this).load(image).fit().centerCrop ().into(imagedes);
        ImageView imagecall = findViewById (R.id.callone);
        ImageView imagecalls = findViewById (R.id.phonecall);
        final ImageView currentlocation = findViewById (R.id.currentlocation);
        currentlocation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping_Description.this, MapsActivity.class);
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
                if (ContextCompat.checkSelfPermission (Shopping_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Shopping_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + phone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+""+mallname,Toast.LENGTH_LONG).show ();
                }
            }
        });

        imagecall.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                MakePhoneCall ();
            }
            private void MakePhoneCall() {
                if (ContextCompat.checkSelfPermission (Shopping_Description.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Shopping_Description.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                } else {
                    String dial = "tel:" + phone;
                    startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                    Toasty.info (getApplicationContext (),"Call To"+" "+mallname,Toast.LENGTH_LONG).show ();
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


