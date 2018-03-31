package com.example.sulta.tplan.view.utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sulta.tplan.R;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeViewHolderUpComingList {
    ImageButton viewTripMapBtn;
    ImageButton cancelTripBtn;
    ImageButton editTripDetailsBtn;
    ImageButton shareTripDetailsBtn;
    ImageButton startTripBtn;
    TextView tripName;
    LinearLayout tripLayout;
    View contentView;

    public HomeViewHolderUpComingList(View contentView) {
        this.contentView = contentView;
    }

    public LinearLayout getTripLayout(){
        if(tripLayout==null){
            tripLayout = (LinearLayout) contentView.findViewById(R.id.tripLayout);
        }
        return tripLayout;
    }

    public ImageButton getViewTripMapBtn() {
        if(viewTripMapBtn==null){
            viewTripMapBtn = (ImageButton) contentView.findViewById(R.id.viewTripMapBtn);
        }
        return viewTripMapBtn;
    }

    public ImageButton getCancelTripBtn() {
        if(cancelTripBtn == null){
            cancelTripBtn = (ImageButton) contentView.findViewById(R.id.cancelTripBtn);
        }
        return cancelTripBtn;
    }

    public ImageButton getShareTripDetailsBtn() {
        if(shareTripDetailsBtn == null){
            shareTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.shareTripDetailsBtn);
        }
        return shareTripDetailsBtn;
    }

    public ImageButton getStartTripBtn() {
        if(startTripBtn == null){
            startTripBtn = (ImageButton) contentView.findViewById(R.id.startTripBtn);
        }
        return startTripBtn;
    }

    public ImageButton getEditTripDetailsBtn() {
        if(editTripDetailsBtn == null){
            editTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.editTripDetailsBtn);
        }
        return editTripDetailsBtn;
    }

    public TextView getTripName() {
        if(tripName==null){
            tripName = (TextView) contentView.findViewById(R.id.tripName);
        }
        return tripName;
    }
}
