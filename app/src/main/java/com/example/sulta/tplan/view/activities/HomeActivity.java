package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.adapters.HomePagerAdapter;
import com.example.sulta.tplan.view.activities.interfaces.IHomeActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {

    Toolbar homeToolBar;
    TabLayout homeTabs;
    ViewPager homeViewPager;
    TextView currentTabName;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        currentTabName = (TextView) findViewById(R.id.currentTabName);
        homeToolBar = (Toolbar) findViewById(R.id.tpToolBar);
        setSupportActionBar(homeToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        homeToolBar.inflateMenu(R.menu.menu_toolbar);
        homeToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfile:
                        Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.syncTripsToFirebase:
                        Toast.makeText(HomeActivity.this, "Sync", Toast.LENGTH_SHORT).show();
                        //TODO calling sync method to get data from firebase
                        return true;
                    case R.id.logoutFromApp:
                        Toast.makeText(HomeActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        //TODO calling logout method which clears user's data
                        return true;
                    default:
                        return false;
                }
            }
        });

        homeTabs = (TabLayout) findViewById(R.id.homeTabs);
        //  add taps to taplayout ;
        homeTabs.addTab(homeTabs.newTab().setIcon(R.drawable.ic_history_black_24dp));
        homeTabs.addTab(homeTabs.newTab().setIcon(R.drawable.ic_done_all_black_24dp));
        homeTabs.addTab(homeTabs.newTab().setIcon(R.drawable.ic_settings_black_24dp));
        currentTabName.setText("UpComing Trips");
        // make gravity  fill tap layout
        homeTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        homeViewPager = (ViewPager) findViewById(R.id.homeViewPager);

        final HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), homeTabs.getTabCount());

        homeViewPager.setAdapter(adapter);
        homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeTabs));
        // listner when select tap  change viewoager (fragments)

        homeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //on select tap  change view page item to another fragment
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    currentTabName.setText("UpComing Trips");
                    tab.getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
                } else if(tab.getPosition()==1){
                    currentTabName.setText("History Trips");
                } else{
                    currentTabName.setText("Settings");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = (SupportMenuInflater) getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    ////sulta editing

// make refrence for synchdata and give home refrence

    @Override
    public ArrayList<Trip> getAllTrips() {
        //return all trips here
        return null;
    }



    @Override
    public void setAllTrips(ArrayList<Trip> trips) {
    //set your trips with new one and reload view
    }


    ///sulta editing
}

