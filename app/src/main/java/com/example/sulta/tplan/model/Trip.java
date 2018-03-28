package com.example.sulta.tplan.model;

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
    String notes;//*checked,unchecked,*checked
    boolean isRoundTrip;
    double distance;
    String date; //YYYY-MM-DD HH:MM:SS
    Long startTimeInMillis;

    public void setStartTimeInMillis(Long startTimeInMillis) {
        this.startTimeInMillis = startTimeInMillis;
    }

    public Long getStartTimeInMillis() {

        return startTimeInMillis;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String tripNotes) {
        this.notes = tripNotes;
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

}