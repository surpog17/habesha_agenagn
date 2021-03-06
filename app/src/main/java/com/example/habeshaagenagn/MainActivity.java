package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topanim, bottomanim;
    ImageView imageView;
    TextView logo;
    private static int Splash_screen = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_main);

        topanim = AnimationUtils.loadAnimation (this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation (this, R.anim.bottom_animation);
        imageView = findViewById (R.id.imageView3);
        logo = findViewById (R.id.textView4);
        imageView.setAnimation (topanim);
        logo.setAnimation (bottomanim);
        if (SharedPrefMana.getInstance (this).isLoggedIn ()) {


            new Handler ().postDelayed (new Runnable () {
                @Override
                public void run() {
                    Intent intent = new Intent (MainActivity.this, ProfileActivity.class);
                    startActivity (intent);
                    finish ();
                }
            }, Splash_screen);

        }
        else {
            new Handler ().postDelayed (new Runnable () {
                @Override
                public void run() {
                    Intent intent = new Intent (MainActivity.this, ProfileActivity.class);
                    startActivity (intent);
                    finish ();
                }
            }, Splash_screen);
        }
    }
}


