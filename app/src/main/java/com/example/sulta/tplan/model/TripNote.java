package com.example.sulta.tplan.model;

import java.io.Serializable;

/**
 * Created by Passant on 3/17/2018.
 */

public class TripNote implements Serializable {

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