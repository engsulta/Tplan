package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.activities.interfaces.IProfileActivity;

public class ProfileActivity extends AppCompatActivity implements IProfileActivity {

    private EditText userEmail, userPassword;
    private Button saveButton, cancelButton;
    Toolbar homeToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userEmail = (EditText) findViewById(R.id.profile_text_email);
        userPassword = (EditText) findViewById(R.id.profile_text_password);
        saveButton = (Button) findViewById(R.id.savebtn);
        cancelButton = (Button) findViewById(R.id.cancelbtn);
        homeToolBar = (Toolbar) findViewById(R.id.tpToolBar);
        setSupportActionBar(homeToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        homeToolBar.inflateMenu(R.menu.menu_profiletoolbar);
        homeToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.syncTripsToFirebase:
                        Toast.makeText(ProfileActivity.this, "Sync", Toast.LENGTH_SHORT).show();
                        //TODO calling sync method to get data from firebase
                        return true;
                    case R.id.logoutFromApp:
                        Toast.makeText(ProfileActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        //TODO calling logout method which clears user's data
                        return true;
                    default:
                        return false;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = (SupportMenuInflater) getMenuInflater();
        inflater.inflate(R.menu.menu_profiletoolbar, menu);
        return true;
    }
}
