package com.example.habeshaagenagn;

import android.os.Parcel;
import android.os.Parcelable;

class Electronics implements Parcelable {
    private String fname;
    private String lname;
    private String work;
    private String experience;
    private String phone;
    private String addphone;
    private String email;
    private String description;
    private String url;
    private String longitude;
    private String latitude;
    private String place;



    public Electronics(String fname, String lname, String work, String experience, String phone,String addphone, String email, String description, String url,String longitude, String latitude, String  place) {
        this.fname = fname;
        this.lname = lname;
        this.work = work;
        this.experience = experience;
        this.phone = phone;
        this.addphone = addphone;
        this.email = email;
        this.description = description;
        this.url = url;
        this.longitude = longitude;
        this.latitude= latitude;
        this.place= place;
    }

    protected Electronics(Parcel in) {
        fname = in.readString ();
        lname = in.readString ();
        work = in.readString ();
        experience = in.readString ();
        phone = in.readString ();
        addphone = in.readString ();
        email = in.readString ();
        description = in.readString ();
    }

    public static final Creator<Electronics> CREATOR = new Creator<Electronics> () {
        @Override
        public Electronics createFromParcel(Parcel in) {
            return new Electronics (in);
        }

        @Override
        public Electronics[] newArray(int size) {
            return new Electronics[size];
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

    public String getExperience() {
        return experience;
    }

    public String getPhone() {
        return email;
    }
    public String getAddphone() {
        return addphone;
    }
    public String getEmail() {
        return phone;
    }

    public String getDescription() {
        return description;
    }
    public String getUrl(){ return url ;}

    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getPlace() {
        return place;
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
        dest.writeString (experience);
        dest.writeString (phone);
        dest.writeString (addphone);
        dest.writeString (email);
        dest.writeString (description);
    }
}