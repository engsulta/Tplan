package com.example.sulta.tplan.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.CreateTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.ICreateTripActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class CreateTripActivity extends AppCompatActivity implements ICreateTripActivity {
    CreateTripActivityPresenter mCreateTripActivityPresenter;
    PlaceAutocompleteFragment startPointFragment;
    PlaceAutocompleteFragment endPointFragment;
    EditText tripNameEdt;
    DatePicker datePicker;
    TimePicker timePicker;
    Button addNoteBtn,createTripBtn;
    String TAG="CreateTripActivityLog";
    Place startPlace;
    Place endPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        mCreateTripActivityPresenter=new CreateTripActivityPresenter(this,this);

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
                startPlace=place;

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
                endPlace=place;

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        createTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.i(TAG, String.valueOf(timePicker.getCurrentHour()));
                Log.i(TAG, String.valueOf(timePicker.getCurrentMinute()));
                Date date=new Date();
                date.setMonth(datePicker.getMonth());
                date.setDate(datePicker.getDayOfMonth());
                date.setYear(datePicker.getYear());
                date.setHours(timePicker.getCurrentHour());
                date.setMinutes(timePicker.getCurrentMinute());
                date.setSeconds(0);

                String date_s = " 2011-01-18 00:00:00.0";
                SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
                try {
                    Date date1 = dt.parse(date_s);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "onClick: "+date);*/
                mCreateTripActivityPresenter.createTrip();
            }
        });


    }

    @Override
    public String getTripName() {
        return tripNameEdt.getText().toString();
    }

    @Override
    public double startPointLat() {
        return startPlace.getLatLng().latitude;
    }

    @Override
    public double startPointLan() {
        return startPlace.getLatLng().longitude;
    }

    @Override
    public double endPointLan() {
        return endPlace.getLatLng().longitude;
    }

    @Override
    public double endPointLat() {
        return endPlace.getLatLng().latitude;
    }
}
