package com.example.sulta.tplan.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

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

    public String convertFromObjectToString (ArrayList<TripNote> noteArrayList){
        String noteString="";
        for (int i=0;i<noteArrayList.size();i++){

            if(noteArrayList.get(i).isChecked()==true)
                noteString+="*"+noteArrayList.get(i).getText()+",";
            else
                noteString+=noteArrayList.get(i).getText()+",";

            if(i==(noteArrayList.size()-1)){
                noteString=noteString.substring(0,noteString.length()-1);
            }
        }
        return noteString;
    }
    public ArrayList<TripNote> convertFromStringToObject(String noteString){
        ArrayList<TripNote> tripNoteArrayList=new ArrayList<>();
        String[] noteText= noteString.split(",");
        for (int i=0;i<noteText.length;i++){
            if (noteText[i].charAt(0)=='*'){
                tripNoteArrayList.add(new TripNote(noteText[i].substring(1,noteText[i].length()),true));
                Log.i("asmaaCheck", "convertFromStringToObject: "+noteText[i].substring(1,noteText[i].length()));
            }
            else {
                tripNoteArrayList.add(new TripNote(noteText[i],false));

            }
        }
        return tripNoteArrayList;
    }
}