package com.example.sulta.tplan.presenter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.HomeActivity;
import com.example.sulta.tplan.view.utilities.HomeViewHolderHistoryList;

import java.util.List;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeLVHistoryTripsAdapter extends ArrayAdapter {
    Context context;
    List<Trip> customList;
    public HomeLVHistoryTripsAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List androidSets) {
        super(context, resource, textViewResourceId, androidSets);
        this.context = context;
        this.customList = androidSets;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View myView = convertView;
        final HomeViewHolderHistoryList viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_list_history_trips_cardview,parent,false);
            viewHolder = new HomeViewHolderHistoryList(myView);
            myView.setTag(viewHolder);
        } else{
            viewHolder = (HomeViewHolderHistoryList) myView.getTag();
        }

        viewHolder.getTripName().setText(customList.get(position).getTitle());
        viewHolder.getTripState().setText(customList.get(position).getStatus());
        viewHolder.getTripDirection().setBackgroundResource(R.drawable.item_list_upcoming_twoways_24dp);
        viewHolder.getDeleteTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                boolean result = homePresenter.deleteTrip(context,customList.get(position).getId());
                if(result){
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("TabFlag",1);
                    context.startActivity(intent);
                    ( (Activity) context).finish();
                } else{
                    Toast.makeText(context, "Cannot be deleted", Toast.LENGTH_SHORT).show();
                }
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
        viewHolder.getShareTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comingTrip="Upcoming Trip\n Trip Name: "+customList.get(position).getTitle()+"\n"+
                        " Trip Date: "+customList.get(position).getDate()+"\n"+
                        " Trip Duration: "+customList.get(position).getDuration()+"\n"+
                        " Trip Distance: "+customList.get(position).getDistance()+"\n"+
                        " From: "+customList.get(position).getStartPointName()+"\n"+
                        " To: "+customList.get(position).getEndPointName()+"\n"+
                        " Trip Notes: "+customList.get(position).getNotes();
                Intent sendintent = new Intent();
                sendintent.setAction(Intent.ACTION_SEND);
                sendintent.putExtra(Intent.EXTRA_TEXT,comingTrip);
                sendintent.setType("text/plain");
                context.startActivity(sendintent);
            }
        });

        notifyDataSetChanged();
        notifyDataSetInvalidated();
        return myView;
    }
}