package com.example.sulta.tplan.view.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by sulta on 3/22/2018.
 */

public class  MySharedPrefManger  {
    private static final String SHARED_PREF_NAME="SharedTplanFile";
    private static Context context=null;
    private static MySharedPrefManger myInstance;

    private MySharedPrefManger(Context context){

       this.context=context;
    }
    public static MySharedPrefManger getInstance(Context context){

        if(myInstance ==null){
            myInstance=new MySharedPrefManger(context);
        }
        return myInstance;
    }
    public  void  storeStringToken(String key ,String token){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,token);
        editor.apply();
    }
    public  String getStringToken(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key,null);
    }
    public  void  storeBoolToken(String key ,Boolean token){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,token);
        editor.apply();
    }
    public  Boolean getBoolToken(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(key,false);
    }
    public  void  storeIntToken(String key ,int token){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,token);
        editor.apply();
    }
    public  int getIntToken(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(key,-1);

    }
    public  void  storeSetToken(String key ,Set<String>  token){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putStringSet(key,token);
        editor.apply();
    }
    public  Set<String> getSetToken(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getStringSet(key,null);
    }

    public void storeSettings(String key, int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public int getSettings(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(key,-1);
    }

}
