package com.example.sulta.tplan.view.utilities;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sulta.tplan.R;

/**
 * Created by Asmaa on 3/29/2018.
 */

public class TripNotesViewHolder {
    CheckBox checkedNote;
    EditText noteContent;
    Button deleteNoteButton;
    View contentView;

    public TripNotesViewHolder(View contentView) {
        this.contentView = contentView;
    }

    public CheckBox getCheckedNote() {
        if(checkedNote==null){
            checkedNote = (CheckBox) contentView.findViewById(R.id.checkedNote);
        }
        return checkedNote;
    }

    public EditText getNoteContent(){
        if(noteContent == null){
            noteContent = (EditText) contentView.findViewById(R.id.noteContent);
        }
        return noteContent;
    }

    public Button getDeleteNoteButton(){
        if(deleteNoteButton == null){
            deleteNoteButton =(Button) contentView.findViewById(R.id.deleteNoteButton);
        }
        return deleteNoteButton;
    }

}

