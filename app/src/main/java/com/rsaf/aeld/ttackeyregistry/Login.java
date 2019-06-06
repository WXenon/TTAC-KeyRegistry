package com.rsaf.aeld.ttackeyregistry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Login extends AppCompatActivity {

    EditText userIdField, passwordField;
    RelativeLayout loginBtn;
    TextView signUpBtn, emailSuffix;
    String confirmedUserID;
    CheckBox signInPersist;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FirebaseApp.initializeApp(this);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading");
        firebaseAuth = FirebaseAuth.getInstance();

        userIdField = findViewById(R.id.login_userId_field);
        passwordField = findViewById(R.id.login_user_pw);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInPersist = findViewById(R.id.signInCheck);
        emailSuffix = findViewById(R.id.emailSuffix);

        emailSuffix.setText("@defence.gov.sg");

        //Load existing username and password on SharedPreferences
        SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
        String restoredUser = prefs.getString("storedUsername", null);
        String restoredPw = prefs.getString("storedPassword",null);
        if (restoredUser != null && restoredPw != null) {
            //Do auto login
            logIn(restoredUser, restoredPw);
        }



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                logIn(userIdField.getText().toString().trim().toLowerCase(), passwordField.getText().toString().trim().toLowerCase());
                Intent toMainActivity = new Intent(Login.this, MainActivity.class);
                toMainActivity.putExtra("action", "home");
                startActivity(toMainActivity);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signUp(userIdField.getText().toString().trim().toLowerCase(), passwordField.getText().toString().trim().toLowerCase());
                Intent signUpActivity = new Intent(Login.this, SignUp.class);
                startActivity(signUpActivity);
            }
        });

    }

    public void logIn(String emailInput, final String passwordInput){
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (emailInput.contains("@defence.gov.sg")) {
            confirmedUserID = emailInput;
            firebaseAuth.signInWithEmailAndPassword(confirmedUserID, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (signInPersist.isChecked()) {
                                    // Store username and password into SharedPrefs (if checkbox checked)
                                    SharedPreferences.Editor editor = getSharedPreferences("session",Context.MODE_PRIVATE).edit();
                                    editor.clear();
                                    editor.putString("storedUsername",confirmedUserID);
                                    editor.putString("storedPassword",passwordInput);
                                    editor.apply();
                                } else {
                                    SharedPreferences.Editor editor = getSharedPreferences("session",Context.MODE_PRIVATE).edit();
                                    editor.clear();
                                    editor.apply();
                                }

                                Intent toMainActivity = new Intent(Login.this, MainActivity.class);
                                toMainActivity.putExtra("UserID", confirmedUserID);
                                startActivity(toMainActivity);
                            } else {
                                Toast.makeText(Login.this, "Log in failed, please check that your email and password are correct.", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
        else{
            progressDialog.dismiss();
            userIdField.getText().clear();
            passwordField.getText().clear();
            Toast.makeText(this, "Please log in with a valid Defence Mail.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
        //Nothing happens when pressing Back button
    }
}