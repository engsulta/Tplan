package com.example.sulta.tplan.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.presenter.interfaces.IEditTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.IEditTripActivity;
import com.example.sulta.tplan.view.services.ReminderService;

/**
 * Created by Asmaa on 3/31/2018.
 */

public class EditTripActivityPresenter implements IEditTripActivityPresenter {
    private Context mContext;
    private IEditTripActivity mIView;
    Trip trip;
    Trip newTrip;
    ReminderService myService;
    boolean isBound = false;
    public EditTripActivityPresenter(Context mContext, IEditTripActivity mIView) {
       this.mContext = mContext;
       this.mIView = mIView;
    }

    @Override
    public void setData(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            trip=extras.getParcelable("trip");
            mIView.setName(trip.getTitle());
           // Toast.makeText(mContext, "round: "+trip.isRoundTrip(), Toast.LENGTH_SHORT).show();
            mIView.setDirection(trip.isRoundTrip());
            mIView.setStartPlace(trip.getStartPointName());
            mIView.setEndPlace(trip.getEndPointName());
            mIView.setNotes(new TripNote().convertFromStringToObject(trip.getNotes()));
            int[] tripDate=convertDateStringToInt(trip.getDate());
            mIView.setDate(tripDate[0],tripDate[1],tripDate[2]);
            mIView.setTime(tripDate[3],tripDate[4]);
        }

    }

    @Override
    public void editTrip() {
         newTrip = new Trip();
        PlacePoint startPlacePoint = new PlacePoint();
        PlacePoint endPlacePoint = new PlacePoint();

        newTrip.setTitle(mIView.getTripName());
        if(mIView.startPointLan()!=0.0d&&mIView.startPointLat()!=0.0d) {
            startPlacePoint.setLongitude(mIView.startPointLan());
            startPlacePoint.setLatitude(mIView.startPointLat());
        }
        else {
            startPlacePoint=trip.getStartPoint();

        }
        newTrip.setId(trip.getId());
        newTrip.setStartPoint(startPlacePoint);
        if(mIView.endPointLan()!=0.0d&&mIView.endPointLat()!=0.0d) {
            endPlacePoint.setLongitude(mIView.endPointLan());
            endPlacePoint.setLatitude(mIView.endPointLat());
        }
        else {
            endPlacePoint=trip.getEndPoint();

        }

        newTrip.setEndPoint(endPlacePoint);

        newTrip.setDate(mIView.getTripDate());
        newTrip.setStatus("upComing");
        newTrip.setNotes(new TripNote().convertFromObjectToString(mIView.getNotes()));
        newTrip.setStartTimeInMillis(mIView.getTripStartTimeInMillis());

        if (mIView.getStartPointName()!=""){
        newTrip.setStartPointName(mIView.getStartPointName());}
        else{
            newTrip.setStartPointName(trip.getStartPointName());}
        if (mIView.getEndPointName()!=""){
        newTrip.setEndPointName(mIView.getEndPointName());}
        else{
            newTrip.setEndPointName(trip.getEndPointName());}
        newTrip.setId(trip.getId());
        newTrip.setRoundTrip(mIView.getTripDirection());

        new SqlAdapter(mContext).updateTrip(newTrip);
    }

    private int[] convertDateStringToInt(String dateString){
        int[] dateArray=new int[6];
        String[] temp=dateString.split(" ");
        String[] date=temp[0].split("-");
        String[] time=temp[1].split(":");
        for (int i=0;i<date.length;i++) {
            Log.i("asmaa", "convertDateStringToInt: "+date[i]);
            dateArray[i] = Integer.valueOf(date[i]);
        }
        for (int i=0;i<time.length;i++) {
            dateArray[i+3] = Integer.valueOf(time[i]);
        }
        return dateArray;
    }
    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            setAlarmSetting();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
           // Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };


    @Override
    public void startSerivice() {
        Intent mintent = new Intent(mContext, ReminderService.class);
        mContext.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void stopService() {
        if(isBound){
            mContext.stopService(new Intent(mContext, ReminderService.class));
            mContext.unbindService(myconnection);
            isBound=false;
        }
    }

    private void setAlarmSetting() {
        if (isBound) {
            myService.EditAlarm(mContext, trip.getId(), newTrip.getStartTimeInMillis());//send request conde from trip id
            stopService();
        }
    }

}
