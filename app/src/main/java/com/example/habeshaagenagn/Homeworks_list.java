package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habeshaagenagn.AdapterElectronics;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.habeshaagenagn.Driver_List.EXTRA_PLACE;
import static com.example.habeshaagenagn.R.string;

public class Homeworks_list extends AppCompatActivity implements AdapterHomework.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_COMPANYNAME="companyname";
    public static final String EXTRA_SERVICETYPE="servicetype";
    public static final String EXTRA_EMAIL="email";
    public static final String EXTRA_PHONE="Phone";
    public static final String EXTRA_ADDPHONE = "addphone";
    public static final String EXTRA_PLACE = "place";
    public static final String EXTRA_DES="Description";
    public static final String EXTRA_URL="url";
    public static final String EXTRA_LONG="longitude";
    public static final String EXTRA_LAT="latitude";
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mrecyclerView;
    private AdapterHomework madapter;
    private ArrayList<Homeworks> muser;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mrequestQueue;
    private static final int ReQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.homeworks_list);
        swipeRefreshLayout = findViewById (R.id.swiperefresh);
        context=getApplicationContext();
        mrecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(context));
        muser=new ArrayList<>();
        madapter = new AdapterHomework (muser);
        mrequestQueue= Volley.newRequestQueue(context);
        // mrecyclerView.setLayoutManager (mLayoutManager);
        mrecyclerView.setAdapter (madapter);
        parseJSON();
        swipeRefreshLayout.setOnRefreshListener (this);
        swipeRefreshLayout.setColorSchemeResources (R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

    }

    @Override
    public void onRefresh() {
        parseJSON ();
        muser.clear ();
        swipeRefreshLayout.setRefreshing (false);
    }
    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback (0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition ();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    Homeworks cilckeditem = muser.get (position);
                    madapter.notifyItemChanged (position);
                    if (ContextCompat.checkSelfPermission (Homeworks_list.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions (Homeworks_list.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                    } else {
                        String dial = "tel:" + cilckeditem.getPhone ();
                        startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                        Toasty.info (getApplicationContext (),"Call To"+" "+cilckeditem.getServicetype (),Toast.LENGTH_LONG).show ();
                    }
                    break;
                case ItemTouchHelper.RIGHT:
                    Homeworks cilckeditemw = muser.get (position);
                    madapter.notifyItemChanged (position);
                    if (ContextCompat.checkSelfPermission (Homeworks_list.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions (Homeworks_list.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                    } else {
                        Intent intent = new Intent(Homeworks_list.this, Emailsend.class);
                        intent.putExtra (Intent.EXTRA_EMAIL,cilckeditemw.getEmail ());
                        intent.putExtra (EXTRA_COMPANYNAME,cilckeditemw.getCompanyname ());
                        startActivity (intent);
                    }
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor (ContextCompat.getColor (Homeworks_list.this,R.color.callcolor))
                    .addSwipeRightBackgroundColor (ContextCompat.getColor (Homeworks_list.this,R.color.callcolor1))
                    .addSwipeRightActionIcon (R.drawable.ic_email_black_24dp)
                    .addSwipeLeftActionIcon (R.drawable.swipecall)
                    .addSwipeLeftLabel ("Call")
                    .setSwipeLeftLabelTextSize (0,50)
                    .setSwipeRightLabelTextSize (0,50)
                    .addSwipeRightLabel ("Message")
                    .create()
                    .decorate();
            super.onChildDraw (c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void parseJSON(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Constants.HOMEWORKS_SERVICEPROVIDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONArray users=new JSONArray(response);
                            for(int i=0;i<users.length();i++){
                                JSONObject orderobject=users.getJSONObject(i);
                                String companyname=orderobject.getString ("companyname");
                                String servicetype=orderobject.getString ("servicetypes");
                                String email=orderobject.getString ("email");
                                String phone=orderobject.getString ("phone");
                                String addphone=orderobject.getString ("addphone");
                                String place = orderobject.getString ("place");
                                String description=orderobject.getString ("description");
                                String url = orderobject.getString ("url");
                                String elon = orderobject.getString ("longitude");
                                String elat = orderobject.getString ("latitude");
                                Homeworks uo=new Homeworks (companyname,servicetype,email,phone,addphone,description,url,elon,elat,place);
                                muser.add(uo);
                            }
                            madapter= new AdapterHomework (context,muser);
                            mrecyclerView.setAdapter(madapter);
                            madapter.setOnItemClickListener (Homeworks_list.this);
//                            madapter.setOnItemClickListener (new AdapterElectronics.OnItemClickListener () {
//                                @Override
//                                public void onItemClick(int position) {
//                                    Intent intent = new Intent(Electronics_List.this, Electronics_Descriptio.class);
//                                    intent.putExtra("firstname", muser.get(position));
//                                    startActivity(intent);
//                                }
//
//                                @Override
//                                public void onDeleteClick(int position) {
//
//                                }
//                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper (simpleCallback);
        itemTouchHelper.attachToRecyclerView (mrecyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                madapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Homeworks_list.this, Homeworks_Description.class);
        Homeworks cilckeditem=muser.get (position);
        intent.putExtra (EXTRA_COMPANYNAME,cilckeditem.getCompanyname ());
        intent.putExtra (EXTRA_SERVICETYPE,cilckeditem.getServicetype ());
        intent.putExtra (EXTRA_EMAIL,cilckeditem.getPhone ());
        intent.putExtra (EXTRA_PHONE,cilckeditem.getEmail ());
        intent.putExtra (EXTRA_ADDPHONE,cilckeditem.getAddphone ());
        intent.putExtra (EXTRA_PLACE, cilckeditem.getPlace ());
        intent.putExtra (EXTRA_DES,cilckeditem.getDescription ());
        intent.putExtra (EXTRA_URL, cilckeditem.getUrl ());
        intent.putExtra (EXTRA_LONG, cilckeditem.getLongtude ());
        intent.putExtra (EXTRA_LAT, cilckeditem.getLatitude ());

        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {

    }

}
