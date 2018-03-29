package com.example.sulta.tplan.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.sulta.tplan.R;

public class AddNoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
        

    }
}
