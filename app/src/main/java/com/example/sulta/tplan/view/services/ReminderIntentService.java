package com.example.sulta.tplan.view.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


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



        }
    }





}
