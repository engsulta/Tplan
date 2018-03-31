package com.example.sulta.tplan.view.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.sulta.tplan.view.receivers.HandleReminder;


public class ReminderIntentService extends IntentService {

    public ReminderIntentService() {
        super("ReminderIntentService");
    }


    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            startNewAlarm(this, intent.getLongExtra("triptime", 0l), intent.getIntExtra("tripid", -1));


        }
    }

    public void startNewAlarm(Context context, Long startTime, int REQUEST_CODE) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HandleReminder.class);
        intent.putExtra("REQUEST_CODE", REQUEST_CODE);
        Toast.makeText(context, "alarm recoverd successfully", Toast.LENGTH_SHORT).show();
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, sender); // Millisec * Second * Minute
    }


}
