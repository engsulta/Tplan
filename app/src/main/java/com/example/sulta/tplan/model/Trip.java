package com.example.sulta.tplan.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Asmaa on 3/17/2018.
 */

public class Trip implements Serializable, Parcelable {
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
    String startPointName;
    String endPointName;

    public Trip(){}

    public String getStartPointName() {
        return startPointName;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public void setStartPointName(String startPointName) {
        this.startPointName = startPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

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


    protected Trip(Parcel in) {
        id = in.readInt();
        title = in.readString();
        startPoint = (PlacePoint) in.readValue(PlacePoint.class.getClassLoader());
        endPoint = (PlacePoint) in.readValue(PlacePoint.class.getClassLoader());
        duration = in.readDouble();
        status = in.readString();
        notes = in.readString();
        isRoundTrip = in.readByte() != 0x00;
        distance = in.readDouble();
        date = in.readString();
        startTimeInMillis = in.readByte() == 0x00 ? null : in.readLong();
        startPointName = in.readString();
        endPointName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeValue(startPoint);
        dest.writeValue(endPoint);
        dest.writeDouble(duration);
        dest.writeString(status);
        dest.writeString(notes);
        dest.writeByte((byte) (isRoundTrip ? 0x01 : 0x00));
        dest.writeDouble(distance);
        dest.writeString(date);
        if (startTimeInMillis == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(startTimeInMillis);
        }
        dest.writeString(startPointName);
        dest.writeString(endPointName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}