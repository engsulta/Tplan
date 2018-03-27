package com.example.sulta.tplan.view.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.sulta.tplan.R;

import java.util.ArrayList;

/**
 * Created by Asmaa on 3/25/2018.
 */

public class AddNoteFragmentDialog extends DialogFragment {
    Button addBtn,button;
    LinearLayout mainLayout;
    LinearLayout linearLayout;
    EditText editText;
    ArrayList<String> noteList;
    Boolean flag=false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_add_note, container, false);
        addBtn=rootView.findViewById(R.id.noteFragment_addBtn);
        mainLayout=rootView.findViewById(R.id.mainLayout);
        noteList=new ArrayList<>();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=true;
                final CheckBox checkBox=new CheckBox(getActivity());
                editText = new EditText(getActivity());
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
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {

                            //Toast.makeText(getActivity(), ((EditText)view).getText(), Toast.LENGTH_SHORT).show();


                            noteList.add(((EditText)view).getText().toString());
                            checkBox.setId(noteList.size()-1);

                          //  Toast.makeText(getActivity(), noteList.get(0), Toast.LENGTH_SHORT).show();


                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                        if(checkBox.isChecked()){
                            Toast.makeText(getActivity(), noteList.get(checkBox.getId()), Toast.LENGTH_SHORT).show();
                            noteList.add(checkBox.getId(),'*'+noteList.get(checkBox.getId()));
                        }
                        else {
                            if((noteList.get(checkBox.getId()).charAt(0))=='*')
                                noteList.add(checkBox.getId(),noteList.get(checkBox.getId()));

                        }
                   }
                });

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
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState == null){
            noteList = new ArrayList<>();
            // populate books from remote server or local json
        }else{
            noteList = savedInstanceState.getStringArrayList("x");
            Toast.makeText(getActivity(), noteList.get(0), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }
}
