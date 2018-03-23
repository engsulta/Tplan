package com.example.sulta.tplan.view.utilities;

import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeCustomeItemUpComingList {

    ImageButton viewTripDetailsBtn;
    ImageButton cancelTripBtn;
    ImageButton editTripDetailsBtn;
    TextView tripName;

    public HomeCustomeItemUpComingList(TextView tripName, ImageButton viewTripDetailsBtn, ImageButton editTripDetailsBtn, ImageButton cancelTripBtn) {
        this.viewTripDetailsBtn = viewTripDetailsBtn;
        this.cancelTripBtn = cancelTripBtn;
        this.editTripDetailsBtn = editTripDetailsBtn;
        this.tripName = tripName;
    }

    public ImageButton getViewTripDetailsBtn() {
        return viewTripDetailsBtn;
    }

    public void setViewTripDetailsBtn(ImageButton viewTripDetailsBtn) {
        this.viewTripDetailsBtn = viewTripDetailsBtn;
    }

    public ImageButton getCancelTripBtn() {
        return cancelTripBtn;
    }

    public void setCancelTripBtn(ImageButton cancelTripBtn) {
        this.cancelTripBtn = cancelTripBtn;
    }

    public ImageButton getEditTripDetailsBtn() {
        return editTripDetailsBtn;
    }

    public void setEditTripDetailsBtn(ImageButton editTripDetailsBtn) {
        this.editTripDetailsBtn = editTripDetailsBtn;
    }

    public TextView getTripName() {
        return tripName;
    }

    public void setTripName(TextView tripName) {
        this.tripName = tripName;
    }
}
