package com.example.sulta.tplan.view.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.services.ReminderService;

import java.util.Calendar;

public class AlarmTestActivity extends AppCompatActivity {
    ReminderService myService;
    boolean isBound = false;
    TimePicker watch;
    Button start;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);
        watch = (TimePicker) findViewById(R.id.watch);
        start = (Button) findViewById(R.id.buttonAlarm);
        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                    calendar = Calendar.getInstance();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            watch.getHour(), watch.getMinute(), 0);
                    Intent intent = new Intent(AlarmTestActivity.this, ReminderService.class);
                    bindService(intent, myconnection, Context.BIND_AUTO_CREATE);

                }

        });
    }

    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            setAlarmSetting();
            Toast.makeText(myService, "service bounded", Toast.LENGTH_SHORT).show();

            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    private void setAlarmSetting() {

            Toast.makeText(myService, "alarm started", Toast.LENGTH_SHORT).show();
            myService.startNewAlarm(this, calendar.getTimeInMillis(), 10);//send request conde from trip id

    }



}


