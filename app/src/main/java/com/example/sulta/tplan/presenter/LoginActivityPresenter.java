package com.example.sulta.tplan.presenter;

import android.content.Context;

import com.example.sulta.tplan.database.SynchData;
import com.example.sulta.tplan.presenter.interfaces.ILoginActivityPresenter;
import com.example.sulta.tplan.view.utilities.UserManager;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Passant on 3/17/2018.
 */

public class LoginActivityPresenter implements ILoginActivityPresenter {

    private final String DATABASE_REF_Users = "users";
    private DatabaseReference databaseUsers ;
    private Context mcontext;
    public LoginActivityPresenter(Context context){
        this.mcontext=context;
    }
    @Override
    public void creatUserInDb(UserManager userManager) {
        SynchData.getInstance(mcontext);


    }

    @Override
    public void removeUserInDb(UserManager myUserManager) {

    }
}
