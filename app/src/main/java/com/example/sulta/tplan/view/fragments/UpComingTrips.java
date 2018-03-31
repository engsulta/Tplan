package com.example.sulta.tplan.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.CreateTripActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingTrips extends Fragment {

    ListView upComingTripsList;

    public UpComingTrips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_up_coming_trips, container, false);
        upComingTripsList = myView.findViewById (R.id.upComingTrips);
        FloatingActionButton createTripBtn = myView.findViewById(R.id.createTripBtn);
        createTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTripActivity.class);
                getContext().startActivity(intent);
            }
        });
        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
        homePresenter.viewUpComingTrips(getContext(), upComingTripsList);
    }

    @Override
    public void onPause() {
        super.onPause();
  //      IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
//        homePresenter.stopService();

    }

    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(getContext(),v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.viewTripDetails) {
                    Toast.makeText(getContext(), "Viewed", Toast.LENGTH_SHORT).show();
                } else if(item.getItemId()== R.id.deleteTrip){
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });// to implement on click event on items of menu
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_listview_options, popup.getMenu());
        popup.show();
    }
}
