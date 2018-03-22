package com.example.sulta.tplan.view.activities.interfaces;

import com.example.sulta.tplan.model.Trip;

import java.util.ArrayList;

/**
 * Created by Passant on 3/17/2018.
 */

public interface IHomeActivity {
    ArrayList<Trip>getMyUpcomingTrips();
    void loadMyUpcomingTrips();
}
