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

import java.util.Random;

public class TripMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SqlAdapter db;
    private Trip myTrip;
    private int[] colors = {Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.DKGRAY, Color.RED, Color.YELLOW};
    private float[] pincolors = {BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_ORANGE, BitmapDescriptorFactory.HUE_YELLOW};

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("myTrip", myTrip);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_map);
        int tripId = getIntent().getIntExtra("tripId", -1);

        //get all done trips
        db = new SqlAdapter(this);

        if (savedInstanceState != null) {
            myTrip = (Trip) savedInstanceState.getSerializable("myTrip");
        } else {
            if (tripId != -1) {
                myTrip = db.selectTripById(tripId);
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void drawPath(PlacePoint startPoint, PlacePoint endPoint, String startPointName, String endPointName) {

        LatLng start = new LatLng(startPoint.getLatitude(), startPoint.getLongitude());
        LatLng end = new LatLng(endPoint.getLatitude(), endPoint.getLongitude());
        int pathcolor = new Random().nextInt(colors.length);
        int pinColor = new Random().nextInt(pincolors.length);

        mMap.addMarker(new MarkerOptions().
                position(start).
                title(startPointName)).
                setIcon(BitmapDescriptorFactory.defaultMarker(pincolors[pinColor]));
        mMap.addMarker(new MarkerOptions().
                position(end).
                title(endPointName)).
                setIcon(BitmapDescriptorFactory.defaultMarker(pincolors[pinColor]));
        PolylineOptions polygonOptions = new PolylineOptions().add(start).add(end).color(colors[pathcolor]);
        mMap.addPolyline(polygonOptions);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        drawPath(myTrip.getStartPoint(), myTrip.getEndPoint(), myTrip.getStartPointName(), myTrip.getEndPointName());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myTrip.getStartPoint().getLatitude(),myTrip.getStartPoint().getLongitude()), 10));


    }
}
