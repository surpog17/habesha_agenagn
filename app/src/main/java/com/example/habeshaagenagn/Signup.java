package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername,editTextname, editTextEmail, editTextPassword;
    private Button buttonRegister,buttonlogin;
    private ProgressDialog progressDialog;
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
        setContentView (R.layout.activity_signup);
        editTextEmail = (EditText) findViewById(R.id.editemail);
        editTextUsername = (EditText) findViewById(R.id.edituser);
        editTextPassword = (EditText) findViewById(R.id.editpass);
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
                        registerUser ();
                        startActivity (new Intent (Signup.this,LoginActivity.class));
                        finish ();

                    }
                }

            }

        });
        buttonlogin.setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextname.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.SIGNUP,
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
                        Toasty.error (getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
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
            startActivity(new Intent (this, LoginActivity.class));
            finish ();
        }
    }

    public void Radiobutton(View view) {
        RadioGroup radioGroup=findViewById (R.id.radiogroup);
        RadioButton radioButton=findViewById (radioGroup.getCheckedRadioButtonId ());
        radioButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intentcon = new Intent (Signup.this, ServiceSignup.class);
                startActivity (intentcon);
                finish ();
            }
        });

    }
}

