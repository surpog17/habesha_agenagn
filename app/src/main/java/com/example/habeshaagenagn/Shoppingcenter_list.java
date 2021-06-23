package com.example.habeshaagenagn;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.habeshaagenagn.Driver_List.EXTRA_PHONE;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LAT;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LONG;

public class Shoppingcenter_list extends AppCompatActivity implements AdapterShopping.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_MALLNAME = "mall_name";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_PHONE = "Phone_number";
    public static final String EXTRA_WORKINGHOUR = "working_hour";
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_LONGI = "longitude";
    public static final String EXTRA_LATI = "latitude";
    public static final String EXTRA_CALL = EXTRA_PHONE;
    Context context;
    private RecyclerView mrecyclerView;
    private AdapterShopping madapter;
    private ArrayList<Shopping_center> muser;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mrequestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int ReQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.shoppingcenter_list);
        swipeRefreshLayout = findViewById (R.id.swiperefresh);
        context = getApplicationContext ();
        mrecyclerView = (RecyclerView) findViewById (R.id.recyclerView);
        mrecyclerView.setHasFixedSize (true);
        mrecyclerView.setLayoutManager (new LinearLayoutManager (context));
        muser = new ArrayList<> ();
        madapter = new AdapterShopping (muser);
        mrequestQueue = Volley.newRequestQueue (context);
        // mrecyclerView.setLayoutManager (mLayoutManager);
        mrecyclerView.setAdapter (madapter);
        parseJSON ();
        swipeRefreshLayout.setOnRefreshListener (this);
        swipeRefreshLayout.setColorSchemeResources (R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        // Fetching data from server
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
                    Shopping_center cilckeditem = muser.get (position);
                    madapter.notifyItemChanged (position);
                    if (ContextCompat.checkSelfPermission (Shoppingcenter_list.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions (Shoppingcenter_list.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                    } else {
                        String dial = "tel:" + cilckeditem.getWorking_hour ();
                        startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                        Toasty.info (getApplicationContext (),"Call To"+" "+cilckeditem.getMall_name (),Toast.LENGTH_LONG).show ();
                    }
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor (ContextCompat.getColor (Shoppingcenter_list.this,R.color.callcolor))
                    .addSwipeLeftActionIcon (R.drawable.swipecall)
                    .addSwipeLeftLabel ("Call")
                    .setSwipeLeftLabelTextSize (0,50)
                    .create()
                    .decorate();
            super.onChildDraw (c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void parseJSON() {
        StringRequest stringRequest = new StringRequest (Request.Method.POST, Constants.SHOPPINGCENTERS_SERVICE,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONArray users = new JSONArray (response);
                            for (int i = 0; i < users.length (); i++) {
                                JSONObject orderobject = users.getJSONObject (i);
                                String mall_name = orderobject.getString ("mall_name");
                                String address = orderobject.getString ("address");
                                String working_hour= orderobject.getString ("working_hour");
                                String phone = orderobject.getString ("phone_number");
                                String image = orderobject.getString ("images");
                                String longitude = orderobject.getString ("longitude");
                                String latitude = orderobject.getString ("latitude");
                                Log.println (Log.INFO,"99999999999999999999",mall_name);
                                Shopping_center uo = new Shopping_center (mall_name, address, working_hour, phone, image,longitude,latitude);
                                muser.add (uo);
                            }
                            madapter = new AdapterShopping (context, muser);
                            mrecyclerView.setAdapter (madapter);
                            madapter.setOnItemClickListener (Shoppingcenter_list.this);

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
                            e.printStackTrace ();
                        }

                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ();
            }
        });

        //RequestHandler.getInstance (this).addToRequestQueue (stringRequest);
        RequestHandler.getInstance (this).addToRequestQueue (stringRequest);
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper (simpleCallback);
        itemTouchHelper.attachToRecyclerView (mrecyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem (R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView ();

        searchView.setImeOptions (EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                madapter.getFilter ().filter (newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent (Shoppingcenter_list.this, Shopping_Description.class);
        Shopping_center cilckeditem = muser.get (position);
        intent.putExtra (EXTRA_MALLNAME, cilckeditem.getMall_name ());
        intent.putExtra (EXTRA_ADDRESS, cilckeditem.getAddress ());
        intent.putExtra (EXTRA_WORKINGHOUR, cilckeditem.getWorking_hour ());
        intent.putExtra (EXTRA_PHONE, cilckeditem.getPhone_number ());
        intent.putExtra (EXTRA_IMAGE, cilckeditem.getImage ());
        intent.putExtra (EXTRA_LONGI, cilckeditem.getLongitude ());
        intent.putExtra (EXTRA_LATI, cilckeditem.getLatitude ());
        startActivity (intent);
    }

    @Override
    public void onDeleteClick(int position) {

    }
    private void MakePhoneCall() {

    }
    private void MakePhoneCall2() {
        if (ContextCompat.checkSelfPermission (Shoppingcenter_list.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (Shoppingcenter_list.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
        } else {
            String dial = "tel:" + EXTRA_CALL;
            startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
            Toasty.info (getApplicationContext (),"Call To"+"   "+EXTRA_MALLNAME,Toast.LENGTH_LONG).show ();
        }
    }
}