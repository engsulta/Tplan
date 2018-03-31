package com.example.sulta.tplan.presenter.interfaces;

import android.content.Context;

import com.example.sulta.tplan.view.utilities.UserManager;

/**
 * Created by Passant on 3/17/2018.
 */

public interface ILoginActivityPresenter {
    public void creatUserInDb(UserManager userManager);

    public void removeUserInDb(UserManager myUserManager);

    public void downloadTripsForUser();

    public void startAllAlarms(Context context);
}
