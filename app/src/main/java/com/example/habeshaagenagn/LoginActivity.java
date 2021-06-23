package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername, editTextPassword;
    //private SweetPassword sweetPassword;
    private Button buttonLogin,buttonSignup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent (this, ProfileActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
//        sweetPassword= findViewById (R.id.editTextPassword);
//        sweetPassword.setInterpolator (new BounceInterpolator ());
//        sweetPassword.setAnimDuration (300);

        buttonLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                if (v == buttonLogin) {
                    String name = editTextUsername.getText ().toString ().trim ();
                    if (name.isEmpty ()) {
                        editTextUsername.setError ("field can't be empty");

                    } else if (NAME.matcher (name).matches ()) {
                        editTextUsername.setError ("please enter your name without numbers and characters");

                    } else {
                        editTextUsername.setError (null);
                    }
                    String pass = editTextPassword.getText ().toString ().trim ();
                    if (pass.isEmpty ()) {
                        editTextPassword.setError ("field can't be empty");
                    } else {
                        editTextPassword.setError (null);
                        userLogin ();
                    }

                }
            }
        });
        buttonSignup.setOnClickListener (this);
    }

    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText ().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                finish();
                            }else{
                                Toasty.info(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toasty.error (
                                getApplicationContext (),
                                error.getMessage (),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if(v == buttonSignup){
            Intent intentcon = new Intent (LoginActivity.this, Signup.class);
            startActivity (intentcon);
        }
    }

    public void Radiobutton(View view) {
        RadioGroup radioGroup=findViewById (R.id.radiogroup);
        RadioButton radioButton=findViewById (radioGroup.getCheckedRadioButtonId ());
        radioButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intentcon = new Intent (LoginActivity.this, ServiceLoginActivity.class);
                startActivity (intentcon);
                finish ();
            }
        });

    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.language, menu);
//        setApplocale ("am-rET");
//        Intent intent2 = new Intent (LoginActivity.this, LoginActivity.class);
//        startActivity (intent2);
//        return true;
////    }
//    private void setApplocale(String localeCode) {
//        Resources resources = getResources ();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics ();
//        Configuration configuration = resources.getConfiguration ();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLocale (new Locale (localeCode.toLowerCase ()));
//        } else {
//            configuration.locale = new Locale (localeCode.toLowerCase ());
//        }
//        resources.updateConfiguration (configuration, displayMetrics);
//    }

}
