package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sulta.tplan.R;
import com.example.sulta.tplan.presenter.ProfileActivityPresenter;
import com.example.sulta.tplan.presenter.interfaces.IProfileActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.IProfileActivity;
import com.example.sulta.tplan.view.utilities.UserManager;

public class ProfileActivity extends AppCompatActivity implements IProfileActivity {

    private EditText userEmail, userPassword;
    private Button saveButton, cancelButton;
    Toolbar homeToolBar;
    private ProgressBar progressBar;
    TextView backFromProfile, durationPerMonth, distancePerMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userEmail = (EditText) findViewById(R.id.profile_text_email);
        userPassword = (EditText) findViewById(R.id.profile_text_password);
        backFromProfile = (TextView) findViewById(R.id.backFromProfile);
        durationPerMonth = (TextView) findViewById(R.id.durationPerMonthTxt);
        distancePerMonth = (TextView) findViewById(R.id.distancePerMonthTxt);
        saveButton = (Button) findViewById(R.id.savebtn);
        cancelButton = (Button) findViewById(R.id.cancelbtn);
        homeToolBar = (Toolbar) findViewById(R.id.tpToolBar);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        hideProgressDiaglog();
        viewProfile();
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
        backFromProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText().toString().isEmpty()) {
                    userEmail.setError("Email is required");
                    userEmail.requestFocus();
                    YoYo.with(Techniques.Shake).playOn(userEmail);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()) {
                    userEmail.setError("Please enter a valid email");
                    userEmail.requestFocus();
                    YoYo.with(Techniques.Shake).playOn(userEmail);
                } else if (userPassword.getText().toString().isEmpty()) {
                    userPassword.setError("Password is required");
                    userPassword.requestFocus();
                    YoYo.with(Techniques.Shake).playOn(userPassword);
                } else if (userPassword.getText().toString().length() < 6) {
                    userPassword.setError("Minimum lenght of password should be 6");
                    userPassword.requestFocus();
                    YoYo.with(Techniques.Shake).playOn(userPassword);
                } else {
                    displayProgressDialog();
                    IProfileActivityPresenter profilePresenter = new ProfileActivityPresenter();
                    boolean isEdited = profilePresenter.editProfile(ProfileActivity.this, userEmail.getText().toString().trim(), userPassword.getText().toString().trim());
                    if(isEdited){
                        Toast.makeText(ProfileActivity.this, "Edited", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(ProfileActivity.this, "Cannot edit, Please try later", Toast.LENGTH_SHORT).show();
                    }
                    hideProgressDiaglog();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = (SupportMenuInflater) getMenuInflater();
        inflater.inflate(R.menu.menu_profiletoolbar, menu);
        return true;
    }

    @Override
    public void displayProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDiaglog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void viewProfile(){
        UserManager manager = UserManager.getUserInstance();
        userEmail.setText(manager.getEmail());
        distancePerMonth.setText(String.valueOf(manager.getDistancePerMonth()));
        durationPerMonth.setText(String.valueOf(manager.getDurationPerMonth()));
    }
}
