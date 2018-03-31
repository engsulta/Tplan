package com.example.sulta.tplan.view.services;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.receivers.HandleReminder;
import com.example.sulta.tplan.view.utilities.MySharedPrefManger;

import java.util.ArrayList;

public class ReminderService extends Service {

    private final IBinder myBinder = new MyLocalBinder();
    private MySharedPrefManger sharedPrefManger;
    private final String FIRSTTRIPID_KEY = "firsttripid";
    private final String FIRSTTRIPTIME_KEY = "firsttriptime";

    private int firstStartTripId = -1;
    private String firstStartTripTime;

    private final String TAG = "tplan";
    private SqlAdapter db;
    private Context context;

    /////
    public ReminderService() {
    }

    //////
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }//publish service here

    public void startNewAlarm(Context context, Long startTime, int REQUEST_CODE) {
        this.context = context;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HandleReminder.class);
        Toast.makeText(context, "alarm started successfully", Toast.LENGTH_SHORT).show();
        intent.putExtra("REQUEST_CODE", REQUEST_CODE);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute
        ((Activity) context).finish();
    }


    public void EditAlarm(Context context, int REQUEST_CODE, Long startTime) {
        this.context = context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        intent.putExtra("REQUEST_CODE", REQUEST_CODE);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute
        // storeFirstUpcomingTrip(REQUEST_CODE,startTime);
        Toast.makeText(context, "alarm edited successfully", Toast.LENGTH_SHORT).show();
        ((Activity) context).finish();
    }

    public void snoozeAlarm(Context context, int REQUEST_CODE, Long snoozTime) {
        this.context = context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        intent.putExtra("REQUEST_CODE", REQUEST_CODE);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozTime, sender);

    }


    public void stopAlarm(Context context, int REQUEST_CODE) {
        this.context = context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        Toast.makeText(context, "alarm stoped successfully", Toast.LENGTH_SHORT).show();
        ((Activity) context).finish();

    }

    public void stopAllAlarms(Context context) {
        this.context = context;
        ArrayList<Trip> upcomingtrips = db.selectUpComingTrips();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Trip t : upcomingtrips
                ) {
            int REQUEST_CODE = t.getId();
            Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
            PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
            alarmManager.cancel(sender);


        }
        Toast.makeText(context, "alarm stoped successfully", Toast.LENGTH_SHORT).show();
        ((Activity) context).finish();

    }

    public void startAllAlarms(Context context) {
        this.context = context;
        ArrayList<Trip> upcomingtrips = db.selectUpComingTrips();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Trip t : upcomingtrips
                ) {
            int REQUEST_CODE = t.getId();
            Long startTime = t.getStartTimeInMillis();
            if (startTime > System.currentTimeMillis() + 1000) {
                Intent intent = new Intent(context, HandleReminder.class);
                intent.putExtra("REQUEST_CODE", REQUEST_CODE);
                PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute

            }
        }
        Toast.makeText(context, "alarm restarted successfully", Toast.LENGTH_SHORT).show();
        ((Activity) context).finish();

    }

    public class MyLocalBinder extends Binder {
        public ReminderService geService() {
            return ReminderService.this;
        }

    }

}
