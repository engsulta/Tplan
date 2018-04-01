package com.example.sulta.tplan.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.adapters.HomeLVHistoryTripsAdapter;
import com.example.sulta.tplan.presenter.adapters.HomeLVUpComingTripsAdapter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.LoginActivity;
import com.example.sulta.tplan.view.activities.TripMapActivity;
import com.example.sulta.tplan.view.services.ReminderService;
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
    ReminderService myService;
    boolean isBound = false;
    Context mContext;

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
        MySharedPrefManger.getInstance(context).storeSettings("SettingsState", value);
    }

    @Override
    public int viewSettings(Context context) {
        return MySharedPrefManger.getInstance(context).getSettings("SettingsState");
    }

    @Override
    public void synchTripsToFireBase(Context context) {
        removeAllTrips();
        db = new SqlAdapter(context);
        userManager = UserManager.getUserInstance();
        userManager.setTripsList(db.selectAllTrips());
        userManager.setDurationPerMonth(db.returnDurationSum());//don't forget to get db.select duration per month
        userManager.setDistancePerMonth(db.returnDistanceSum());//don't forget db.select distance per month
        FirebaseDatabase.getInstance().getReference().child("users").child(userManager.getId()).setValue(userManager);

    }

    private void removeAllTrips() {
        userManager = UserManager.getUserInstance();
        Log.i("tplan", "removeAllTrips: " + userManager.getId());
        FirebaseDatabase.getInstance().getReference().child("users").child(userManager.getId()).removeValue();
    }

    @Override
    public void logOutSettings(Context context) {
        userManager = UserManager.getUserInstance();
        synchTripsToFireBase(context);
        db.deleteTripTable();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        // refreshList(context);
        LoginManager.getInstance().logOut();
        // ((Activity)context).finish();
    }

    @Override
    public void shareTrip(Context context, Trip trip) {
        String comingTrip = "Upcoming Trip\n Trip Name: " + trip.getTitle() + "\n" +
                " Trip Date: " + trip.getDate() + "\n" +
                " Trip Duration: " + trip.getDuration() + "\n" +
                " Trip Distance: " + trip.getDistance() + "\n" +
                " From: " + trip.getStartPointName() + "\n" +
                " To: " + trip.getEndPointName(); //+ "\n" + " Trip Notes: " + trip.getNotes();
        Intent sendintent = new Intent();
        sendintent.setAction(Intent.ACTION_SEND);
        sendintent.putExtra(Intent.EXTRA_TEXT, comingTrip);
        sendintent.setType("text/plain");
        context.startActivity(sendintent);
    }

    @Override
    public void viewMapTrip(Context context, Trip trip) {
        Intent intent = new Intent(context, TripMapActivity.class);
        intent.putExtra("tripId", trip.getId());
        context.startActivity(intent);
    }

    @Override
    public void refreshList(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    @Override
    public void removeAllAlarms(Context context) {
        this.mContext = context;
       if (!isBound){
            Intent mintent = new Intent(context, ReminderService.class);
            mContext.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
        }else{
           stopService();
       }
    }

    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;

            myService = binder.geService();
            isBound = true;
            myService.stopAllAlarms(mContext);//send request conde from trip id


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
            Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void startSerivice() {

        upComingTripsAdapter.startSerivice();
    }


    @Override
    public void stopService() {
        if (isBound) {
            mContext.stopService(new Intent(mContext, ReminderService.class));
            mContext.unbindService(myconnection);
            isBound = false;
        }
    }
}
