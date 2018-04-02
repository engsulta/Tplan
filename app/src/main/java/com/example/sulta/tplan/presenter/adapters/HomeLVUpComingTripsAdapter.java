package com.example.sulta.tplan.presenter.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.HomeActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IHomeActivityPresenter;
import com.example.sulta.tplan.view.activities.EditTripActivity;
import com.example.sulta.tplan.view.activities.HeadlessActivity;
import com.example.sulta.tplan.view.activities.HomeActivity;
import com.example.sulta.tplan.view.services.ReminderService;
import com.example.sulta.tplan.view.utilities.HomeViewHolderUpComingList;
import com.example.sulta.tplan.view.utilities.MyNotificationManager;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeLVUpComingTripsAdapter extends ArrayAdapter implements  LocationListener{
    Context context;
    List<Trip> customList;
    ReminderService myService;
    int tripImages[] = {R.drawable.tripimage1, R.drawable.tripimage2, R.drawable.tripimage3, R.drawable.tripimage4, R.drawable.tripimage5,
        R.drawable.tripimage6};
    boolean isBound = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 1;
    private LocationManager locmgr;
    private  int pos;
    private int position1;
    private SqlAdapter db;
    private MyNotificationManager myNotificationManager;
    private double longitude;
    private double latitude;

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
                    ( (Activity) context).finish();
                } else{
                    Toast.makeText(context, "Cannot be cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.getEditTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //customList.get(position);
                //here goto intent
                Intent intent=new Intent(context, EditTripActivity.class);
                intent.putExtra("trip", (Parcelable) customList.get(position));
                //Toast.makeText(context, "adapter"+customList.get(position).isRoundTrip(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

        viewHolder.getViewTripMapBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                homePresenter.viewMapTrip(context,customList.get(position));
            }
        });

        viewHolder.getStartTripBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
                locmgr = (LocationManager) context.getSystemService((Context.LOCATION_SERVICE));
                myNotificationManager = MyNotificationManager.getInstance();
                //Intent intent=new Intent()
                pos=position;
                ((Activity)context).finish();
                customList.get(pos).setStatus("Done");
                db=new SqlAdapter(context);
                db.updateTrip( customList.get(pos));
                getCurrentLocation();
            }
        });

        viewHolder.getShareTripDetailsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                homePresenter.shareTrip(context,customList.get(position));
            }
        });

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new
                        Intent(context, HeadlessActivity.class);
                intent.putExtra("tripId",customList.get(position).getId());
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

    public void showListViewMenu(View v)
    {
        PopupMenu popup = new PopupMenu(getContext(),v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.viewTripDetails) {
                    Intent intent=new
                            Intent(context, HeadlessActivity.class);
                    intent.putExtra("tripId",customList.get(position1).getId());
                    context.startActivity(intent);
                } else if(item.getItemId() == R.id.deleteTrip){

                    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                    boolean result = homePresenter.deleteTrip(context,customList.get(position1).getId());
                    if(result){
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("TabFlag",0);
                        context.startActivity(intent);
                        ( (Activity) context).finish();
                    } else{
                        Toast.makeText(context, "Cannot be deleted", Toast.LENGTH_SHORT).show();
                    }

                } else if(item.getItemId() == R.id.doneTrip){

                    IHomeActivityPresenter homePresenter = new HomeActivityPresenter();
                    customList.get(position1).setStatus("Done");
                    homePresenter.editTrip(context,customList.get(position1));
                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("TabFlag",0);
                    context.startActivity(intent);
                    ( (Activity) context).finish();
                }
                return true;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_listview_options, popup.getMenu());
        popup.show();
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
            myService.stopAlarm(context, tripId);//send request conde from trip id
        }
    }
    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)context),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(((Activity)context),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_LOCATION
                );

            }
        } else {
            Location location = locmgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
                //
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                myNotificationManager.CancelNotification(context, pos);
                ((Activity)context).finish();
                Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&daddr=" + customList.get(pos).getEndPoint().getLatitude() + customList.get(pos).getEndPoint().getLongitude()));
                context.startActivity(mintent);
            } else {
                locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    locmgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();

                            Toast.makeText(context, "picked your location successfully", Toast.LENGTH_SHORT).show();
                            // getCompleteAddressString(location.getLatitude(), location.getLongitude());
                            Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?saddr=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&daddr=" + customList.get(pos).getEndPoint().getLatitude() + customList.get(pos).getEndPoint().getLongitude()));
                            context.startActivity(mintent);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)context),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                    }
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            myNotificationManager.CancelNotification(context, pos);
            ((Activity)context).finish();
            Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&daddr=" + customList.get(pos).getEndPoint().getLatitude() + customList.get(pos).getEndPoint().getLongitude()));
            context.startActivity(mintent);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }



}