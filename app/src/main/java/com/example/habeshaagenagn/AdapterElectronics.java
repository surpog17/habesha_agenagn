package com.example.habeshaagenagn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class AdapterElectronics extends RecyclerView.Adapter<AdapterElectronics.AdapterElectronics_viewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<Electronics> userssItemArrayList;
    private List<Electronics> exampleListFull;
   // private OnItemClickListener mlistener;
    //private AdapterView.OnItemClickListener mlistener;
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Electronics> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Electronics item : exampleListFull) {
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
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public class AdapterElectronics_viewHolder extends RecyclerView.ViewHolder {
        public TextView firstname, lastname, work, experience, email, phone,addphone, description,iname,elongitude, elatitude,place;
        LinearLayout cd;
        public ImageView url;

        public AdapterElectronics_viewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super (itemView);
            firstname = itemView.findViewById (R.id.firstelectronics);
            lastname = itemView.findViewById (R.id.lastelectronics);
            work = itemView.findViewById (R.id.workelectronics);
            experience = itemView.findViewById (R.id.experienceelectrioncs);
            email = itemView.findViewById (R.id.emailelectronics);
            phone = itemView.findViewById (R.id.phoneelectronics);
            addphone = itemView.findViewById (R.id.addphone_electronics);
            description = itemView.findViewById (R.id.descriptionelectroincs);
            url=itemView.findViewById (R.id.electronic_image);
            elongitude=itemView.findViewById (R.id.elong);
            elatitude=itemView.findViewById (R.id.elati);
            place=itemView.findViewById (R.id.place);
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
    public AdapterElectronics.AdapterElectronics_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.electronics_serviceproviders, parent, false);
        AdapterElectronics_viewHolder evh = new AdapterElectronics_viewHolder (v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterElectronics.AdapterElectronics_viewHolder holder, int position) {
        Electronics currentitem = userssItemArrayList.get (position);
        String fname = currentitem.getFname ();
        String lastname = currentitem.getLname ();
        String work = currentitem.getWork ();
        String exp = currentitem.getExperience ();
        String email = currentitem.getEmail ();
        String phone = currentitem.getPhone ();
        String addphone = currentitem.getAddphone ();
        String imageurl= currentitem.getUrl ();
        String description = currentitem.getDescription ();
        String elongtiude = currentitem.getLongitude ();
        String elatitude = currentitem.getLatitude ();
        String place = currentitem.getPlace ();
        holder.firstname.setText (fname);
        holder.lastname.setText (lastname);
        holder.work.setText (work);
        holder.experience.setText (exp);
        holder.email.setText (email);
        holder.phone.setText (phone);
        holder.addphone.setText (addphone);
        holder.description.setText (description);
        holder.elongitude.setText (elongtiude);
        holder.elatitude.setText (elatitude);
        holder.place.setText (place);
        Picasso.with(mcontext).load(imageurl).fit().centerInside ().into(holder.url);
    }
    public AdapterElectronics(ArrayList<Electronics> muser) {
        userssItemArrayList = muser;
        exampleListFull = new ArrayList<>(muser);
    }
    public AdapterElectronics(Context context, ArrayList<Electronics> muser) {
        userssItemArrayList = muser;
        mcontext = context;
        exampleListFull = new ArrayList<> (userssItemArrayList);
    }


    @Override
    public int getItemCount() {
        return userssItemArrayList.size();
    }


}
