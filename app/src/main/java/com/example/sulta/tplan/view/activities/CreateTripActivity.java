package com.example.sulta.tplan.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.CreateTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.ICreateTripActivity;
import com.example.sulta.tplan.view.fragments.AddNoteFragmentDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    ImageButton imgBtn;
    int flag=0;

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
        imgBtn=(ImageButton)findViewById(R.id.imgBtn);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){
                    imgBtn.setBackgroundResource(R.drawable.tripdir2);
                    flag=2;
                }
                else{
                    imgBtn.setBackgroundResource(R.drawable.tripdir);
                    flag=0;
                }
            }
        });

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
                mCreateTripActivityPresenter.createTrip();
                mCreateTripActivityPresenter.startSerivice();
            }
        });
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentManager fm = getFragmentManager();
                AddNoteFragmentDialog dialogFragment = new AddNoteFragmentDialog ();
              //  dialogFragment.getActivity().getWindow().setLayout(400,400);
                dialogFragment.show(getFragmentManager(), "");
             //   yourDialog.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);


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

    @Override
    public String getTripDate() {
        //YYYY-MM-DD HH:MM:SS
        String date=datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+" "
                +timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute()+":00";
        Log.i(TAG,"Date: "+date);
        return date;
    }

    @Override
    public long getTripStartTimeInMillis() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String dateInString=datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+" "
                +timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute()+":00";

        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(dateInString);
        System.out.println("Date - Time in milliseconds : " + date.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    @Override
    public boolean getTripDirection() {
        if (flag==0){
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();

        mCreateTripActivityPresenter.stopService();

    }


}
