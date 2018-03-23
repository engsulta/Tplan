package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.utilities.UserManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
//facebook variables
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    private static final String TAG = "tplan_log";
    LoginButton loginButton;


//views

    private TextView loginTextRegister;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressBar progressBar;
    private Button loginbtn;
    private Button mFacebookBtn;

    private UserManager myUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
//managers
        myUserManager=UserManager.getUserInstance();


        // Views

        loginTextRegister = (TextView) findViewById(R.id.login_text_register);
        userEmail = (EditText) findViewById(R.id.login_text_email);
        userPassword = (EditText) findViewById(R.id.login_text_password);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        loginbtn = (Button) findViewById(R.id.login_button_login);
        mFacebookBtn = (Button) findViewById(R.id.login_button_mfacebook);
        //end views

        //listener
        loginTextRegister.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        mFacebookBtn.setOnClickListener(this);

        //end listener
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
               // myUserManager.setId(currentUser.getUid());
             currentUser.getEmail();
               myUserManager.setEmail(currentUser.getEmail());
               myUserManager.setPassword(currentUser.getDisplayName());//3awzeen nsheel el password from database
               myUserManager.setName(currentUser.getDisplayName());
            Log.i(TAG, "onStart: "+currentUser.getEmail()+currentUser.getDisplayName()  );
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }


    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            myUserManager.setEmail(user.getEmail());
                            myUserManager.setPassword(user.getDisplayName());
                            myUserManager.setName(user.getDisplayName());

                            mFacebookBtn.setEnabled(true);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mFacebookBtn.setEnabled(true);

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void hideProgressDialog() {

        progressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);

    }
    // [END auth_with_facebook]

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
        //back to login page
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            //store in shared pref
            //make profile in firebase
            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {

            Toast.makeText(this, "couldnot Log in successfully", Toast.LENGTH_SHORT).show();
            mFacebookBtn.setVisibility(View.VISIBLE);

        }
    }

    private void userLogin() {

        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();

        if (email.isEmpty()) {
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            YoYo.with(Techniques.Shake).playOn(userEmail);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Please enter a valid email");
            userEmail.requestFocus();
            YoYo.with(Techniques.Shake).playOn(userEmail);
            return;
        }

        if (password.isEmpty()) {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            YoYo.with(Techniques.Shake).playOn(userPassword);
            return;
        }

        if (password.length() < 6) {
            userPassword.setError("Minimum lenght of password should be 6");
            userPassword.requestFocus();
            YoYo.with(Techniques.Shake).playOn(userPassword);
            return;
        }

        //  progressBar.setVisibility(View.VISIBLE);
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    //first time login with email & password sharedpref make profile on firebase
                   FirebaseUser user=mAuth.getCurrentUser();
                   UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(email).build();
                    updateUserProfile(user,profile);
                    myUserManager.setEmail(email);
                    myUserManager.setPassword(password);
                    myUserManager.setName(email);

                    finish();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserProfile(FirebaseUser user, UserProfileChangeRequest profile) {
        user.updateProfile(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           // Toast.makeText(LoginActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.login_button_login:
                //TODO implement
                YoYo.with(Techniques.FadeIn).playOn(loginbtn);

                userLogin();
                break;
            case R.id.login_button_mfacebook:
                //TODO implement
                YoYo.with(Techniques.FadeIn).playOn(mFacebookBtn);

                loginWithFB();
                break;
            case R.id.login_text_register:
                YoYo.with(Techniques.FadeIn).playOn(loginTextRegister);
                //TODO implement
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void loginWithFB() {
        mFacebookBtn.setEnabled(false);
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,Arrays.asList(EMAIL, PROFILE));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                // forward to home page
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }


            @Override
            public void onCancel() {
                // App code
                // show toast
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "facebook:onError", exception);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
                // App code
                //show toast
            }

        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

}
