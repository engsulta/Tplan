package com.example.sulta.tplan.presenter;

import android.content.Context;
import android.widget.ListView;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.adapters.HomeLVUpComingTripsAdapter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;

import java.util.List;

/**
 * Created by Passant on 3/17/2018.
 */

public class HomeActivityPresenter implements IHomeActivityPresenter {
    @Override
    public void viewUpComingTrips(Context context, ListView upComingTripsList, int userId) {
        SqlAdapter sqlAdapter = new SqlAdapter(context);
        List<Trip> upComingTrips = sqlAdapter.selectUpComingTrips(userId);
        HomeLVUpComingTripsAdapter upComingTripsAdapter = new HomeLVUpComingTripsAdapter(context, R.layout.item_list_upcoming_trips, R.id.tripName, upComingTrips);
        upComingTripsList.setAdapter(upComingTripsAdapter);
    }

}
