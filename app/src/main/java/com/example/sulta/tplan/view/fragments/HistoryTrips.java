package com.example.sulta.tplan.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryTrips extends Fragment {

    ListView historyTripsList;
    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
    SqlAdapter db;
    ArrayList<Trip> doneTrips;

    public HistoryTrips() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_history_trips, container, false);
        historyTripsList = myView.findViewById(R.id.historyTrips);
        FloatingActionButton createTripBtn = myView.findViewById(R.id.createTripBtn);

        createTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CreateTripActivity.class);
//                getContext().startActivity(intent);
                //sulta

                //sulta
            }
        });
        homePresenter.viewHistoryTrips(getContext(), historyTripsList);
        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        homePresenter.viewHistoryTrips(getContext(), historyTripsList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SqlAdapter(getContext());
        if (savedInstanceState.get("doneTrips") != null) {
            doneTrips = (ArrayList<Trip>) savedInstanceState.get("doneTrips");
        } else {
            doneTrips = db.selectDoneTrips();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("doneTrips", doneTrips);

    }
}
