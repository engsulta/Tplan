package com.example.sulta.tplan.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Asmaa on 3/21/2018.
 */

public class PlacePoint implements Serializable, Parcelable {
    double latitude;
    double longitude;

    public PlacePoint() {

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "lat:" + latitude + " lon:" + longitude;
    }

    protected PlacePoint(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlacePoint> CREATOR = new Parcelable.Creator<PlacePoint>() {
        @Override
        public PlacePoint createFromParcel(Parcel in) {
            return new PlacePoint(in);
        }

        @Override
        public PlacePoint[] newArray(int size) {
            return new PlacePoint[size];
        }
    };
}