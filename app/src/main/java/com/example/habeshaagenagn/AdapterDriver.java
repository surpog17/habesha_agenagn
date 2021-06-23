package com.example.habeshaagenagn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habeshaagenagn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class AdapterDriver extends RecyclerView.Adapter<AdapterDriver.AdapterDriver_viewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<Drivers> userssItemArrayList;
    private List<Drivers> exampleListFull;
    // private OnItemClickListener mlistener;
    //private AdapterView.OnItemClickListener mlistener;
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Drivers> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Drivers item : exampleListFull) {
                    if (item.getWork ().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userssItemArrayList.clear();
            userssItemArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private AdapterDriver.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(AdapterDriver.OnItemClickListener listener) {
        mListener = listener;
    }
    public class AdapterDriver_viewHolder extends RecyclerView.ViewHolder {
        public TextView firstname, lastname, work, experience, email, phone, description,addphone,place,longi,lati;
        public ImageView url;
        LinearLayout cd;

        public AdapterDriver_viewHolder(@NonNull View itemView, final AdapterDriver.OnItemClickListener listener) {
            super (itemView);
            firstname = itemView.findViewById (R.id.firstelectronics);
            lastname = itemView.findViewById (R.id.lastelectronics);
            work = itemView.findViewById (R.id.workelectronics);
            experience = itemView.findViewById (R.id.experienceelectrioncs);
            email = itemView.findViewById (R.id.emailelectronics);
            phone = itemView.findViewById (R.id.phones);
            addphone=itemView.findViewById (R.id.addphoneelectronics);
            place=itemView.findViewById (R.id.place);
            description = itemView.findViewById (R.id.descriptionelectroincs);
            url=itemView.findViewById (R.id.driverimage);
            longi=itemView.findViewById (R.id.dlong);
            lati=itemView.findViewById (R.id.dlati);
            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position=getAdapterPosition ();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick (position);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public AdapterDriver.AdapterDriver_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_serviceproviders, parent, false);
        AdapterDriver.AdapterDriver_viewHolder evh = new AdapterDriver_viewHolder (v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDriver.AdapterDriver_viewHolder holder, int position) {
        Drivers currentitem = userssItemArrayList.get (position);
        String fname = currentitem.getFname ();
        String lastname = currentitem.getLname ();
        String work = currentitem.getWork ();
        String email = currentitem.getEmail ();
        String phone = currentitem.getPhone ();
        String addphone = currentitem.getAddPhone ();
        String place = currentitem.getPlace ();
        String description = currentitem.getDescription ();
        String imageurl= currentitem.getUrl ();
        String longitude= currentitem.getLongtude ();
        String latitude= currentitem.getLatitude ();
        holder.firstname.setText (fname);
        holder.lastname.setText (lastname);
        holder.work.setText (work);
        holder.email.setText (email);
        holder.phone.setText (phone);
        holder.addphone.setText (addphone);
        holder.place.setText (place);
        holder.description.setText (description);
        holder.longi.setText (longitude);
        holder.lati.setText (latitude);
        Picasso.with(mcontext).load(imageurl).fit().centerInside ().into(holder.url);
    }
    public AdapterDriver(ArrayList<Drivers> muser) {
        userssItemArrayList = muser;
        exampleListFull = new ArrayList<>(muser);
    }
    public AdapterDriver(Context context, ArrayList<Drivers> muser) {
        userssItemArrayList = muser;
        mcontext = context;
        exampleListFull = new ArrayList<> (userssItemArrayList);
    }


    @Override
    public int getItemCount() {
        return userssItemArrayList.size();
    }

    }
