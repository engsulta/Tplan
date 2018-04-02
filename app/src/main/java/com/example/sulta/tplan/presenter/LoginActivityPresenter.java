package com.example.sulta.tplan.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.model.User;
import com.example.sulta.tplan.presenter.interfaces.ILoginActivityPresenter;
import com.example.sulta.tplan.view.services.ReminderService;
import com.example.sulta.tplan.view.utilities.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Passant on 3/17/2018.
 */

public class LoginActivityPresenter implements ILoginActivityPresenter {

    private final String DATABASE_REF_Users = "users";
    private DatabaseReference databaseUsers;
    private Context mcontext;
    private UserManager myUserManager;
    ReminderService myService;
    boolean isBound = false;

    public LoginActivityPresenter(Context context) {
        this.mcontext = context;
    }

    @Override
    public void creatUserInDb(UserManager userManager) {


    }

    @Override
    public void removeUserInDb(UserManager myUserManager) {

    }


    @Override
    public void downloadTripsForUser() {
        final SqlAdapter db = new SqlAdapter(mcontext);
        myUserManager = UserManager.getUserInstance();
        FirebaseDatabase.getInstance().getReference().child("users").child(myUserManager.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    myUserManager.setDistancePerMonth(user.getDistancePerMonth());
                    myUserManager.setDurationPerMonth(user.getDurationPerMonth());
                    // myUserManager.setTripsList(user.getTripsList());
                    if (user.getTripsList() != null) {
                        for (Trip t : user.getTripsList()
                                ) {
                            db.insertTrip(t);
                        }
                    }
                    // Home refresh method
                    HomeActivityPresenter homeActivityPresenter = new HomeActivityPresenter();
                    homeActivityPresenter.refreshList(mcontext);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void startAllAlarms(Context context) {
        this.mcontext = context;
        if (!isBound) {
            Intent mintent = new Intent(context, ReminderService.class);
            context.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
        }else{stopService();}
    }

    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            myService.startAllAlarms(mcontext);//send request conde from trip id


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;

        }
    };

    public void stopService() {
        if (isBound) {
            mcontext.stopService(new Intent(mcontext, ReminderService.class));
            mcontext.unbindService(myconnection);
            isBound = false;
        }
    }
}
