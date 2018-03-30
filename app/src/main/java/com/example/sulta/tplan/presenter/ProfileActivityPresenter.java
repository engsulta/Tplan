package com.example.sulta.tplan.presenter;

import android.content.Context;

import com.example.sulta.tplan.presenter.interfaces.IProfileActivityPresenter;
import com.example.sulta.tplan.view.utilities.UserManager;

/**
 * Created by Passant on 3/28/2018.
 */

public class ProfileActivityPresenter  implements IProfileActivityPresenter {

    @Override
    public boolean editProfile(Context context, String email, String password) {
        UserManager manager = UserManager.getUserInstance();

        


        return true;
    }
}
