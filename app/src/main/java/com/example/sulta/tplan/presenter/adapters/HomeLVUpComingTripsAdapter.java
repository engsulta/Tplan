package com.example.sulta.tplan.presenter.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.utilities.HomeCustomeItemUpComingList;
import com.example.sulta.tplan.view.utilities.HomeViewHolderList;

import java.util.List;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeLVUpComingTripsAdapter extends ArrayAdapter {
    Context context;
    List<HomeCustomeItemUpComingList> customList;
    public HomeLVUpComingTripsAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List androidSets) {
        super(context, resource, textViewResourceId, androidSets);
        this.context = context;
        this.customList = androidSets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView = convertView;
        HomeViewHolderList viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_list_upcoming_trips,parent,false);
            viewHolder = new HomeViewHolderList(myView);
            myView.setTag(viewHolder);
        } else{
            viewHolder = (HomeViewHolderList) myView.getTag();
        }

        viewHolder.getTripName().setText(customList.get(position).getTripName().toString());
        viewHolder.getCancelTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.getEditTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.getViewTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "view", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }
}