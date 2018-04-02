package com.example.sulta.tplan.view.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by sulta on 4/2/2018.
 */

public class MyServiceConnection {
    private static MyServiceConnection serviceConnectionInstace;
    private Context mcontext;
    private ReminderService myService;
    private Boolean isBound;


    private MyServiceConnection() {

    }

    public static MyServiceConnection getInstance() {
        if (serviceConnectionInstace == null) {
            serviceConnectionInstace = new MyServiceConnection();
        }
        return serviceConnectionInstace;
    }

    private android.content.ServiceConnection myconnection = new android.content.ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;

            myService = binder.geService();
            isBound = true;
            // myService.stopAllAlarms(mcontext);//send request conde from trip id


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
            Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };

    public ReminderService getMyService() {
        return myService;
    }

    public Boolean getBound() {
        return isBound;
    }

    public void unBind() {
        if (isBound) {
            mcontext.stopService(new Intent(mcontext, ReminderService.class));
            mcontext.unbindService(myconnection);
            isBound = false;
        }
    }

    public void bind(Context context) {
        if (!isBound) {
            this.mcontext=context;
            Intent mintent = new Intent(context, ReminderService.class);
            context.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
        }
    }
}
