package com.example.sulta.tplan.presenter;

import android.content.Context;
import android.util.Log;

import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.interfaces.ICreateTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.ICreateTripActivity;

/**
 * Created by Asmaa on 3/19/2018.
 */

public class CreateTripActivityPresenter implements ICreateTripActivityPresenter {
    private Context mContext;
    private ICreateTripActivity mIView;
    Trip trip;
    public CreateTripActivityPresenter(Context mContext, ICreateTripActivity mIView) {
        this.mContext = mContext;
        this.mIView = mIView;
    }

    @Override
    public void createTrip() {
        trip=new Trip();
        PlacePoint startPlacePoint=new PlacePoint();
        PlacePoint endPlacePoint=new PlacePoint();

        trip.setTitle(mIView.getTripName());

        startPlacePoint.setLongitude(mIView.startPointLan());
        startPlacePoint.setLatitude(mIView.startPointLat());
        trip.setStartPoint(startPlacePoint);

        endPlacePoint.setLongitude(mIView.endPointLan());
        endPlacePoint.setLatitude(mIView.endPointLat());
        trip.setEndPoint(endPlacePoint);

        Log.i("test",trip.getTitle()+", "+trip.getStartPoint().getLongitude()+", "+trip.getEndPoint().getLongitude());

       // new SqlAdapter(mContext).insertTrip(trip);
    }
}
