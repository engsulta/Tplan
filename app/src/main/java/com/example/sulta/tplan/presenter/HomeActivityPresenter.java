package com.example.sulta.tplan.presenter;

import android.content.Context;
import android.widget.ListView;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.adapters.HomeLVHistoryTripsAdapter;
import com.example.sulta.tplan.presenter.adapters.HomeLVUpComingTripsAdapter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.utilities.MySharedPrefManger;
import com.example.sulta.tplan.view.utilities.UserManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Passant on 3/17/2018.
 */

public class HomeActivityPresenter implements IHomeActivityPresenter {

    SqlAdapter sqlAdapter;
    HomeLVUpComingTripsAdapter upComingTripsAdapter;
    HomeLVHistoryTripsAdapter historyTripsAdapter;
    private FirebaseAuth mAuth;
    private UserManager userManager;
    SqlAdapter db;

    @Override
    public void viewUpComingTrips(Context context, ListView upComingTripsList) {
        sqlAdapter = new SqlAdapter(context);
        List<Trip> upComingTrips = sqlAdapter.selectUpComingTrips();
        upComingTripsAdapter = new HomeLVUpComingTripsAdapter(context, R.layout.item_list_upcoming_trips_cardview, R.id.tripName, upComingTrips);
        upComingTripsList.setAdapter(upComingTripsAdapter);
    }

    @Override
    public void viewHistoryTrips(Context context, ListView historyTripsList) {
        sqlAdapter = new SqlAdapter(context);
        List<Trip> historyTrips = sqlAdapter.selectTrips();
        historyTripsAdapter = new HomeLVHistoryTripsAdapter(context, R.layout.item_list_history_trips_cardview, R.id.tripName, historyTrips);
        //historyTripsAdapter.notifyDataSetChanged();
        //historyTripsAdapter.notifyDataSetInvalidated();
        historyTripsList.setAdapter(historyTripsAdapter);
    }

    @Override
    public boolean deleteTrip(Context context, int tripId) {
        sqlAdapter = new SqlAdapter(context);
        sqlAdapter.deleteTrip(tripId);
        return true;
    }

    @Override
    public boolean editTrip(Context context, Trip trip) {
        sqlAdapter = new SqlAdapter(context);
        sqlAdapter.updateTrip(trip);
        return true;
    }

    @Override
    public void editSettings(Context context, int value) {
        MySharedPrefManger.getInstance(context).storeSettings("SettingsState",value);
    }

    @Override
    public int viewSettings(Context context) {
        return MySharedPrefManger.getInstance(context).getSettings("SettingsState");
    }

    @Override
    public void synchTripsToFireBase(Context context) {
        db = new SqlAdapter(context);
        userManager = UserManager.getUserInstance();
        userManager.setTripsList(db.selectAllTrips());
        userManager.setDurationPerMonth(3);//don't forget to get db.select duration per month
        userManager.setDistancePerMonth(10);//don't forget db.select distance per month
        FirebaseDatabase.getInstance().getReference().child("users").child(userManager.getId()).setValue(userManager);

    }

    @Override
    public void logOutSettings(Context context) {
        db = new SqlAdapter(context);
        userManager = UserManager.getUserInstance();
        userManager.setTripsList(db.selectAllTrips());
        userManager.setDurationPerMonth(3);
        userManager.setDistancePerMonth(10);
        FirebaseDatabase.getInstance().getReference().child("users").child(userManager.getId()).setValue(userManager);

        db.deleteTripTable();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void startSerivice() {
        upComingTripsAdapter.startSerivice();
    }

    @Override
    public void stopService() {


    }
}