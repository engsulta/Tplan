package com.example.sulta.tplan.presenter.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.sulta.tplan.model.TripNote;

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


}
