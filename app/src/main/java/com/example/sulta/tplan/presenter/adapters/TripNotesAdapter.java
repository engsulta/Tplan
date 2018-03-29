package com.example.sulta.tplan.presenter.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.view.utilities.TripNotesViewHolder;

import java.util.List;

/**
 * Created by Asmaa on 3/29/2018.
 */

public class TripNotesAdapter extends ArrayAdapter {

    Context context;
    List<TripNote> notesList;
    public TripNotesAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List androidSets) {
        super(context, resource, textViewResourceId, androidSets);
        this.context = context;
        this.notesList = androidSets;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView = convertView;
        final TripNotesViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_trip_note_row,parent,false);
            viewHolder = new TripNotesViewHolder(myView);
            myView.setTag(viewHolder);
        } else{
            viewHolder = (TripNotesViewHolder) myView.getTag();
        }

        viewHolder.getCheckedNote().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.getCheckedNote().isChecked()){
                    notesList.get(position).setChecked(true);
                } else{
                    notesList.get(position).setChecked(false);
                }
            }
        });
        viewHolder.getNoteContent().setText(notesList.get(position).getText());
        viewHolder.getDeleteNoteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesList.remove(position);
            }
        });
        return myView;
    }
}
