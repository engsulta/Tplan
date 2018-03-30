package com.example.sulta.tplan.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.HomeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {


    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        final IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
        int settings = homePresenter.viewSettings(getContext());
        final Switch notificationSwitch = (Switch) myView.findViewById(R.id.notifications_switch);
        Button saveButton = (Button) myView.findViewById(R.id.savebtn);
        Button cancelButton = (Button) myView.findViewById(R.id.cancelbtn);

        if(settings==0){
            notificationSwitch.setChecked(false);
        } else{
            notificationSwitch.setChecked(true);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int settingsState = 1;
                if(notificationSwitch.isChecked()) {
                    settingsState = 0;
                }
                homePresenter.editSettings(getContext(),settingsState);
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.putExtra("TabFlag",2);
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                getContext().startActivity(intent);
                ( (Activity) getContext()).finish();
            }
        });
        return myView;
    }

}
