package com.example.sulta.tplan.view.activities.interfaces;

import com.example.sulta.tplan.model.TripNote;

import java.util.ArrayList;

/**
 * Created by Asmaa on 3/31/2018.
 */

public interface IEditTripActivity extends ICreateTripActivity {
    public void setName(String name);
    public void setDirection (Boolean dir);
    public void setStartPlace (String placeName);
    public void setEndPlace (String placeName);
    public void setDate(int year,int mon,int day);
    public void setTime(int hour,int min);
    public void setNotes(ArrayList<TripNote> noteArrayList);



}
