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
import com.example.sulta.tplan.presenter.EditTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.IEditTripActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity implements IEditTripActivity{
    EditTripActivityPresenter mEditTripActivityPresenter;
    PlaceAutocompleteFragment startPointFragment;
    PlaceAutocompleteFragment endPointFragment;
    EditText tripNameEdt;
    DatePicker datePicker;
    TimePicker timePicker;
    Button addNoteBtn,saveTripBtn,cancelBtn;
    String TAG="EditTripActivityLog";
    Place startPlace;
    Place endPlace;
    ImageButton imgBtn;
    int flag=0;
    ArrayList<TripNote> noteArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        mEditTripActivityPresenter=new EditTripActivityPresenter(this,this);
        tripNameEdt=(EditText)findViewById(R.id.editTrip_edt_name);
        datePicker=(DatePicker)findViewById(R.id.editTrip_datePicker);
        timePicker=(TimePicker)findViewById(R.id.editTrip_timePicker);
        addNoteBtn=(Button)findViewById(R.id.editTrip_button_note);
        saveTripBtn=(Button)findViewById(R.id.editTrip_button_create);
        startPointFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_start_edit);
        endPointFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_end_edit);
        imgBtn=(ImageButton)findViewById(R.id.imgBtn_edit);
        cancelBtn=(Button)findViewById(R.id.editTrip_button_cancel);

        mEditTripActivityPresenter.setData(getIntent());

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
        saveTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                if(tripNameEdt.getText().toString().trim()!=null&&
                        (calendar.getTimeInMillis()+1000)>=(System.currentTimeMillis()-1000)){
                    mEditTripActivityPresenter.editTrip();// mEditTripActivityPresenter.startSerivice();
                    finish();}
                else
                    Toast.makeText(EditTripActivity.this, "Please Check all data are submitted with upcoming date!", Toast.LENGTH_SHORT).show();
            }
        });
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EditTripActivity.this,AddNoteActivity.class);

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
        if (startPlace!=null)
            return startPlace.getLatLng().latitude;
        else
            return 0.0d;
    }

    @Override
    public double startPointLan() {
        if (startPlace!=null)
            return startPlace.getLatLng().longitude;
        else
            return 0.0d;
    }

    @Override
    public double endPointLan() {
        if (endPlace!=null)
            return endPlace.getLatLng().longitude;
        else
            return 0.0d;
    }

    @Override
    public double endPointLat() {
        if (endPlace!=null)
            return endPlace.getLatLng().latitude;
        else
            return 0.0d;
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
       if (startPlace!=null)
        return startPlace.getName().toString();
       else
           return "";
    }

    @Override
    public String getEndPointName() {
        if (endPlace!=null)
        return endPlace.getName().toString();
        else
            return "";
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
       // mEditTripActivityPresenter.stopService();
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

    @Override
    public void setName(String name) {
        tripNameEdt.setText(name);

    }

    @Override
    public void setDirection(Boolean dir) {
        if(dir==true){
            imgBtn.setBackgroundResource(R.drawable.tripdir2);
            Toast.makeText(EditTripActivity.this, "toot", Toast.LENGTH_SHORT).show();
            flag=2;
        }
        else {
            imgBtn.setBackgroundResource(R.drawable.tripdir);
            Toast.makeText(EditTripActivity.this, "toot2", Toast.LENGTH_SHORT).show();

            flag=0;
        }

    }

    @Override
    public void setStartPlace(String placeName) {
        startPointFragment.setText(placeName);
    }

    @Override
    public void setEndPlace(String placeName) {
        endPointFragment.setText(placeName);

    }

    @Override
    public void setDate(int year, int mon, int day) {
        datePicker.updateDate(year,mon,day);

    }

    @Override
    public void setTime(int hour, int min) {
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);

    }

    @Override
    public void setNotes(ArrayList<TripNote> noteArrayList) {
        this.noteArrayList=noteArrayList;

    }
}
