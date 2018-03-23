package com.example.sulta.tplan.model;

import java.util.List;

/**
 * Created by Passant on 3/17/2018.
 */

public class Trip {
    int id;
    String title;
    PlacePoint startPoint;
    PlacePoint endPoint;
    double duration;
    String status;
    List<TripNote> tripNotes;
    boolean isRoundTrip;
    double distance;
    int userId;
    String date; //YYYY-MM-DD HH:MM:SS

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PlacePoint getStartPoint() {
        return startPoint;
    }

    public PlacePoint getEndPoint() {
        return endPoint;
    }

    public void setStartPoint(PlacePoint startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(PlacePoint endPoint) {
        this.endPoint = endPoint;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TripNote> getTripNotes() {
        return tripNotes;
    }

    public void setTripNotes(List<TripNote> tripNotes) {
        this.tripNotes = tripNotes;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
