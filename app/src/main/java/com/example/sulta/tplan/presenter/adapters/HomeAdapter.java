package com.example.sulta.tplan.presenter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Passant on 3/21/2018.
 */

public class HomeAdapter extends FragmentStatePagerAdapter {
    int num_fragment;

    public HomeAdapter(FragmentManager fm , int fragment_num) {

        super(fm);
        this.num_fragment= fragment_num;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return num_fragment;
    }
}