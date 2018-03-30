package com.example.sulta.tplan.view.activities.interfaces;

import com.example.sulta.tplan.model.TripNote;

import java.util.ArrayList;

/**
 * Created by Asmaa on 3/19/2018.
 */

public interface ICreateTripActivity {
    public String getTripName();
    public double startPointLat();
    public double startPointLan();
    public double endPointLan();
    public double endPointLat();
    public String getTripDate();
    public boolean getTripDirection();
    public long getTripStartTimeInMillis();
    public String getStartPointName();
    public String getEndPointName();
    public ArrayList<TripNote> getNotes();

}
