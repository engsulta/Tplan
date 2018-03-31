package com.example.sulta.tplan.model;

import java.io.Serializable;

/**
 * Created by Asmaa on 3/21/2018.
 */

public class PlacePoint implements Serializable {
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
}
