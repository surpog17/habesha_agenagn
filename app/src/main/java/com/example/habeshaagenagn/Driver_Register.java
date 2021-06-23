package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class Driver_Register extends AppCompatActivity implements LocationListener {
    Spinner dwork;

    private TextInputLayout firstname,lastname,editText, work, email, phone, addphone,description,textView_location;
    private ProgressBar loading;
    private TextView longitu,latitu;
    Dialog mydialog;
    private Button buttonUpload;
    private ImageView imageView;
    String encodeImageString;
    String lat;
    String lng;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    //EditText textView_location;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.driver_register);
        dwork = (Spinner) findViewById (R.id.dwork);
        loading = findViewById (R.id.loading);
        firstname = findViewById (R.id.firstnamediver);
        lastname = findViewById (R.id.lastnamedirver);
        dwork = findViewById (R.id.dwork);
        description = findViewById (R.id.descriptiondirver);
        email = findViewById (R.id.emaildirver);
        phone = findViewById (R.id.phonedirver);
        addphone = findViewById (R.id.addphonedirver);
        buttonUpload = findViewById (R.id.buttonUpload);
        textView_location=findViewById (R.id.text_location);
        longitu=findViewById (R.id.longi);
        latitu=findViewById (R.id.lati);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Driver_Register.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener () {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        textView_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
                Toasty.info (getApplicationContext (),"Please wait until your current location displayed",Toast.LENGTH_LONG).show ();
            }
        });
        String[] dirversworks = new String[]{"personal driver", "school driver ", "taxi Driver",};
        ArrayAdapter<String> works = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, dirversworks);
        dwork.setAdapter (works);
        buttonUpload.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
     validation ();


            }
        });

    }
private boolean validation(){
    String firstn = firstname.getEditText ().getText ().toString ();
    String emailinput = email.getEditText ().getText ().toString ();
    email.getEditText().setText (SharedPrefMana.getInstance (this).getUserEmail ());
    String last = lastname.getEditText ().getText ().toString ();
    String pho=phone.getEditText ().getText ().toString ();
    String des = description.getEditText ().getText ().toString ();
    String loca=textView_location.getEditText ().getText ().toString ();
    if (firstn.isEmpty ()){
        firstname.setError ("Please enter your first name");
    }
    if (last.isEmpty ()){
        lastname.setError ("Please enter Your lastname");
    }if (pho.isEmpty ()){
        phone.setError ("Please enter your phone number");
    }
    if (des.isEmpty ()){
        description.setError ("Please enter Your description");
    }if (loca.isEmpty ()){
        textView_location.setError ("Please enter your location");
    }

    if (emailinput.isEmpty ()) {
        email.setError ("Please enter Your email");
    } else if (!Patterns.EMAIL_ADDRESS.matcher (emailinput).matches ()) {
        email.setError ("please enter  a valid email address");
    } else {
        uploadMultipart ();
    }
    return false;
}

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Driver_Register.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder (Driver_Register.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.getEditText ().setText (address);
            longitu.setText (""+location.getLongitude ());
            latitu.setText (""+location.getLatitude ());
        }catch (Exception e){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    public void uploadMultipart() {
        //getting name for the image
        final String firstname=this.firstname.getEditText ().getText ().toString().trim();
        final String lastname=this.lastname.getEditText ().getText ().toString().trim();
        final String work=this.dwork.getSelectedItem ().toString ();
        final String email=this.email.getEditText ().getText ().toString().trim();
        final  String phone=this.phone.getEditText ().getText ().toString().trim();
        final  String addphone=this.addphone.getEditText ().getText ().toString().trim();
        final String place=this.textView_location.getEditText ().getText ().toString().trim();
        final String description=this.description.getEditText ().getText ().toString().trim();
        final String lat=this.latitu.getText ().toString().trim();
        final String lng=this.longitu.getText ().toString().trim();
        //Uploading code
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, Constants.DRIVER_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject= new JSONObject (response);
                    boolean  success=jsonObject.getBoolean ("success");
                    if (success){
                        loading.dismiss();
                        Toasty.success (Driver_Register.this, "Register Successfully", Toast.LENGTH_SHORT).show ();
                        startActivity (new Intent (Driver_Register.this,ServiceProfile.class));
                    }else {
                        loading.dismiss();
                                Toasty.info (Driver_Register.this, jsonObject.getString ("message"), Toast.LENGTH_SHORT).show ();
                         }

                } catch (JSONException e) {
                    e.printStackTrace ();
                    loading.dismiss();
                    Toasty.error (Driver_Register.this, "Register error" + e.toString (), Toast.LENGTH_SHORT).show ();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toasty.warning (Driver_Register.this, "Register error" + error.toString (), Toast.LENGTH_LONG).show ();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put ("firstname", firstname);
                map.put ("lastname", lastname);
                map.put ("work", work);
                map.put ("email", email);
                map.put ("phone", phone);
                map.put ("addphone", addphone);
                map.put ("place", place);
                map.put ("Description", description);
                map.put("upload",encodeImageString);
                map.put("longtude",lng);
                map.put("latitude",lat);
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}