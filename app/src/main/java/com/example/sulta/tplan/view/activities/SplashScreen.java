package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.services.MyServiceConnection;

public class SplashScreen extends AppCompatActivity {
    final static int SPLASH_DISPLAY_LENGTH=2000;
    MyServiceConnection myServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        myServiceConnection=MyServiceConnection.getInstance();
        myServiceConnection.bind(getApplicationContext());
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(myServiceConnection.getBound()){
                Intent mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
