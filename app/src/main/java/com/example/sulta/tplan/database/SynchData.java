package com.example.sulta.tplan.database;

import android.content.Context;

import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.activities.interfaces.IHomeActivity;
import com.example.sulta.tplan.view.utilities.MySharedPrefManger;
import com.example.sulta.tplan.view.utilities.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by sulta on 3/23/2018.
 */

public class SynchData {
    // Write a message to the database
    private final String DATABASE_REF_NAME = "trips";
    private final String DATABASE_REF_Users = "users";
    DatabaseReference databaseTrips;
    DatabaseReference databaseUsers;
    private IHomeActivity homerRef;
    private static SynchData synchDataRef = null;
    private Context mcontext;
    private SynchData(Context context) {
         mcontext = context;
         databaseUsers = FirebaseDatabase.getInstance().getReference(DATABASE_REF_Users);
         creatUserInDb();
        }

    public static SynchData getInstance(Context context) {
        if (synchDataRef == null)
            synchDataRef = new SynchData(context);

        return synchDataRef;
    }

    private void creatUserInDb() {
        UserManager myuserManager=UserManager.getUserInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference(DATABASE_REF_Users);
        String id = databaseUsers.push().getKey();
        MySharedPrefManger.getInstance(mcontext).storeStringToken("UserPushId", id);
        databaseUsers.child(id).setValue(myuserManager);
    }

    private void createTripInDbForUser(String parent, Trip t) {
        databaseTrips = FirebaseDatabase.getInstance().getReference(DATABASE_REF_NAME).child(parent);
        String id =databaseTrips.push().getKey();
        databaseTrips.child(id).setValue(t);
    }
    private void createTripsInDbForUser(String parent, ArrayList<Trip> trips) {
        databaseTrips = FirebaseDatabase.getInstance().getReference(DATABASE_REF_NAME).child(parent);
        for (Trip t:trips
             ) {
            String id =databaseTrips.push().getKey();
            databaseTrips.child(id).setValue(t);
        }

    }

    private void deleteTripsForUser(String parentid) {
        databaseTrips = FirebaseDatabase.getInstance().getReference(DATABASE_REF_NAME).child(parentid);

        databaseTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot TripSnapshot : dataSnapshot.getChildren()
                        ) {
                    //String key=postsnapshot.getKey();
                    TripSnapshot.getRef().removeValue();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveTripsForUser(final ArrayList<Trip> alltrips) {
        databaseUsers = FirebaseDatabase.getInstance().getReference(DATABASE_REF_Users);
        final String id = MySharedPrefManger.getInstance(mcontext).getStringToken("UserPushId");
        deleteTripsForUser(id);
        createTripsInDbForUser(id,alltrips);
    }


    public ArrayList<Trip> downloadTripsForUser(){
        String parentid = MySharedPrefManger.getInstance(mcontext).getStringToken("UserPushId");
        databaseTrips = FirebaseDatabase.getInstance().getReference(DATABASE_REF_NAME).child(parentid);
        final ArrayList<Trip> trips = new ArrayList<>();
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()
                        ) {

                    Trip trip = tripSnapshot.getValue(Trip.class);
                    trips.add(trip);

                }
                homerRef.setAllTrips(trips);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return trips;

    }





























    public void deleteTrips() {

        databaseTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()
                        ) {
                    //String key=postsnapshot.getKey();
                    postsnapshot.getRef().removeValue();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void uploadTrips(final ArrayList<Trip> alltrips) {

        databaseTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()
                        ) {
                    //String key=postsnapshot.getKey();
                    postsnapshot.getRef().removeValue();

                }

                for (Trip trip : alltrips) {

                    String id = databaseTrips.push().getKey();
                    databaseTrips.child(id).setValue(trip);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Trip> downloadTrips() {

        final ArrayList<Trip> trips = new ArrayList<>();
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()
                        ) {

                    Trip trip = tripSnapshot.getValue(Trip.class);
                    trips.add(trip);

                }
                homerRef.setAllTrips(trips);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return trips;
    }

}

