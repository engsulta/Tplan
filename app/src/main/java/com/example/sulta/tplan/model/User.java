package com.example.sulta.tplan.model;

import java.util.ArrayList;

/**
 * Created by Passant on 3/17/2018.
 */

public class User {
    String id;
    String name;
    String password;
    String email;
    double distancePerMonth;
    double durationPerMonth;
    ArrayList<Trip> tripsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDistancePerMonth() {
        return distancePerMonth;
    }

    public void setDistancePerMonth(double distancePerMonth) {
        this.distancePerMonth = distancePerMonth;
    }

    public double getDurationPerMonth() {
        return durationPerMonth;
    }

    public void setDurationPerMonth(double durationPerMonth) {
        this.durationPerMonth = durationPerMonth;
    }

    public void setTripsList(ArrayList<Trip> tripsList) {
        this.tripsList = tripsList;
    }

    public ArrayList<Trip> getTripsList() {

        return tripsList;
    }
}
