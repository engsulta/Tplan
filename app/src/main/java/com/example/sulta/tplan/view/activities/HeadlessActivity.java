package com.example.sulta.tplan.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.Trip;

public class HeadlessActivity extends Activity implements View.OnClickListener {
    private Intent recievingIntent;
    private TextView textView;
    private TextView headlessTitle;
    private TextView headlessBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headless);
        Toast.makeText(this, "in headless", Toast.LENGTH_SHORT).show();
        int tripId = recievingIntent.getIntExtra("tripId", -1);
        Trip recievingTrip = (Trip) recievingIntent.getSerializableExtra("trip");
        textView = (TextView) findViewById(R.id.textView);
        headlessTitle = (TextView) findViewById(R.id.headless_title);
        headlessBody = (TextView) findViewById(R.id.headless_body);
        findViewById(R.id.editTripDetailsBtn).setOnClickListener(this);
        findViewById(R.id.deleteTripBtn).setOnClickListener(this);
        findViewById(R.id.shareTripDetailsBtn).setOnClickListener(this);

        headlessTitle.setText(recievingTrip.getTitle());
        headlessBody.setText(recievingTrip.getNotes());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTripDetailsBtn:
                //TODO implement
                break;
            case R.id.deleteTripBtn:
                //TODO implement
                break;
            case R.id.shareTripDetailsBtn:
                //TODO implement
                break;
        }
    }


}
