package com.example.sulta.tplan.view.activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.presenter.CreateTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.ICreateTripActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTripActivity extends AppCompatActivity implements ICreateTripActivity {

    CreateTripActivityPresenter mCreateTripActivityPresenter;
    PlaceAutocompleteFragment startPointFragment;
    PlaceAutocompleteFragment endPointFragment;
    EditText tripNameEdt;
    DatePicker datePicker;
    TimePicker timePicker;
    Button addNoteBtn,createTripBtn,cancelBtn;
    String TAG="CreateTripActivityLog";
    Place startPlace;
    Place endPlace;
    ImageButton imgBtn;
    int flag=0;
    ArrayList<TripNote> noteArrayList;

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
        cancelBtn=(Button)findViewById(R.id.creatTrip_button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(noteArrayList==null)
            noteArrayList=new ArrayList<>();

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
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                if(tripNameEdt.getText().toString().trim()!=null&&startPlace!=null&&endPlace!=null&&
                        (calendar.getTimeInMillis()+1000)>=(System.currentTimeMillis()-1000)){
                mCreateTripActivityPresenter.createTrip();
                mCreateTripActivityPresenter.startSerivice();
                finish();
                }
                else
                    Toast.makeText(CreateTripActivity.this, "Please Check all data are submitted with upcoming date!", Toast.LENGTH_SHORT).show();
            }
        });
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CreateTripActivity.this,AddNoteActivity.class);

                intent.putParcelableArrayListExtra("SendNote",noteArrayList);
                // Set the request code to any code you like, you can identify the
                // callback via this code
                startActivityForResult(intent, 10);


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
       Calendar calendar = Calendar.getInstance();
       calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public String getStartPointName() {
        Log.i(TAG, "getStartPointName: "+startPlace.getName());
        Log.i(TAG, "getStartPointName: "+startPlace.getAddress());
        return startPlace.getName().toString();
    }

    @Override
    public String getEndPointName() {
        return endPlace.getName().toString();
    }

    @Override
    public ArrayList<TripNote> getNotes() {
        return noteArrayList;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10) {
            if (data.hasExtra("returnNotes")) {
                noteArrayList=data.getExtras().getParcelableArrayList("returnNotes");
                ArrayList<TripNote>temp=new ArrayList<>();

                for(int i=0;i<noteArrayList.size();i++) {
                    if (noteArrayList.get(i).getText()!=null){
                        if(noteArrayList.get(i).getText().trim()!=null) {
                            temp.add(noteArrayList.get(i));
                        }
                    }
                }
                noteArrayList=temp;
            }
        }
    }
}
