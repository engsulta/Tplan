package com.example.sulta.tplan.view.utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sulta.tplan.R;

/**
 * Created by Passant on 3/23/2018.
 */

public class HomeViewHolderList {
    ImageButton viewTripDetailsBtn;
    ImageButton cancelTripBtn;
    ImageButton editTripDetailsBtn;
    TextView tripName;
    View contentView;

    public HomeViewHolderList(View contentView) {
        this.contentView = contentView;
    }

    public ImageButton getViewTripDetailsBtn() {
        if(viewTripDetailsBtn==null){
            viewTripDetailsBtn = (ImageButton) contentView.findViewById(R.id.viewTripDetailsBtn);
        }
        return viewTripDetailsBtn;
    }

    public ImageButton getCancelTripBtn() {
        if(getCancelTripBtn()==null){
            cancelTripBtn = (ImageButton) contentView.findViewById(R.id.cancelTripBtn);
        }
        return cancelTripBtn;
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
