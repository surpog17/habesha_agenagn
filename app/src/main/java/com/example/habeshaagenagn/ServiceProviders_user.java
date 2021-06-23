package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServiceProviders_user extends AppCompatActivity {
    CardView electronics,driver,homeworks,shopping_centers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.service_providers_user);
        electronics=findViewById (R.id.electronics);
        driver=findViewById (R.id.driveruser);
        homeworks=findViewById (R.id.homeworks);
        shopping_centers=findViewById (R.id.shopping_centers);
        shopping_centers.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServiceProviders_user.this,Shoppingcenter_list.class));
            }
        });
        homeworks.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServiceProviders_user.this,Homeworks_list.class));
            }
        });
        driver.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServiceProviders_user.this,Driver_List.class));
            }
        });
        electronics.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ServiceProviders_user.this,Electronics_List.class));

            }
        });
    }
}
