package com.example.sulta.tplan.view.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sulta.tplan.R;

/**
 * Created by Asmaa on 3/25/2018.
 */

public class AddNoteFragmentDialog extends DialogFragment {
    Button addBtn;
    LinearLayout mainLayout;
   String TAG="AddNoteFragmentDialog";
    String note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_add_note, container, false);
        addBtn=rootView.findViewById(R.id.noteFragment_addBtn);
        mainLayout=rootView.findViewById(R.id.mainLayout);
        if(savedInstanceState==null) {
            note="anaaa";
            Toast.makeText(getActivity(),"First: "+ note, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "saved: "+ savedInstanceState.getString("test"), Toast.LENGTH_SHORT).show();

        }
        show(getFragmentManager(),"");
        Log.i(TAG, "onCreateView: "+note);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
        Log.i(TAG, "onResume: ");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("test",note);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored: ");


    }


}
