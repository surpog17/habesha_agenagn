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

class AdapterHomework extends RecyclerView.Adapter<AdapterHomework.AdapterHomework_viewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<Homeworks> userssItemArrayList;
    private List<Homeworks> exampleListFull;
    // private OnItemClickListener mlistener;
    //private AdapterView.OnItemClickListener mlistener;
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Homeworks> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Homeworks item : exampleListFull) {
                    if (item.getServicetype ().toLowerCase().contains(filterPattern)) {
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
    public class AdapterHomework_viewHolder extends RecyclerView.ViewHolder {
        public TextView firstname, lastname, servicetype, company_name, email, phone,addphone, description,iname,elongitude, elatitude,place;
        LinearLayout cd;
        public ImageView url;

        public AdapterHomework_viewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super (itemView);
            servicetype = itemView.findViewById (R.id.service_type);
            company_name = itemView.findViewById (R.id.companyname);
            email = itemView.findViewById (R.id.emailhome);
            phone = itemView.findViewById (R.id.phonehome);
            addphone = itemView.findViewById (R.id.addphone_home);
            description = itemView.findViewById (R.id.home_image);
            url=itemView.findViewById (R.id.descriptionhome);
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
    public AdapterHomework.AdapterHomework_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeworks_serviceproviders, parent, false);
        AdapterHomework_viewHolder evh = new AdapterHomework_viewHolder (v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomework.AdapterHomework_viewHolder holder, int position) {
        Homeworks currentitem = userssItemArrayList.get (position);
        String servicetype = currentitem.getServicetype ();
        String companyname = currentitem.getCompanyname ();
        String email = currentitem.getEmail ();
        String phone = currentitem.getPhone ();
        String addphone = currentitem.getAddphone ();
        String imageurl= currentitem.getUrl ();
        String description = currentitem.getDescription ();
        String elongtiude = currentitem.getLongtude ();
        String elatitude = currentitem.getLatitude ();
        String place = currentitem.getPlace ();
        holder.servicetype.setText (servicetype);
        holder.company_name.setText (companyname);
        holder.email.setText (email);
        holder.phone.setText (phone);
        holder.addphone.setText (addphone);
        holder.description.setText (imageurl);
        holder.elongitude.setText (elongtiude);
        holder.elatitude.setText (elatitude);
        holder.place.setText (place);
        Picasso.with(mcontext).load(description).fit().centerInside ().into(holder.url);
    }
    public AdapterHomework(ArrayList<Homeworks> muser) {
        userssItemArrayList = muser;
        exampleListFull = new ArrayList<>(muser);
    }
    public AdapterHomework(Context context, ArrayList<Homeworks> muser) {
        userssItemArrayList = muser;
        mcontext = context;
        exampleListFull = new ArrayList<> (userssItemArrayList);
    }


    @Override
    public int getItemCount() {
        return userssItemArrayList.size();
    }


}
