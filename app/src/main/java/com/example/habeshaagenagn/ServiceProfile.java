package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Locale;

public class ServiceProfile extends AppCompatActivity {
  private TextView textViewUsername, textViewUserEmail;
  ViewFlipper viewFlipper;
  CardView locationservice,register,update,nearby;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_service_profile);

    if (!SharedPrefMana.getInstance (this).isLoggedIn ()) {
      finish ();
      startActivity (new Intent (this, LoginActivity.class));
    }

    textViewUsername = (TextView) findViewById (R.id.textViewUsername);
    textViewUserEmail = (TextView) findViewById (R.id.textViewUseremail);
    locationservice=findViewById (R.id.locationservice);
    register=findViewById (R.id.register);
    update=findViewById (R.id.update);
    nearby=findViewById (R.id.nearbyservice);
    nearby.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (getApplicationContext (),GoogleMapsActivity.class));
      }
    });
    update.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (getApplicationContext (),HomeworksRegister.class));
      }
    });


    textViewUserEmail.setText (SharedPrefMana.getInstance (this).getUserEmail ());
    textViewUsername.setText (SharedPrefMana.getInstance (this).getUsername ());
    int images[] = {R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.service};
    viewFlipper = findViewById (R.id.flip);

    for (int image : images) {
      flipperImages (image);
    }
    register.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ServiceProfile.this,ServicePage.class));
      }
    });
    locationservice.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        startActivity (new Intent (ServiceProfile.this, Currentlocation.class));
      }
    });
  }


  private void flipperImages(int image) {
    ImageView imageView = new ImageView (getApplicationContext ());
    imageView.setBackgroundResource (image);

    viewFlipper.addView (imageView);
    viewFlipper.setFlipInterval (3000);
    viewFlipper.setAutoStart (true);

    //animation
    viewFlipper.setInAnimation (getApplicationContext (), android.R.anim.slide_in_left);
    viewFlipper.setOutAnimation (getApplicationContext (), android.R.anim.slide_out_right);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater ().inflate (R.menu.menu, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId ()) {
      case R.id.menuLogout:
        SharedPrefMana.getInstance (this).logout ();
        finish ();
        startActivity (new Intent (this, ServiceLoginActivity.class));
        break;
      case R.id.language:
        startActivity(new Intent (this, ServiceProfile.class));
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