package com.example.sulta.tplan.view.utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sulta.tplan.R;

/**
 * Created by Passant on 3/25/2018.
 */

public class HomeViewHolderHistoryList {

    ImageButton viewTripMapBtn;
    ImageButton deleteTripBtn;
    ImageButton editTripDetailsBtn;
    ImageButton shareTripDetailsBtn;
    ImageButton moreOptionsBtn;
    TextView tripName;
    TextView tripState;
    LinearLayout tripLayout;
    View contentView;

    public HomeViewHolderHistoryList(View contentView) {
        this.contentView = contentView;
    }

    public ImageButton getViewMapTripBtn() {
        if (viewTripMapBtn == null) {
            viewTripMapBtn = (ImageButton) contentView.findViewById(R.id.viewTripMapBtn);
        }
        return viewTripMapBtn;
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

    public ImageButton getMoreOptionsBtn() {
        if (moreOptionsBtn == null) {
            moreOptionsBtn = (ImageButton) contentView.findViewById(R.id.moreOptionsBtn);
        }
        return moreOptionsBtn;
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

    public LinearLayout getTripLayout(){
        if(tripLayout==null){
            tripLayout = (LinearLayout) contentView.findViewById(R.id.tripLayout);
        }
        return tripLayout;
    }

}