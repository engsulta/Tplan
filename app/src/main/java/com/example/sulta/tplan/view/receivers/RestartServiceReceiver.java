package com.example.sulta.tplan.view.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.services.ReminderIntentService;
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
            ArrayList<Trip> trips=db.selectAllTrips();//blocking task
            Log.i("tplan", "onReceive: "+trips.size());
            for (Trip x:trips) {

                Log.i("tplan", "onReceive: "+x.getTitle());
                Log.i("tplan", "onReceive: "+x.getStartTimeInMillis());
                if(System.currentTimeMillis()-1000< x.getStartTimeInMillis()+1000) {
                    Intent myintent=new Intent(context, ReminderIntentService.class);
                    myintent.putExtra("triptime",x.getStartTimeInMillis());
                    myintent.putExtra("tripid",x.getId());
                    context.startService(myintent);
                } else {

                    x.setStatus("missed");
                    Toast.makeText(context, "your trip"+x.getTitle()+"is missed", Toast.LENGTH_SHORT).show();
                    db.updateTrip(x);

                    //
                }
                }

           // context.bindService(intent,myconnection, Context.BIND_AUTO_CREATE);

        }

    }
//    private ServiceConnection myconnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            ReminderService.MyLocalBinder binder=(ReminderService.MyLocalBinder) iBinder;
//            myService = binder.geService();
//            isBound=true;
//            restartAlarmSetting();
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            isBound=false;
//        }
//    };
//    private void restartAlarmSetting() {
//         ArrayList<Trip> trips=db.selectAllTrips();//blocking task
//        for (Trip x:trips) {
//            if(System.currentTimeMillis()<x.getStartTimeInMillis()) {
//                myService.startNewAlarm(this.context,  x.getStartTimeInMillis(), x.getId());//send request conde from trip id
//            }
//            else {
//                //edit trip to make status past
//                x.setStatus("missed");
//                Toast.makeText(context, "your trip"+x.getTitle()+"is missed", Toast.LENGTH_SHORT).show();
//                db.updateTrip(x);
//
//                //
//            }
//
//        }
//
//    }


}
