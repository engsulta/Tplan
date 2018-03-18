package com.example.sulta.tplan.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.activities.interfaces.IRegisterActivity;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
