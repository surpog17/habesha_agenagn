package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ServiceLoginActivity extends AppCompatActivity implements View.OnClickListener {
  private EditText editTextUsername, editTextPassword;
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
    setContentView (R.layout.activity_service_login);

    if(SharedPrefMana.getInstance(this).isLoggedIn()){
      finish();
      startActivity(new Intent (this, ServiceProfile.class));
      return;
    }

    editTextUsername = (EditText) findViewById(R.id.editTextUsername);
    editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    buttonLogin = (Button) findViewById(R.id.buttonLogin);
    buttonSignup = (Button) findViewById(R.id.signup);

    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");

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
    final String password = editTextPassword.getText().toString().trim();

    progressDialog.show();


    StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            Constants.SERVICElogin,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                  JSONObject obj = new JSONObject(response);
                  if(!obj.getBoolean("error")){
                    SharedPrefMana.getInstance(getApplicationContext())
                            .userLogin(
                                    obj.getInt("sid"),
                                    obj.getString("username"),
                                    obj.getString("email")
                            );
                    startActivity(new Intent(getApplicationContext(), ServiceProfile.class));
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
      Intent intentcon = new Intent (ServiceLoginActivity.this, ServiceSignup.class);
      startActivity (intentcon);
    }
  }

  public void Radiobutton(View view) {
    RadioGroup radioGroup=findViewById (R.id.radiogroup);
    RadioButton radioButton=findViewById (radioGroup.getCheckedRadioButtonId ());
    radioButton.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        Intent intentcon = new Intent (ServiceLoginActivity.this, LoginActivity.class);
        startActivity (intentcon);
        finish ();
      }
    });

  }
}
