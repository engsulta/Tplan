package com.example.sulta.tplan.view.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sulta.tplan.R;

/**
 * Created by Asmaa on 3/25/2018.
 */

public class AddNoteFragmentDialog extends DialogFragment {
    Button addBtn,button;
    LinearLayout mainLayout;
    LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_add_note, container, false);
        addBtn=rootView.findViewById(R.id.noteFragment_addBtn);
        mainLayout=rootView.findViewById(R.id.mainLayout);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckBox checkBox=new CheckBox(getActivity());
                EditText editText = new EditText(getActivity());
                editText.setWidth(450);
                button=new Button(getActivity());
                button.setText("Delete");

                linearLayout=new LinearLayout(getActivity());
                linearLayout.setGravity(Gravity.CENTER);
                final LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.addView(checkBox);
                linearLayout.addView(editText);
                linearLayout.addView(button);
                mainLayout.addView(linearLayout);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                           mainLayout.removeView((View)view.getParent());


                    }
                });


            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}
