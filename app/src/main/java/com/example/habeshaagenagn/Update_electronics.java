package com.example.habeshaagenagn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_electronics extends AppCompatActivity {

    AdapterElectronics adapter;
    public static ArrayList<Electronics> electronicsArrayList = new ArrayList<> ();
    EditText edveri,edfName,edlname,edwork,edexp,edphone,edEmail,edDescription;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_electronics);
        edfName = findViewById(R.id.ed_name);
        edlname = findViewById(R.id.ed_lname);
        edwork = findViewById(R.id.ed_work);
        edexp = findViewById(R.id.ed_exp);
        edphone = findViewById(R.id.ed_phone);
        edEmail = findViewById(R.id.ed_email);
        edDescription = findViewById(R.id.ed_description);
        Intent intent = getIntent();
        String fname = intent.getStringExtra(Detail_View.EXTRA_FIRSTNAME);
        String lname = intent.getStringExtra(Detail_View.EXTRA_LASTAME);
        String veri = intent.getStringExtra(Detail_View.EXTRA_WORK);
        String exper = intent.getStringExtra(Detail_View.EXTRA_EXPER);
        String phone = intent.getStringExtra(Detail_View.EXTRA_PHONE);
        String des = intent.getStringExtra(Detail_View.EXTRA_Description);
        String emailupdate = intent.getStringExtra(Detail_View.EXTRA_EMAIL);
        edfName.setText(fname);
        edlname.setText(lname);
        edwork.setText(veri);
        edexp.setText(exper);
        edDescription.setText(des);
        edEmail.setText(emailupdate);
        edphone.setText(phone);
//        Intent intent = getIntent();
//        position = intent.getExtras().getInt("position");
//
//
//       // edId.setText(Update_electronics.electronicsArrayList.get(position).getId());
//        edfName.setText(Update_electronics.electronicsArrayList.get(position).getFname ());
//        edlname.setText(Update_electronics.electronicsArrayList.get(position).getLname ());
//        edwork.setText(Update_electronics.electronicsArrayList.get(position).getWork ());
//        edexp.setText(Update_electronics.electronicsArrayList.get(position).getExperience ());
//        edEmail.setText(Update_electronics.electronicsArrayList.get(position).getEmail());
//        edphone.setText(Update_electronics.electronicsArrayList.get(position).getPhone ());
//        edDescription.setText(Update_electronics.electronicsArrayList.get(position).getDescription ());




    }

    public void btn_updateData(View view) {

        final String firstname = edfName.getText().toString();
        final String lastname = edlname.getText().toString();
        final String work = edwork.getText().toString();
        final String experience = edexp.getText().toString();
        final String email = edEmail.getText().toString();
        final String phone = edphone.getText().toString();
        final String description = edDescription.getText().toString();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.UPDATE_ELECTRONICS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Update_electronics.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ServiceProfile.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Update_electronics.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String> ();
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("work",work);
                params.put("experience",experience);
                params.put("email",email);
                params.put("phone",phone);
                params.put("Description",description);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Update_electronics.this);
        requestQueue.add(request);





    }
}
