package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Detail_Serviceprovider extends AppCompatActivity {

  private Button find;
  private EditText email;
  public static String checkphone;
  Context context;
  View parent_view;
  //TextView editTextfname, editTextlname, editTextmname, editTextemail;
  //String text, PhoneNo, date, state, lname;
  public static final String EXTRA_CHECKEMAIL = " com.example.EXTRA_CHECKEMAIL";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.detail_serviceprovider);
    find = findViewById (R.id.Checkinv);
    email = findViewById (R.id.checkemail);
    Intent intent = getIntent();
    String emailupdate = intent.getStringExtra(Detail_View.EXTRA_CHECKEMAIL);
    email.setText (emailupdate);
    find.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        Intent intentcon = new Intent (getApplicationContext (), Detail_View.class);
        intentcon.putExtra ("email", email.getText ().toString ());
        startActivity (intentcon);
      }

    });
  }
}