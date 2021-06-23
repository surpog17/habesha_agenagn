package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class ServiceSignup extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername,editTextname, editTextEmail, editTextPassword;
    private Button buttonRegister,buttonlogin;
    private ProgressDialog progressDialog;

    private ImageView imageView,btnimagechoose;
    private EditText editText;
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    private static final Pattern PASSWORD = Pattern.compile (
            "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=\\S+$)"+
                    ".{6,}");
    private static final Pattern NAME = Pattern.compile (
            "(?=.*[A-Z])" +
                    "(?=.*[a-z])" +
                    "(?=\\S+$)");

    private TextView textViewLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_service_signup);
        editTextEmail = (EditText) findViewById(R.id.editemail);
        editTextUsername = (EditText) findViewById(R.id.edituser);
        editTextPassword = (EditText) findViewById(R.id.editpass);
        editText=(EditText) findViewById (R.id.editTextName);
        editTextname = (EditText) findViewById(R.id.editname);

        buttonlogin = (Button) findViewById(R.id.login);


        buttonRegister = (Button) findViewById(R.id.signup);

        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (v == buttonRegister) {
                    String name = editTextname.getText ().toString ().trim ();
                    if (name.isEmpty ()) {
                        editTextname.setError ("field can't be empty");

                    } else if (NAME.matcher (name).matches ()) {
                        editTextname.setError ("please enter your name without numbers and characters");

                    } else {
                        editTextname.setError (null);
                    }
                    String emailinput = editTextEmail.getText ().toString ().trim ();
                    if (emailinput.isEmpty ()) {
                        editTextEmail.setError ("field can't be empty");

                    } else if (!Patterns.EMAIL_ADDRESS.matcher (emailinput).matches ()) {
                        editTextEmail.setError ("please enter validate email address");

                    } else {
                        editTextEmail.setError (null);
                    }
                    String pass = editTextPassword.getText ().toString ().trim ();
                    if (pass.isEmpty ()) {
                        editTextPassword.setError ("field can't be empty");

                    } else if (!PASSWORD.matcher (pass).matches ()) {
                        editTextPassword.setError ("password to weak");
                    } else {
                        editTextPassword.setError (null);
                    }
                    String username = editTextUsername.getText ().toString ().trim ();
                    if (username.isEmpty ()) {
                        editTextUsername.setError ("field can't be empty");

                    } else if (username.length () > 15) {
                        editTextUsername.setError ("username to long");

                    } else {
                        editTextPassword.setError (null);
                        registerService ();
                        startActivity (new Intent (ServiceSignup.this,ServiceLoginActivity.class));
                        finish ();

                    }
                }

            }

        });
        buttonlogin.setOnClickListener(this);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerService() {
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextname.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering Service Provider...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.SERVICE_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toasty.success (getApplicationContext (), jsonObject.getString ("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put("name", name);
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }




    @Override
    public void onClick(View v) {
        if (v == buttonlogin) {
            startActivity(new Intent (this, ServiceLoginActivity.class));
            finish ();

        }

    }

    public void Radiobutton(View view) {
        startActivity(new Intent (this, Signup.class));
        finish ();
    }
}
