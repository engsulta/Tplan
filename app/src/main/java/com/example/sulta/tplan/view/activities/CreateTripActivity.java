package com.example.sulta.tplan.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.sulta.tplan.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class CreateTripActivity extends AppCompatActivity {
    PlaceAutocompleteFragment startPointFragment;
    PlaceAutocompleteFragment endPointFragment;
    EditText tripNameEdt;
    DatePicker datePicker;
    TimePicker timePicker;
    Button addNoteBtn,createTripBtn;
    String TAG="CreateTripActivityLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        tripNameEdt=(EditText)findViewById(R.id.createTrip_edt_name);
        datePicker=(DatePicker)findViewById(R.id.createTrip_datePicker);
        timePicker=(TimePicker)findViewById(R.id.createTrip_timePicker);
        addNoteBtn=(Button)findViewById(R.id.creatTrip_button_note);
        createTripBtn=(Button)findViewById(R.id.creatTrip_button_create);
        startPointFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_start);
        endPointFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_end);

        /*** change fragment design ***/
        startPointFragment.setHint("Start Point");
        ImageView searchIcon = (ImageView)((LinearLayout)startPointFragment.getView()).getChildAt(0);
        // Set the desired icon
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.pinicon));

        endPointFragment.setHint("End Point");
        searchIcon = (ImageView)((LinearLayout)endPointFragment.getView()).getChildAt(0);
        // Set the desired icon
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.pinicon));
        /*** end of change fragment design ***/

        startPointFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        endPointFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }
}
