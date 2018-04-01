package com.example.sulta.tplan.presenter;

import android.content.Context;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.presenter.interfaces.IStatisticsActivityPresenter;

/**
 * Created by Passant on 4/1/2018.
 */

public class StatisticsActivityPresenter implements IStatisticsActivityPresenter {
    @Override
    public double getDistance(Context context) {
        SqlAdapter sqlAdapter = new SqlAdapter(context);
        return sqlAdapter.returnDistanceSum();
    }

    @Override
    public double getDuration(Context context) {
        SqlAdapter sqlAdapter = new SqlAdapter(context);
        return sqlAdapter.returnDurationSum();
    }
}
