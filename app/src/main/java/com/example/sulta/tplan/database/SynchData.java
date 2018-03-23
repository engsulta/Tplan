package com.example.sulta.tplan.database;

import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.activities.interfaces.IHomeActivity;
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
    private  final String DATABASE_REF_NAME = "trips";
    DatabaseReference databaseTrips;
    private IHomeActivity homerRef;


    public SynchData(IHomeActivity ref) {

        homerRef=(IHomeActivity) ref;
        databaseTrips= FirebaseDatabase.getInstance().getReference(DATABASE_REF_NAME);
    }

    public void deleteTrips() {

        databaseTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot: dataSnapshot.getChildren()
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
                for (DataSnapshot postsnapshot: dataSnapshot.getChildren()
                        ) {
                    //String key=postsnapshot.getKey();
                    postsnapshot.getRef().removeValue();

                }

                for (Trip trip:alltrips) {

                    String id= databaseTrips.push().getKey();
                    databaseTrips.child(id).setValue(trip);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Trip> downloadTrips(){

        final ArrayList<Trip>trips=new ArrayList<>();
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot:dataSnapshot.getChildren()
                     ) {

                    Trip trip=tripSnapshot.getValue(Trip.class);
                    trips.add(trip);

                }
                homerRef.setAllTrips(trips);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  trips;
    }

}

