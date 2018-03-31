package com.example.sulta.tplan.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.presenter.interfaces.IEditTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.IEditTripActivity;

/**
 * Created by Asmaa on 3/31/2018.
 */

public class EditTripActivityPresenter implements IEditTripActivityPresenter {
    private Context mContext;
    private IEditTripActivity mIView;
    Trip trip;

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
        Trip newTrip = new Trip();
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

        newTrip.setStartPointName(mIView.getStartPointName());
        newTrip.setEndPointName(mIView.getEndPointName());
        newTrip.setId(trip.getId());

        new SqlAdapter(mContext).updateTrip(newTrip);
    }

    private int[] convertDateStringToInt(String dateString){
        int[] dateArray=new int[6];
        String[] temp=dateString.split(" ");
        String[] date=temp[0].split(":");
        String[] time=temp[1].split(":");
        for (int i=0;i<date.length;i++) {
            dateArray[i] = Integer.valueOf(date[i]);
        }
        for (int i=3;i<dateArray.length;i++) {
            dateArray[i] = Integer.valueOf(time[i]);
        }
        return dateArray;
    }
}
