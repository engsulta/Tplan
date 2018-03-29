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
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.services.ReminderService;

import java.util.Calendar;

public class AlarmTestActivity extends AppCompatActivity {
    ReminderService myService;
    boolean isBound = false;
    TimePicker watch;
    Button start;
    Calendar calendar;
    Trip testTrip;
    SqlAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);
        watch = (TimePicker) findViewById(R.id.watch);
        start = (Button) findViewById(R.id.buttonAlarm);
        // initialize trip empty for test only
        testTrip = new Trip();
        testTrip.setTitle("my first trip ");
        testTrip.setNotes("my notes ");
        testTrip.setStatus("upcoming");
        PlacePoint mystart = new PlacePoint();
        PlacePoint endpoint = new PlacePoint();
        mystart.setLatitude(29.971082);
        mystart.setLongitude(31.193361);
        endpoint.setLatitude(29.871082);
        endpoint.setLongitude(31.093361);
        testTrip.setStartPoint(mystart);
        testTrip.setEndPoint(endpoint);
        testTrip.setId(100);


        db = new SqlAdapter(this);
        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        watch.getHour(), watch.getMinute(), 0);
                testTrip.setStartTimeInMillis(calendar.getTimeInMillis());
                db.insertTrip(testTrip);
                startService();

            }

        });
    }

    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            setAlarmSetting();
            Toast.makeText(myService, "service bounded", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
            Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };

    private void setAlarmSetting() {
        if (isBound) {
            Toast.makeText(myService, "alarm started", Toast.LENGTH_SHORT).show();
            myService.startNewAlarm(this, testTrip.getStartTimeInMillis(), testTrip.getId());//send request conde from trip id
        }
    }

    private void startService() {
        Intent intent = new Intent(AlarmTestActivity.this, ReminderService.class);
        bindService(intent, myconnection, Context.BIND_AUTO_CREATE);
    }

    public void stopService() {
        this.stopService(new Intent(this, ReminderService.class));
        this.unbindService(myconnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            stopService();
            isBound = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isBound) {
            stopService();
            isBound = false;
        }
    }
}


