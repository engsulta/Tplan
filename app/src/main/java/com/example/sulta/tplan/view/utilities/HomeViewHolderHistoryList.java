package com.example.sulta.tplan.view.utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sulta.tplan.R;

/**
 * Created by Passant on 3/25/2018.
 */

public class HomeViewHolderHistoryList {

    ImageButton viewTripDetailsBtn;
    ImageButton deleteTripBtn;
    ImageButton editTripDetailsBtn;
    ImageButton shareTripDetailsBtn;
    TextView tripName;
    TextView tripState;
    View contentView;

    public HomeViewHolderHistoryList(View contentView) {
        this.contentView = contentView;
    }

    public ImageButton getViewTripDetailsBtn() {
        if (viewTripDetailsBtn == null) {
            viewTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.viewTripDetailsBtn);
        }
        return viewTripDetailsBtn;
    }

    public ImageButton getDeleteTripBtn() {
        if (deleteTripBtn == null) {
            deleteTripBtn = (ImageButton) contentView.findViewById(R.id.deleteTripBtn);
        }
        return deleteTripBtn;
    }

    public ImageButton getEditTripDetailsBtn() {
        if (editTripDetailsBtn == null) {
            editTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.editTripDetailsBtn);
        }
        return editTripDetailsBtn;
    }

    public ImageButton getShareTripDetailsBtn() {
        if (shareTripDetailsBtn == null) {
            shareTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.shareTripDetailsBtn);
        }
        return shareTripDetailsBtn;
    }

    public TextView getTripName() {
        if (tripName == null) {
            tripName = (TextView) contentView.findViewById(R.id.tripName);
        }
        return tripName;
    }

    public TextView getTripState() {
        if (tripState == null) {
            tripState = (TextView) contentView.findViewById(R.id.tripState);
        }
        return tripState;
    }
}