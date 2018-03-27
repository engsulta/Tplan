package com.example.sulta.tplan.view.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.services.ReminderService;

import java.util.ArrayList;

public class RestartServiceReceiver extends BroadcastReceiver {
    private ReminderService myService;
    private boolean isBound=false;
    private SqlAdapter db;
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {

            this.context=context;
             db=new SqlAdapter(context);
            Intent myintent=new Intent(context,ReminderService.class);
            context.bindService(intent,myconnection, Context.BIND_AUTO_CREATE);

        }

    }
    private ServiceConnection myconnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder=(ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            restartAlarmSetting();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
        }
    };
    private void restartAlarmSetting() {
         ArrayList<Trip> trips=db.selectAllTrips();
        /*for (Trip x:trips) {
            if(System.currentTimeMillis()<x.startTimeMillis) {
                myService.startNewAlarm(this.context, (long) x.getstartTimeMillis(), x.getId());//send request conde from trip id
            }
            else {
                //edit trip to make status past
                x.setStatus("missed");

                //
            }

        }*/

    }

}
