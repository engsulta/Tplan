package com.example.sulta.tplan.view.utilities.Recievers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.view.utilities.Services.ReminderService;

public class RestartServiceReceiver extends BroadcastReceiver {
    ReminderService myService;
    boolean isBound=false;
    SqlAdapter db;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
             db=new SqlAdapter(context);
            Intent myintent=new Intent(context,ReminderService.class);
            context.bindService(intent,myconnection, Context.BIND_AUTO_CREATE);
            //get from database sql start time get upcoming start time and loop to start alarm with this dates
            //alarm.setAlarm(context,start_time);
           // ArrayList<Trip> trips=db.getalltrips();
            //for (Trip x:trips){


                 //   new Alarm().setAlarm(context,Long.parseLong(x.getAlarm()) ,x.getId());

           // }
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
        // ArrayList<Trip> trips=db.getalltrips();
        //for (Trip x:trips){


        //   new Alarm().setAlarm(context,Long.parseLong(x.getAlarm()) ,x.getId());
       // myService.startNewAlarm(this,x.getTimeInMillis(),10);//send request conde from trip id

        // }


    }

}
