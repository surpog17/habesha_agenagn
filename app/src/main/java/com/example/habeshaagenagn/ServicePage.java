package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServicePage extends AppCompatActivity {
    CardView electronics,driver,homework;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.servicepage);
        electronics=findViewById (R.id.electronics);
        driver=findViewById (R.id.driver);
        homework=findViewById (R.id.homeworks);
        homework.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServicePage.this,HomeworksRegister.class));
            }
        });
        driver.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServicePage.this,Driver_Register.class));
            }
        });
        electronics.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServicePage.this,Electronics_register.class));
            }
        });
    }
}
