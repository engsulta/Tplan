package com.example.sulta.tplan.view.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

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
        this.context=context;
        sharedPrefManger = MySharedPrefManger.getInstance(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HandleReminder.class);
        Log.i(TAG, "startNewAlarm: " + firstStartTripId);
        storeFirstUpcomingTrip(REQUEST_CODE,startTime);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute

    }



    public void EditAlarm(Context context, int REQUEST_CODE, Long startTime) {
        this.context=context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute
        storeFirstUpcomingTrip(REQUEST_CODE,startTime);
    }

    public void snoozeAlarm(Context context, int REQUEST_CODE, Long snoozTime) {
        this.context=context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            alarmManager.wait(snoozTime);
            storeFirstUpcomingTrip(REQUEST_CODE,snoozTime);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void stopAlarm(Context context, int REQUEST_CODE) {
        this.context=context;
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);


    }




    public class MyLocalBinder extends Binder {
        public ReminderService geService() {
            return ReminderService.this;
        }

    }
    private void storeFirstUpcomingTrip(int REQUEST_CODE,Long startTime) {

        db=new SqlAdapter(this.context);
        ArrayList<Trip> trips= db.selectAllTrips();
//        // long minTime=trips[0].getStartTimeMillis;
//        //int firstTripId=-1;
//        for (Trip t:trips
//                ) {
//            //if( minTime>t.getStartTimeMillis()){minTime=t.getStartTimeMillis;firstTripId=t.getId();}
//        }
        firstStartTripId = sharedPrefManger.getIntToken(FIRSTTRIPID_KEY);
        firstStartTripTime = sharedPrefManger.getStringToken(FIRSTTRIPTIME_KEY);
        if (firstStartTripId < 0 || firstStartTripTime == null) {
            sharedPrefManger.storeIntToken(FIRSTTRIPID_KEY, REQUEST_CODE);
            sharedPrefManger.storeStringToken(FIRSTTRIPTIME_KEY, String.valueOf(startTime));
        } else {
            if (Long.parseLong(firstStartTripTime) > startTime) {
                firstStartTripTime = String.valueOf(startTime);
                sharedPrefManger.storeStringToken("FIRSTTRIPTIME_KEY", firstStartTripTime);
                sharedPrefManger.storeIntToken(FIRSTTRIPID_KEY, REQUEST_CODE);

            }
        }
    }
}
