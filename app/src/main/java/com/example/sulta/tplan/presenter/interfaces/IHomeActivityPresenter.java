package com.example.sulta.tplan.presenter.interfaces;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by Passant on 3/17/2018.
 */

public interface IHomeActivityPresenter {
    public void viewUpComingTrips(Context context, ListView upComingTripsList, int userId);
}
