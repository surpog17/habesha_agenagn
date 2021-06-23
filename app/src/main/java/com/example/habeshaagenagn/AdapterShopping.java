package com.example.habeshaagenagn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habeshaagenagn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.habeshaagenagn.R.id.rate;

class AdapterShopping extends RecyclerView.Adapter<AdapterShopping.AdapterShopping_viewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<Shopping_center> userssItemArrayList;
    private List<Shopping_center> exampleListFull;
    // private OnItemClickListener mlistener;
    //private AdapterView.OnItemClickListener mlistener;
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Shopping_center> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Shopping_center item : exampleListFull) {
                    if (item.getMall_name ().toLowerCase().contains(filterPattern)) {
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
    private AdapterShopping.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(AdapterShopping.OnItemClickListener listener) {
        mListener = listener;
    }
    public class AdapterShopping_viewHolder extends RecyclerView.ViewHolder {
        public TextView mall_name, address, working_time, phone_number,longi,lati,rating;
        public ImageView shoppine_image;
        public RatingBar ratingbar;

        LinearLayout cd;

        public AdapterShopping_viewHolder(@NonNull View itemView, final AdapterShopping.OnItemClickListener listener) {
            super (itemView);
            mall_name = itemView.findViewById (R.id.mall_name);
            address = itemView.findViewById (R.id.address);
            working_time = itemView.findViewById (R.id.worktime);
            phone_number = itemView.findViewById (R.id.phone_number);
            longi = itemView.findViewById (R.id.slong);
            lati = itemView.findViewById (R.id.slati);
            shoppine_image=itemView.findViewById (R.id.shopping_image);
            longi=itemView.findViewById (R.id.slong);
            lati=itemView.findViewById (R.id.slati);
            rating=itemView.findViewById (R.id.rating);
            ratingbar=itemView.findViewById (rate);
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
    public AdapterShopping.AdapterShopping_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingcenter_service, parent, false);
        AdapterShopping.AdapterShopping_viewHolder evh = new AdapterShopping_viewHolder (v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShopping.AdapterShopping_viewHolder holder, int position) {
        Shopping_center currentitem = userssItemArrayList.get (position);
        String mallname = currentitem.getMall_name ();
        String address = currentitem.getAddress ();
        String working_time = currentitem.getWorking_hour ();
        String phone = currentitem.getPhone_number ();
        String imageurl= currentitem.getImage ();
        String longitude= currentitem.getLongitude();
        String latitude= currentitem.getLatitude ();
//        String rate= currentitem.getRating ();
//        String ratebar= currentitem.getRating ();
        holder.mall_name.setText (mallname);
        holder.address.setText (address);
        holder.working_time.setText (working_time);
        holder.phone_number.setText (phone);
        holder.longi.setText (longitude);
        holder.lati.setText (latitude);
//      sho

        Picasso.with(mcontext).load(imageurl).fit().centerInside ().into(holder.shoppine_image);
    }
    public AdapterShopping(ArrayList<Shopping_center> muser) {
        userssItemArrayList = muser;
        exampleListFull = new ArrayList<>(muser);
    }
    public AdapterShopping(Context context, ArrayList<Shopping_center> muser) {
        userssItemArrayList = muser;
        mcontext = context;
        exampleListFull = new ArrayList<> (userssItemArrayList);
    }


    @Override
    public int getItemCount() {
        return userssItemArrayList.size();
    }

}
