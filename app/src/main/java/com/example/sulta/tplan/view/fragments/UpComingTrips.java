package com.example.sulta.tplan.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

}
