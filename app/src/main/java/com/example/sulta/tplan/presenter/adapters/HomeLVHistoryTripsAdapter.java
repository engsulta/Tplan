package com.example.sulta.tplan.presenter.adapters;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.HeadlessActivity;
import com.example.sulta.tplan.view.activities.HomeActivity;
import com.example.sulta.tplan.view.services.ReminderService;
import com.example.sulta.tplan.view.utilities.HomeViewHolderHistoryList;

import java.util.List;
import java.util.Random;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeLVHistoryTripsAdapter extends ArrayAdapter {
    Context context;
    List<Trip> customList;
    ReminderService myService;
    int tripImages[] = {R.drawable.tripimage1, R.drawable.tripimage2, R.drawable.tripimage3, R.drawable.tripimage4, R.drawable.tripimage5,
            R.drawable.tripimage6};
    boolean isBound = false;
    private int pos;
    private int position1;

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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_list_history_trips_cardview, parent, false);
            viewHolder = new HomeViewHolderHistoryList(myView);
            myView.setTag(viewHolder);
        } else {
            viewHolder = (HomeViewHolderHistoryList) myView.getTag();
        }

        Random r = new Random();
        int randomNum = r.nextInt(tripImages.length);
        viewHolder.getTripLayout().setBackgroundResource(tripImages[randomNum]);
        viewHolder.getTripName().setText(customList.get(position).getTitle());
        viewHolder.getTripState().setText(customList.get(position).getStatus());

        viewHolder.getViewMapTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                homePresenter.viewMapTrip(context, customList.get(position));
            }
        });
        viewHolder.getDeleteTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                boolean result = homePresenter.deleteTrip(context, customList.get(position).getId());
                if (result) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("TabFlag", 1);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "Cannot be deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.getEditTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                customList.get(position1).setStatus("Done");
                homePresenter.editTrip(context, customList.get(position1));
                Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("TabFlag", 1);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

        viewHolder.getMoreOptionsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                showMoreOptionsMenu(v);
            }
        });
        viewHolder.getShareTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                homePresenter.shareTrip(context, customList.get(position));
            }
        });

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
                Intent intent = new
                        Intent(context, HeadlessActivity.class);
                intent.putExtra("tripId", customList.get(position).getId());
                context.startActivity(intent);
            }
        });

        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                position1 = position;
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                showListViewMenu(v);
                return true;
            }
        });
        return myView;
    }

    public void showListViewMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.viewTripDetails) {

                    Toast.makeText(context, "viewed", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.deleteTrip) {

                    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                    boolean result = homePresenter.deleteTrip(context, customList.get(position1).getId());
                    if (result) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("TabFlag", 1);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "Cannot be deleted", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.doneTrip) {

                    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                    customList.get(position1).setStatus("Done");
                    homePresenter.editTrip(context, customList.get(position1));
                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("TabFlag", 1);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                return true;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_listview_options, popup.getMenu());
        popup.show();
    }

    public void showMoreOptionsMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.startAfterMins) {
                    Toast.makeText(context, "Snoozed", Toast.LENGTH_SHORT).show();
                    stopService();
                    startSerivice();
                    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                    customList.get(pos).setStatus("upComing");
                    homePresenter.editTrip(context, customList.get(pos));
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("TabFlag", 1);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else if (item.getItemId() == R.id.repeatTrip) {
                    Toast.makeText(context, "Repeated", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_trip_options, popup.getMenu());
        popup.show();
    }

    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            snoozeAlarmSettings(customList.get(pos).getId());
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
        if (isBound) {
            context.stopService(new Intent(context, ReminderService.class));
            context.unbindService(myconnection);
            isBound = false;
        }
    }

    private void snoozeAlarmSettings(int tripId) {
        if (isBound) {
            Toast.makeText(myService, "alarm started", Toast.LENGTH_SHORT).show();
            myService.snoozeAlarm(context, tripId, 10 * 60 * 1000L);//send request conde from trip id
            stopService();
        }
    }

}