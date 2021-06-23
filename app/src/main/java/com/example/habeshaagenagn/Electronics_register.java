package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLng;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class Electronics_register extends AppCompatActivity implements LocationListener,View.OnClickListener {
    Spinner Ework;
    private TextInputLayout firstname, lastname, work, experience, email, phone,addphone, description,place,editText,textView_location;
    private EditText Displayseat;
    private TextView longitu,latitu;
    private Button eregister;
    private ProgressBar loading;
    String encodeImageString;
    Dialog mydialog;
    ImageView imageView;
    public static String ver;
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.electronics_register);
        Ework = (Spinner) findViewById (R.id.ework);
        loading = findViewById (R.id.loading);
        firstname = findViewById (R.id.firstname_ele);
        lastname = findViewById (R.id.lastname_ele);
        experience = findViewById (R.id.experience_ele);
        Ework = findViewById (R.id.ework);
        description = findViewById (R.id.description_ele);
        email = findViewById (R.id.email_ele);
        phone = findViewById (R.id.phone_ele);
        addphone = findViewById (R.id.addphon_ele);
        eregister = findViewById (R.id.buttonUpload);
        textView_location=findViewById (R.id.text_location);
        longitu=findViewById (R.id.longi);
        latitu=findViewById (R.id.lati);
        imageView = (ImageView) findViewById(R.id.imageView);
        String[] electronicsworks = new String[]{"mobile Maintainer ", "Pc Maintainer ", "Dish Maintainer",};
        ArrayAdapter<String> works = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, electronicsworks);
        Ework.setAdapter (works);
        textView_location.setOnClickListener (this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Electronics_register.this)
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
        eregister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
            validation ();
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Electronics_register.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private boolean validation(){
        String firstn = firstname.getEditText ().getText ().toString ();
        String emailinput = email.getEditText ().getText ().toString ();
        email.getEditText().setText (SharedPrefMana.getInstance (this).getUserEmail ());
        String last = lastname.getEditText ().getText ().toString ();
        String compan = experience.getEditText ().getText ().toString ();
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
        }if (des.isEmpty ()){
            description.setError ("Please enter Your description");
        }if (loca.isEmpty ()){
            textView_location.setError ("Please enter your location");
        }
        if (compan.isEmpty ()){
            experience.setError ("Please enter your company");
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

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder (Electronics_register.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.getEditText ().setText(address);
            latitu.setText (""+ location.getLatitude ());
            longitu.setText ("" +location.getLongitude ());


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

//
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
        final String work=this.Ework.getSelectedItem ().toString ();
        final String experience=this.experience.getEditText ().getText ().toString().trim();
        final String email=this.email.getEditText ().getText ().toString().trim();
        final  String phone=this.phone.getEditText ().getText ().toString().trim();
        final  String addphone=this.addphone.getEditText ().getText ().toString().trim();
        final String place=this.textView_location.getEditText ().getText ().toString().trim();
        final String description=this.description.getEditText ().getText ().toString().trim();
        final String lo=this.longitu.getText ().toString ().trim ();
        final String la=this.latitu.getText ().toString ().trim ();
        //Uploading code
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, Constants.ELECTRONICS_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject= new JSONObject (response);
                    boolean  success=jsonObject.getBoolean ("success");
                    if (success){
                        loading.dismiss();
                        Toasty.success (Electronics_register.this, "Register Successfully", Toast.LENGTH_SHORT).show ();
                        startActivity (new Intent (Electronics_register.this,ServiceProfile.class));
                    }else {
                        loading.dismiss();
                        Toasty.info (Electronics_register.this, jsonObject.getString ("message"), Toast.LENGTH_SHORT).show ();
                    }

                } catch (JSONException e) {
                    e.printStackTrace ();
                    loading.dismiss();
                    Toasty.error (Electronics_register.this, "Register error" + e.toString (), Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText (Electronics_register.this, "Register error" + error.toString (), Toast.LENGTH_LONG).show ();
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
                map.put ("experience", experience);
                map.put ("email", email);
                map.put ("phone", phone);
                map.put ("addphone", addphone);
                map.put ("place", place);
                map.put ("Description", description);
                map.put("upload",encodeImageString);
                map.put("longtude",lo);
                map.put("latitude",la);
                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void dosamething(View view) {
        getLocation();
        Toasty.info (getApplicationContext (),"Please wait until your current location displayed",Toast.LENGTH_LONG).show ();

    }

    @Override
    public void onClick(View v) {
switch (v.getId ()){
        case R.id.text_location:
        getLocation();
        Toasty.info (getApplicationContext (),"Please wait until your current location displayed",Toast.LENGTH_LONG).show ();

}
    }
//    public void ShowDialog() {
//        AlertDialog.Builder builder= new AlertDialog.Builder (Driver_Register.this);
//        builder.setTitle ("Habesha Agenagn");
//        builder.setIcon (android.R.drawable.ic_menu_call);
//        builder.setMessage ("choose number to call");
//
//        builder.setPositiveButton ("ok", new DialogInterface.OnClickListener () {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText (getApplicationContext (),getString(thankyou),Toast.LENGTH_LONG).show ();
//                Intent intent = new Intent(Passenger_Details.this, Home_selam.class);
//                startActivity(intent);
//                finish ();
//
//            }
//        });
//        builder.setCancelable (false);
//        builder.show ();
//    }
}