package com.fyp.david.sensorycontrolv2.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.RecordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by DAVID on 3/21/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private final String TAG = "SignupActivity";

    FirebaseAuth auth;
    TextInputLayout signupInputLayoutEmail;
    TextInputLayout signupInputLayoutPassword;
    ProgressBar progressBar;
    EditText signupEmail;
    EditText signupPassword;
    Button btnSignup;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        signupInputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_input_layout_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signupEmail = (EditText) findViewById(R.id.signup_input_email);
        signupPassword = (EditText) findViewById(R.id.signup_input_password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_link_login);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(tent);
            }
        });

    }


    private void submitForm() {

        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }

        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        //actually create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()) {
                            Log.d(TAG, "Authentication failed." + task.getException());
                        }

                        else {
                            Intent intent = new Intent(SignupActivity.this, RecordActivity.class);
                            startActivity(intent);
                            //finish();
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "You are successfully Registered!",
                Toast.LENGTH_SHORT).show();
    }


    private boolean checkEmail() {

        String email = signupEmail.getText().toString().trim();
        if(email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            //signupInputLayoutEmail.setError("Some details are wrong or empty!");
            signupEmail.setError("Required!");
            requestFocus(signupEmail);
            return false;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        else return false;
    }

    private boolean checkPassword() {

        String password = signupPassword.getText().toString().trim();
        if(password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setErrorEnabled(true);
            //signupInputLayoutPassword.setError("Some details are wrong or empty!");
            signupPassword.setError("Required!");
            requestFocus(signupPassword);
            return false;
        }
        signupInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private static boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }


    private void requestFocus(View view) {

        if(view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}