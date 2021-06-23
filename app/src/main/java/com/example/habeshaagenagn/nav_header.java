package com.example.habeshaagenagn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

public class nav_header extends AppCompatActivity {
    private TextView textViewUsername, textViewUserEmail,usernamenav,emailnav;
    ViewFlipper viewFlipper;
    CardView locationUser,serviceproviders;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.nav_header);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        drawerLayout = findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener (toggle);
        toggle.syncState ();
        if (!SharedPrefManager.getInstance (this).isLoggedIn ()) {
            finish ();
            startActivity (new Intent (this, LoginActivity.class));
        }

        textViewUsername = (TextView) findViewById (R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById (R.id.textViewUseremail);
        locationUser = findViewById (R.id.locationusers);
        serviceproviders = findViewById (R.id.serviceproviders);
        usernamenav = findViewById (R.id.usernamenav);
        emailnav = findViewById (R.id.emailnav);
        emailnav.setText (SharedPrefManager.getInstance (this).getUserEmail ());
        usernamenav.setText (SharedPrefManager.getInstance (this).getUsername ());

    }
}