package com.example.sulta.tplan.presenter.interfaces;

import android.content.Intent;

/**
 * Created by Asmaa on 3/31/2018.
 */

public interface IEditTripActivityPresenter {
    public void setData(Intent intent);
    public void editTrip();
    public void startSerivice();
    public void stopService();
}
