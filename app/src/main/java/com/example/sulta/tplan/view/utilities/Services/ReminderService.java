package com.example.sulta.tplan.view.utilities.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.sulta.tplan.view.utilities.Recievers.HandleReminder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReminderService extends Service {

    private final IBinder myBinder =new MyLocalBinder();


    /////
    public ReminderService() {

    }
    //////
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }//publish service here

    public void startNewAlarm(Context context,Long startTime,int REQUEST_CODE){
        AlarmManager alarmManager =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HandleReminder.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,startTime, sender); // Millisec * Second * Minute

    }
    public void EditAlarm(Context context,int REQUEST_CODE,Long startTime){
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        alarmManager.set(AlarmManager.RTC_WAKEUP,startTime, sender); // Millisec * Second * Minute

    }
    public void snoozeAlarm(Context context,int REQUEST_CODE,Long snoozTime){
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            alarmManager.wait(snoozTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void stopAlarm(Context context,int REQUEST_CODE){
        Intent intent = new Intent(context, HandleReminder.class);//there will be toast or alert told you that this alarm stoped
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);

    }
    public String getCurrentTime(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH::mm:ss MM/dd/yyyy", Locale.US);
        return dateFormat.format(new Date());
    }


    public class MyLocalBinder extends Binder {
        public ReminderService geService(){
            return ReminderService.this;
        }

    }
}
