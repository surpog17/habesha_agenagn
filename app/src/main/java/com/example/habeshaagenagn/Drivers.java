package com.example.habeshaagenagn;

import android.os.Parcel;
import android.os.Parcelable;

public class Drivers implements Parcelable {
    private String fname;
    private String lname;
    private String work;
    private String phone;
    private String addphone;
    private String place;
    private String email;
    private String description;
    private String url;
    private String longtude;
    private String latitude;


    public Drivers(String fname, String lname, String work, String email,String phone,String addphone,String place, String description, String url,String longtude,String latitude) {
        this.fname = fname;
        this.lname = lname;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.addphone = addphone;
        this.place = place;
        this.description = description;
        this.url = url;
        this.longtude = longtude;
        this.latitude = latitude;

    }





    protected Drivers(Parcel in) {
        fname = in.readString ();
        lname = in.readString ();
        work = in.readString ();
        phone = in.readString ();
        addphone = in.readString ();
        place = in.readString ();
        email = in.readString ();
        description = in.readString ();
    }

    public static final Parcelable.Creator<Drivers> CREATOR = new Parcelable.Creator<Drivers> () {
        @Override
        public Drivers createFromParcel(Parcel in) {
            return new Drivers (in);
        }

        @Override
        public Drivers[] newArray(int size) {
            return new Drivers[size];
        }
    };

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getWork() {
        return work;
    }


    public String getPhone() {
        return phone;
    }
    public String getAddPhone() {
        return addphone;
    }
    public String getPlace() {
        return place;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }
    public String getUrl(){
        return url ;}
    public String getLongtude() {
        return longtude;
    }
    public String getLatitude() {
        return latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString (fname);
        dest.writeString (lname);
        dest.writeString (work);
        dest.writeString (email);
        dest.writeString (phone);
        dest.writeString (addphone);
        dest.writeString (place);
        dest.writeString (description);
    }
}