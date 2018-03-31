package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.StatisticsActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IStatisticsActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.IStatisticsActivity;

public class StatisticsActivity extends AppCompatActivity implements IStatisticsActivity{

    TextView distanceTxt, durationTxt;
    ImageButton backButton;
    Toolbar homeToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        distanceTxt = (TextView) findViewById(R.id.distancePerMonthTxt);
        durationTxt = (TextView) findViewById(R.id.durationPerMonthTxt);
        backButton = (ImageButton) findViewById(R.id.backButton);
        homeToolBar = (Toolbar) findViewById(R.id.tpToolBar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IStatisticsActivityPresenter statisticsPresenter = new StatisticsActivityPresenter();
        distanceTxt.setText(String.valueOf(statisticsPresenter.getDistance(this)));
        durationTxt.setText(String.valueOf(statisticsPresenter.getDuration(this)));

        final IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
        homeToolBar.inflateMenu(R.menu.menu_statistics_toolbar);
        homeToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        finish();
                        return true;
                    case R.id.syncTripsToFirebase:
                        Toast.makeText(StatisticsActivity.this, "your trips has been Synchronized ", Toast.LENGTH_SHORT).show();
                        homePresenter.synchTripsToFireBase(StatisticsActivity.this);
                        return true;
                    case R.id.logoutFromApp:
                        Toast.makeText(StatisticsActivity.this, "you logged out", Toast.LENGTH_SHORT).show();
                        homePresenter.logOutSettings(StatisticsActivity.this);
                        finish();

                        Intent logoutIntent = new Intent(StatisticsActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);

                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = (SupportMenuInflater) getMenuInflater();
        inflater.inflate(R.menu.menu_statistics_toolbar, menu);
        return true;
    }
}
