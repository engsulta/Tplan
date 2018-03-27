package com.example.sulta.tplan.model;

/**
 * Created by Passant on 3/17/2018.
 */

public class TripNote  {

    String text;
    boolean isChecked;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}