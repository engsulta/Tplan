package com.example.sulta.tplan.view.utilities;

import com.example.sulta.tplan.model.User;

/**
 * Created by sulta on 3/23/2018.
 */

public class UserManager extends User {
    private static UserManager userInstance;
    private UserManager(){

    }
    public static UserManager getUserInstance(){
        if(userInstance==null){
            userInstance=new UserManager();
        }
        return userInstance;
    }
}
