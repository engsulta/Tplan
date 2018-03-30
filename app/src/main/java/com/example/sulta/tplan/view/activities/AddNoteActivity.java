package com.example.sulta.tplan.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.presenter.adapters.TripNotesAdapter;

import java.util.ArrayList;

public class AddNoteActivity extends Activity {

    ListView listView;
    Button addNoteBtn;
    ArrayList<TripNote> tripNoteArrayList;
    TripNotesAdapter tripNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView=(ListView)findViewById(R.id.noteLV);
        addNoteBtn=(Button)findViewById(R.id.noteFragment_addBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tripNoteArrayList=extras.getParcelableArrayList("SendNote");
        }else {
            tripNoteArrayList = new ArrayList<>();

        }


        tripNotesAdapter=new TripNotesAdapter(getBaseContext(),R.layout.item_trip_note_row,R.id.noteContent,tripNoteArrayList);
        listView.setAdapter(tripNotesAdapter);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripNote tripNote=new TripNote();
                tripNoteArrayList.add(tripNote);
                tripNotesAdapter=new TripNotesAdapter(getBaseContext(),R.layout.item_trip_note_row,R.id.noteContent,tripNoteArrayList);
                listView.setAdapter(tripNotesAdapter);

            }
        });
    }
    @Override
    public void finish() {
        TripNote tripNote=new TripNote();
        tripNoteArrayList.add(tripNote);
        tripNotesAdapter=new TripNotesAdapter(getBaseContext(),R.layout.item_trip_note_row,R.id.noteContent,tripNoteArrayList);
        listView.setAdapter(tripNotesAdapter);

        Intent data = new Intent();
        // Return some hard-coded values
        data.putParcelableArrayListExtra("returnNotes",tripNoteArrayList);
        setResult(RESULT_OK, data);
        super.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
