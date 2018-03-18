package com.example.sulta.tplan.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sulta.tplan.R;
import com.example.sulta.tplan.view.activities.interfaces.IRegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity,View.OnClickListener {

    EditText userEmail,userPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerbtn=(Button) findViewById(R.id.register_button_register);
        findViewById(R.id.register_button_register).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.register_progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        userEmail=(EditText)findViewById(R.id.register_text_email);
        userPassword=(EditText)findViewById(R.id.register_text_password);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

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
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button_register:
                YoYo.with(Techniques.FadeIn).playOn(registerbtn);

                registerUser();

                break;
        }
    }
}
