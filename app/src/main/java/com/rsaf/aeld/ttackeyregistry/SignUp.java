package com.rsaf.aeld.ttackeyregistry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText userIdField, userFullNameField, passwordField, passwordField2,rank,branch,appointment;
    RelativeLayout signUpBtn;
    TextView signUpEmailSuffix, signUpError;
    String confirmedUserID;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_dialog);
        FirebaseApp.initializeApp(this);
        toolbar = findViewById(R.id.signUptoolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Loading");
        firebaseAuth = FirebaseAuth.getInstance();

        userIdField = findViewById(R.id.signup_userId_field);
        signUpEmailSuffix = findViewById(R.id.signUp_emailSuffix);
        userFullNameField = findViewById(R.id.sign_up_name);
        passwordField = findViewById(R.id.signup_user_pw);
        passwordField2 = findViewById(R.id.signup_user_pw_cfm);
        signUpBtn = findViewById(R.id.signUpBtn);
        signUpError = findViewById(R.id.signUperrorMsg);

        signUpEmailSuffix.setText("@defence.gov.sg");

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordField.length() < 6) {
                    Toast.makeText(SignUp.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    if (passwordField.getText().toString().equals(passwordField2.getText().toString())) {
                        signUp(userIdField.getText().toString().trim().toLowerCase() + signUpEmailSuffix.getText().toString(), userFullNameField.getText().toString(), passwordField.getText().toString().trim());
                    } else {
                       signUpError.setVisibility(View.VISIBLE);
                        passwordField2.getText().clear();
                        passwordField.getText().clear();
                    }
                }
            }
        });
    }

    public void signUp(String emailInput, final String fullNameInput, String passwordInput){

        final Map<String, Object> user = new HashMap<>();
        user.put("email", emailInput);
        user.put("name", fullNameInput);

        progressDialog.show();
        confirmedUserID = emailInput;
        firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUp.this,"User Created",Toast.LENGTH_SHORT).show();
                        db.collection("users").document(confirmedUserID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignUp.this, "Fields added to user document", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, "Fields NOT added: "+ e.toString(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this,"User Creation Unsuccessful: " + e.toString(),Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
