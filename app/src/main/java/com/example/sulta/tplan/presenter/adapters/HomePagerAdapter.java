package com.example.sulta.tplan.presenter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sulta.tplan.view.fragments.HistoryTrips;
import com.example.sulta.tplan.view.fragments.Settings;
import com.example.sulta.tplan.view.fragments.UpComingTrips;

/**
 * Created by Passant on 3/21/2018.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    int num_taps;
    public HomePagerAdapter(FragmentManager fm, int num_taps) {
        super(fm);
        this.num_taps=num_taps;
    }

    @Override
    public Fragment getItem(int position) {
        UpComingTrips tab1 = new UpComingTrips();
        switch (position) {
            case 0:
                return tab1;
            case 1:
                HistoryTrips tab2 = new HistoryTrips();
                return tab2;
            case 2:
                Settings tab3 = new Settings();
                return tab3;
            default:
                return tab1;
        }
    }

    @Override
    public int getCount() {
        return num_taps;
    }
}
