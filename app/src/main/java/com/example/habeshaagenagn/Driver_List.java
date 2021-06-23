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

import static com.example.habeshaagenagn.Electronics_List.EXTRA_LAT;
import static com.example.habeshaagenagn.Electronics_List.EXTRA_LONG;

public class Driver_List extends AppCompatActivity implements AdapterDriver.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_FNAME = "firstname";
    public static final String EXTRA_LASTNAME = "lastname";
    public static final String EXTRA_WORK = "work";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PHONE = "Phone";
    public static final String EXTRA_ADDPHONE = "addphone";
    public static final String EXTRA_PLACE = "place";
    public static final String EXTRA_DES = "Description";
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_LONG = "longtude";
    public static final String EXTRA_LATI = "latitude";
    public static final String EXTRA_CALL = EXTRA_PHONE;
    Context context;
    private RecyclerView mrecyclerView;
    private AdapterDriver madapter;
    private ArrayList<Drivers> muser;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mrequestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int ReQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.driver_list);
        swipeRefreshLayout = findViewById (R.id.swiperefresh);
        context = getApplicationContext ();
        mrecyclerView = (RecyclerView) findViewById (R.id.recyclerView);
        mrecyclerView.setHasFixedSize (true);
        mrecyclerView.setLayoutManager (new LinearLayoutManager (context));
        muser = new ArrayList<> ();
        madapter = new AdapterDriver (muser);
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
                    Drivers cilckeditem = muser.get (position);
                    madapter.notifyItemChanged (position);
                    if (ContextCompat.checkSelfPermission (Driver_List.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions (Driver_List.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                    } else {
                        String dial = "tel:" + cilckeditem.getPhone ();
                        startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
                        Toasty.info (getApplicationContext (),"Call To"+" "+cilckeditem.getFname ()+" "+cilckeditem.getLname (),Toast.LENGTH_LONG).show ();
                    }
                    break;
                case ItemTouchHelper.RIGHT:
                    Drivers cilckeditemw = muser.get (position);
                    madapter.notifyItemChanged (position);
                    if (ContextCompat.checkSelfPermission (Driver_List.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions (Driver_List.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
                    } else {
                        Intent intent = new Intent(Driver_List.this, Emailsend.class);
                        intent.putExtra (Intent.EXTRA_EMAIL,cilckeditemw.getEmail ());
                        intent.putExtra (EXTRA_FNAME,cilckeditemw.getFname ());
                        startActivity (intent);
                    }
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor (ContextCompat.getColor (Driver_List.this,R.color.callcolor))
                    .addSwipeRightBackgroundColor (ContextCompat.getColor (Driver_List.this,R.color.callcolor1))
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

    private void parseJSON() {
        StringRequest stringRequest = new StringRequest (Request.Method.POST, Constants.DRIVER_serviceProvider,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONArray users = new JSONArray (response);
                            for (int i = 0; i < users.length (); i++) {
                                JSONObject orderobject = users.getJSONObject (i);
                                String firstname = orderobject.getString ("firstname");
                                String last = orderobject.getString ("lastname");
                                String work = orderobject.getString ("work");
                                String email = orderobject.getString ("email");
                                String phone = orderobject.getString ("phone");
                                String addphone = orderobject.getString ("addphone");
                                String place = orderobject.getString ("place");
                                String description = orderobject.getString ("Description");
                                String url = orderobject.getString ("url");
                                String longitude = orderobject.getString ("longtude");
                                String latitude = orderobject.getString ("latitude");
                                Drivers uo = new Drivers (firstname, last, work, email, phone,addphone,place, description, url,longitude,latitude);
                                muser.add (uo);
                            }
                            madapter = new AdapterDriver (context, muser);
                            mrecyclerView.setAdapter (madapter);
                            madapter.setOnItemClickListener (Driver_List.this);

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
        Intent intent = new Intent (Driver_List.this, Driver_Description.class);
        Drivers cilckeditem = muser.get (position);
        intent.putExtra (EXTRA_FNAME, cilckeditem.getFname ());
        intent.putExtra (EXTRA_LASTNAME, cilckeditem.getLname ());
        intent.putExtra (EXTRA_WORK, cilckeditem.getWork ());
        intent.putExtra (EXTRA_EMAIL, cilckeditem.getEmail ());
        intent.putExtra (EXTRA_PHONE, cilckeditem.getPhone ());
        intent.putExtra (EXTRA_ADDPHONE, cilckeditem.getAddPhone ());
        intent.putExtra (EXTRA_PLACE, cilckeditem.getPlace ());
        intent.putExtra (EXTRA_DES, cilckeditem.getDescription ());
        intent.putExtra (EXTRA_URL, cilckeditem.getUrl ());
        intent.putExtra (EXTRA_LONG, cilckeditem.getLongtude ());
        intent.putExtra (EXTRA_LATI, cilckeditem.getLatitude ());
        startActivity (intent);
    }

    @Override
    public void onDeleteClick(int position) {

    }
    private void MakePhoneCall() {

    }
    private void MakePhoneCall2() {
        if (ContextCompat.checkSelfPermission (Driver_List.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (Driver_List.this,new String[]{Manifest.permission.CALL_PHONE}, ReQUEST_CALL );
        } else {
            String dial = "tel:" + EXTRA_CALL;
            startActivity (new Intent (Intent.ACTION_CALL, Uri.parse (dial)));
            Toasty.info (getApplicationContext (),"Call To"+"    "+EXTRA_FNAME+""+EXTRA_LASTNAME,Toast.LENGTH_LONG).show ();
        }
    }
}