package com.example.sulta.tplan.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Asmaa on 3/17/2018.
 */

public class TripNote implements Serializable, Parcelable {

    String text;
    boolean checked;

    public TripNote(){}
    public TripNote(String text,boolean checked ){
        this.text=text;
        this.checked=checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    protected TripNote(Parcel in) {
        text = in.readString();
        checked = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (checked ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripNote> CREATOR = new Parcelable.Creator<TripNote>() {
        @Override
        public TripNote createFromParcel(Parcel in) {
            return new TripNote(in);
        }

        @Override
        public TripNote[] newArray(int size) {
            return new TripNote[size];
        }
    };
}