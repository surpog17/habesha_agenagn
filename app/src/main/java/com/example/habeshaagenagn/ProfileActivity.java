package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import static java.security.AccessController.getContext;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  private TextView textViewUsername, textViewUserEmail,usernamenav,emailnav;
  ViewFlipper viewFlipper;
  CardView locationUser,serviceproviders,nearby,upload;
  private DrawerLayout drawerLayout;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Toolbar toolbar=findViewById (R.id.toolbar);
    setSupportActionBar (toolbar);
    drawerLayout=findViewById (R.id.drawer_layout);
    NavigationView navigationView=findViewById (R.id.nav_view);
    navigationView.setNavigationItemSelectedListener (this);
    ActionBarDrawerToggle toggle=new ActionBarDrawerToggle (this,drawerLayout,toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener (toggle);
    toggle.syncState ();
    if(!SharedPrefManager.getInstance(this).isLoggedIn()){
      finish();
      startActivity(new Intent(this, LoginActivity.class));
    }

    textViewUsername = (TextView) findViewById(R.id.textViewUsername);
    textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
    locationUser=findViewById (R.id.locationusers);
    serviceproviders=findViewById (R.id.serviceproviders);
    nearby=findViewById (R.id.nearby);
    upload=findViewById (R.id.upload);
    usernamenav=findViewById (R.id.usernamenav);
    emailnav=findViewById (R.id.emailnav);
    // emailnav.setText (SharedPrefManager.getInstance (this).getUserEmail ());
    //usernamenav.setText (SharedPrefManager.getInstance (this).getUsername ());


    textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
    textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    int images[] ={R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.service};
    viewFlipper=findViewById(R.id.flip);

//        for (int image: images){
//            flipperImages(image);
//        }
    upload.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ProfileActivity.this,UploadImage.class));
      }
    });
    nearby.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ProfileActivity.this,GoogleMapsActivity.class));
      }
    });
    locationUser.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ProfileActivity.this,Currentlocation.class));
      }
    });
    serviceproviders.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ProfileActivity.this,ServiceProviders_user.class));
      }
    });
  }


  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen (GravityCompat.START)){
      drawerLayout.closeDrawer (GravityCompat.START);
    }else {
      super.onBackPressed ();
    }
  }
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId ()){
      case R.id.catagory:
        Intent intenthome = new Intent (ProfileActivity.this, ServiceProviders_user.class);
        startActivity (intenthome);
        Toast.makeText (getApplicationContext (),"catagoryyyyyyyyy",Toast.LENGTH_LONG).show ();
        break;
      case R.id.current:
        startActivity (new Intent(this,Currentlocation.class));
        break;
//            case R.id.nearby:
//                startActivity (new Intent(this,ServiceProviders_user.class));
//                break;
    }
    drawerLayout.closeDrawer (GravityCompat.START);
    return true;
  }

//    private void flipperImages(int image) {
//        ImageView imageView = new ImageView (getApplicationContext ());
//        imageView.setBackgroundResource(image);
//
//        viewFlipper.addView(imageView);
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//
//        //animation
//        viewFlipper.setInAnimation(getApplicationContext (), android.R.anim.slide_in_left);
//        viewFlipper.setOutAnimation(getApplicationContext (), android.R.anim.slide_out_right);
//    }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()){
      case R.id.menuLogout:
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent (this, LoginActivity.class));
        break;
      case R.id.language:
        startActivity(new Intent (this, ProfileActivity.class));
        setApplocale ("am");
        break;

    }
    return true;
  }
  private void setApplocale(String localeCode) {
    Resources resources = getResources ();
    DisplayMetrics displayMetrics = resources.getDisplayMetrics ();
    Configuration configuration = resources.getConfiguration ();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      configuration.setLocale (new Locale (localeCode.toLowerCase ()));
    } else {
      configuration.locale = new Locale (localeCode.toLowerCase ());
    }
    resources.updateConfiguration (configuration, displayMetrics);
  }

}


