package com.example.habeshaagenagn;

import android.os.Parcel;
import android.os.Parcelable;

public class Homeworks implements Parcelable {
    private String companyname;
    private String servicetype;
    private String email;
    private String phone;
    private String addphone;
    private String place;
    private String description;
    private String url;
    private String longtude;
    private String latitude;

    public Homeworks(String servicetype, String companyname, String email, String phone, String addphone, String place, String description, String url, String longtude, String latitude) {
        this.companyname = companyname;
        this.servicetype = servicetype;
        this.email = email;
        this.phone = phone;
        this.addphone = addphone;
        this.place = place;
        this.description = description;
        this.url = url;
        this.longtude = longtude;
        this.latitude = latitude;
    }
    public String getEmail() {
        return email;
    }
    public String getCompanyname() {
        return companyname;
    }

    public String getServicetype() {
        return servicetype;
    }


    public String getPhone() {
        return phone;
    }

    public String getAddphone() {
        return addphone;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getLongtude() {
        return longtude;
    }

    public String getLatitude() {
        return latitude;
    }

    protected Homeworks(Parcel in) {
        companyname=in.readString ();
        servicetype = in.readString ();
        email = in.readString ();
        phone = in.readString ();
        addphone = in.readString ();
        place = in.readString ();
        description = in.readString ();
    }

    public static final Parcelable.Creator<Homeworks> CREATOR = new Parcelable.Creator<Homeworks> () {
        @Override
        public Homeworks createFromParcel(Parcel in) {
            return new Homeworks (in);
        }

        @Override
        public Homeworks[] newArray(int size) {
            return new Homeworks[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString (companyname);
        dest.writeString (servicetype);
        dest.writeString (email);
        dest.writeString (phone);
        dest.writeString (addphone);
        dest.writeString (place);
        dest.writeString (description);
    }
}
