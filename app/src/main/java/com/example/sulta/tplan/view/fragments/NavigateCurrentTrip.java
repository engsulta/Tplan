package com.example.sulta.tplan.view.fragments;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.sulta.tplan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NavigateCurrentTrip extends FragmentActivity implements OnMapReadyCallback, LocationListener,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Intent intent;
    private LatLng startpoint;
    private LatLng endpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_current_trip);
        intent=getIntent();
         startpoint=intent.getParcelableExtra("startpoint");
         endpoint=intent.getParcelableExtra("endpoint");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(  startpoint).title("start Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng( startpoint));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startpoint, 10.0f));

        //
        mMap.addMarker(new MarkerOptions().position(  endpoint).title("end Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng( endpoint));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endpoint, 10.0f));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}
