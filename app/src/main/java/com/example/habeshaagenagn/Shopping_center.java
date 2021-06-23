package com.example.habeshaagenagn;

import android.os.Parcel;
import android.os.Parcelable;

public class Shopping_center implements Parcelable {
    private String mall_name;
    private String address;
    private String phone_number;
    private String working_hour;
    private String image;
    private String longitude;
    private String latitude;

    public Shopping_center(String mall_name, String address, String phone_number, String working_hour, String image, String longitude, String latitude) {
        this.mall_name = mall_name;
        this.address = address;
        this.phone_number = phone_number;
        this.working_hour = working_hour;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getMall_name() {
        return mall_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getWorking_hour() {
        return working_hour;
    }

    public String getImage() {
        return image;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
//    public String getRating() {
//        return rating;
//    }
    protected Shopping_center(Parcel in) {
        mall_name = in.readString ();
        address = in.readString ();
        working_hour = in.readString ();
        phone_number = in.readString ();
    }

    public static final Parcelable.Creator<Shopping_center> CREATOR = new Parcelable.Creator<Shopping_center> () {
        @Override
        public Shopping_center createFromParcel(Parcel in) {
            return new Shopping_center (in);
        }

        @Override
        public Shopping_center[] newArray(int size) {
            return new Shopping_center[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString (mall_name);
        dest.writeString (address);
        dest.writeString (phone_number);
        dest.writeString (working_hour);
    }
}