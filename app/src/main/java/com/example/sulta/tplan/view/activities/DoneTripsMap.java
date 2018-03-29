package com.example.sulta.tplan.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

public class DoneTripsMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SqlAdapter db;
    private ArrayList<Trip>doneTrips;
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("doneTrips", doneTrips);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_trips_map);

        //get all done trips
        db = new SqlAdapter(this);
        if (savedInstanceState.get("doneTrips") != null) {
            doneTrips = (ArrayList<Trip>) savedInstanceState.get("doneTrips");
        } else {
            doneTrips = db.selectDoneTrips();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void drawPath(PlacePoint startPoint, PlacePoint endPoint,String startPointName , String endPointName) {


        LatLng start = new LatLng(startPoint.getLatitude(), startPoint.getLongitude());
        LatLng end = new LatLng(endPoint.getLatitude(), endPoint.getLongitude());
        mMap.addMarker(new MarkerOptions().
                position(start).
                title(startPointName)).
                setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mMap.addMarker(new MarkerOptions().
                position(end).
                title(endPointName)).
                setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        int red=new Random().nextInt(255);int green=new Random().nextInt(255);int blue=new Random().nextInt(255);
        PolylineOptions polygonOptions=new PolylineOptions().add(start).add(end).color(Color.argb(1,red,green,blue)).width(5);
        mMap.addPolyline(polygonOptions);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (Trip x:doneTrips
                ) {

            drawPath( x.getStartPoint(),x.getEndPoint(),x.getStartPointName(),x.getEndPointName());

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.044281,31.340002),5));


    }

}
