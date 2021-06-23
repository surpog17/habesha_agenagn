package com.example.habeshaagenagn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail_View extends AppCompatActivity {
    TextView editTextfname, editTextlname, experience,phoneview,email,description,verification;
    String s;
    ImageView btn_deleteuser,update_data;
    Dialog myd;
    public static final String EXTRA_FIRSTNAME = " com.example.EXTRA_FIRSTNAME";
    public static final String EXTRA_LASTAME = " com.example.EXTRA_LASTAME";
    public static final String EXTRA_WORK = " com.example.EXTRA_WORK";
    public static final String EXTRA_EXPER= " com.example.EXTRA_EXPER";
    public static final String EXTRA_PHONE = " com.example.EXTRA_PHONE";
    public static final String EXTRA_EMAIL = " com.example.EXTRA_EMAIL";
    public static final String EXTRA_Description = " com.example.EXTRA_Description";
    public static final String EXTRA_CHECKEMAIL = " com.example.EXTRA_CHECKEMAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.detail_view);
        s = getIntent ().getStringExtra ("email");
        editTextfname = (TextView) findViewById (R.id.tv_fname);
        editTextlname = (TextView) findViewById (R.id.tv_lname);
        experience=findViewById (R.id.experience);
        phoneview = findViewById (R.id.phone);
        email = findViewById (R.id.email);
        description = findViewById (R.id.description);
        verification=findViewById (R.id.verification);
        update_data=findViewById (R.id.update_user);
        update_data.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                OpenUpDate();
                // startActivity (new Intent (getApplicationContext (),Update_electronics.class));
            }
        });
        btn_deleteuser = findViewById (R.id.delete_user);
        btn_deleteuser.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ShowDialog ();
            }
        });

        setdatas ();
    }
    public void OpenUpDate(){
        editTextfname = (TextView) findViewById(R.id.tv_fname);
        String fname = editTextfname.getText().toString();
        editTextlname = (TextView) findViewById(R.id.tv_lname);
        String lname = editTextlname.getText().toString();
        verification = (TextView) findViewById(R.id.verification);
        String veri = verification.getText().toString();
        experience = (TextView) findViewById(R.id.experience);
        String exper = experience.getText().toString();
        phoneview = (TextView) findViewById(R.id.phone);
        String phoneu = phoneview.getText().toString();
        description = (TextView) findViewById(R.id.description);
        String desc = description.getText().toString();
        email = (TextView) findViewById(R.id.email);
        String emailu = email.getText().toString();
        Intent intent = new Intent(this, Update_electronics.class);
        intent.putExtra(EXTRA_FIRSTNAME, fname);
        intent.putExtra(EXTRA_LASTAME, lname);
        intent.putExtra(EXTRA_WORK, veri);
        intent.putExtra(EXTRA_EXPER, exper);
        intent.putExtra(EXTRA_PHONE, phoneu);
        intent.putExtra(EXTRA_Description, desc);
        intent.putExtra(EXTRA_EMAIL, emailu);
        intent.putExtra (EXTRA_CHECKEMAIL,s);
        startActivity(intent);
    }

    private void setdatas() {

        final StringRequest jsonObjectRequest = new StringRequest (Request.Method.POST, Constants.DETAIL_VIEW,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray orders = new JSONArray (response);
                            JSONObject orderobject = orders.getJSONObject (0);
                            String fname = orderobject.getString ("firstname");
                            String lname = orderobject.getString ("lastname");
                            String ver = orderobject.getString ("work");
                            String exp=orderobject.getString ("experience");
                            String phone = orderobject.getString ("phone");
                            String emai = orderobject.getString ("email");
                            String des = orderobject.getString ("Description");

                            Log.println (Log.INFO, "999999999999999999", fname);
                            Log.println (Log.INFO, "999999999999999999", lname);
                            Log.println (Log.INFO, "999999999999999999", phone);
                            editTextfname.setText (fname);
                            editTextlname.setText (lname);
                            phoneview.setText (phone);
                            email.setText (emai);
                            description.setText (des);
                            verification.setText(ver);
                            experience.setText (exp);

                        } catch(JSONException e){
                            e.printStackTrace ();
                        }
                    }

                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                int x = 1;
                params.put ("email",s);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue (this);
        requestQueue.add (jsonObjectRequest);
    }

    //    public void ShowDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder (getApplicationContext ());
//        builder.setTitle ("Habehsa Agenagn");
//        builder.setIcon (R.drawable.warning);
//
//        builder.setMessage("are you sure"+s);
//
//        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss ();
//            }
//        });
//        builder.setPositiveButton ("Ok", new DialogInterface.OnClickListener () {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Cancelticket ();
//                Intent intent = new Intent (getApplicationContext (), ServiceProfile.class);
//                startActivity (intent);
//                finish ();
//
//            }
//        });
//        builder.setCancelable (false);
//        builder.show ();
//    }
    public void ShowDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder (Detail_View.this);
        builder.setIcon (R.drawable.warning);
        builder.setTitle ("Habesha Agenagn");
        builder.setMessage ("Are you sure to Delete!"+s);
        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss ();
            }
        });
        builder.setPositiveButton ("Ok", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cancelticket ();
                Intent intent = new Intent (getApplicationContext (), ServiceProfile.class);
                startActivity (intent);
                finish ();
            }
        });
        builder.setCancelable (false);
        builder.show ();
    }
    private void Cancelticket() {

        final StringRequest jsonObjectRequest  = new StringRequest(Request.Method.POST, Constants.DELETE_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


//                            JSONArray orders = new JSONArray (response);
                            JSONObject orderobject = new JSONObject (response);
                            boolean success = orderobject.getBoolean ("success");
                            if(success){

                                Toast.makeText(getApplicationContext (), "deleted", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext (), "nudeleted"+orderobject.getString ("message"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext (), "Error here: "+e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })



        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",s );

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest );

    }


}