package com.yap.testapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yap.testapplication.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText emailAddress;
    CheckBox img_visible;
    EditText password;
    Button register;
    ProgressBar reg_progressbar;
    FirebaseAuth mAuth;

    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        initView();
        initEvent();
    }

    private void initView() {
        emailAddress = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        register = findViewById(R.id.btn_register);
        reg_progressbar = findViewById(R.id.progress_bar);
        img_visible = findViewById(R.id.img_visible);
    }

    private boolean validateEmail() {
        // Extract input from EditText
        String emailInput = emailAddress.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            reg_progressbar.setVisibility(View.INVISIBLE);
            emailAddress.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            reg_progressbar.setVisibility(View.INVISIBLE);
            emailAddress.setError("Please enter a valid email address");
            return false;
        } else {
            emailAddress.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            reg_progressbar.setVisibility(View.INVISIBLE);
            password.setError("Field can not be empty");
            return false;
        }

        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            reg_progressbar.setVisibility(View.INVISIBLE);
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void initEvent() {
        register.setOnClickListener(v -> {
            final String email = emailAddress.getText().toString();
            final String pwd = password.getText().toString();

            reg_progressbar.setVisibility(View.VISIBLE);

            if (!validateEmail() | !validatePassword()) {
                return;
            } else {
                mAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    reg_progressbar.setVisibility(View.INVISIBLE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Register error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        img_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(null);
                }else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }
}