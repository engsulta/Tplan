package com.example.sulta.tplan.presenter;

import android.content.Context;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.database.SynchData;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.model.User;
import com.example.sulta.tplan.presenter.interfaces.ILoginActivityPresenter;
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
    private DatabaseReference databaseUsers ;
    private Context mcontext;
    private UserManager myUserManager;
    public LoginActivityPresenter(Context context){
        this.mcontext=context;
    }
    @Override
    public void creatUserInDb(UserManager userManager) {
        SynchData.getInstance(mcontext);


    }

    @Override
    public void removeUserInDb(UserManager myUserManager) {

    }

    @Override
    public void downloadTripsForUser() {
        final SqlAdapter db = new SqlAdapter(mcontext);
        FirebaseDatabase.getInstance().getReference().child("users").child(myUserManager.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    myUserManager.setDistancePerMonth(user.getDistancePerMonth());
                    myUserManager.setDurationPerMonth(user.getDurationPerMonth());
                    myUserManager.setTripsList(user.getTripsList());
                    if (user.getTripsList() != null) {
                        for (Trip t : user.getTripsList()
                                ) {
                            db.insertTrip(t);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
