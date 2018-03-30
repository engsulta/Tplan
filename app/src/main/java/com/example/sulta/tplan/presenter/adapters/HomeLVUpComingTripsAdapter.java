package com.example.sulta.tplan.presenter.adapters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
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
import com.example.sulta.tplan.view.services.ReminderService;
import com.example.sulta.tplan.view.utilities.HomeViewHolderUpComingList;

import java.util.List;
import java.util.Random;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeLVUpComingTripsAdapter extends ArrayAdapter {
    Context context;
    List<Trip> customList;
    ReminderService myService;
    int tripImages[] = {R.drawable.tripimage1, R.drawable.tripimage2, R.drawable.tripimage3, R.drawable.tripimage4, R.drawable.tripimage5,
        R.drawable.tripimage6};
    boolean isBound = false;

    private  int pos;
    public HomeLVUpComingTripsAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List androidSets) {
        super(context, resource, textViewResourceId, androidSets);
        this.context = context;
        this.customList = androidSets;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View myView = convertView;
        HomeViewHolderUpComingList viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_list_upcoming_trips_cardview,parent,false);
            viewHolder = new HomeViewHolderUpComingList(myView);
            myView.setTag(viewHolder);
        } else{
            viewHolder = (HomeViewHolderUpComingList) myView.getTag();
        }

        Random r = new Random();
        int randomNum = r.nextInt(tripImages.length);
        viewHolder.getTripLayout().setBackgroundResource(tripImages[randomNum]);
        viewHolder.getTripName().setText(customList.get(position).getTitle());
        viewHolder.getCancelTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customList.get(position).setStatus("Cancelled");
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                boolean result = homePresenter.editTrip(context,customList.get(position));
                if(result){
                    pos=position;

                    stopService();
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    startSerivice();
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                   // ( (Activity) context).finish();
                } else{
                    Toast.makeText(context, "Cannot be cancelled", Toast.LENGTH_SHORT).show();
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

        viewHolder.getSeeTripDirectionBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "see direction", Toast.LENGTH_SHORT).show();
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

        return myView;
    }
    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            cancelAlarmSetting(customList.get(pos).getId());
            //Toast.makeText(myService, "service bounded", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
           // Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };


    public void startSerivice() {
        Intent mintent = new Intent(context, ReminderService.class);
        context.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
    }


    public void stopService() {
        if(isBound){
            context.stopService(new Intent(context, ReminderService.class));
            context.unbindService(myconnection);
            isBound=false;
        }
    }

    private void cancelAlarmSetting(int  tripId) {
        if (isBound) {
            Toast.makeText(myService, "alarm started", Toast.LENGTH_SHORT).show();
            myService.stopAlarm(context, tripId);//send request conde from trip id
        }
    }
}