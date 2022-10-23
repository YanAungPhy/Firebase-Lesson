package com.yap.testapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yap.testapplication.R;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    CheckBox img_visible;
    TextView txt_register;
    Button btn_login;
    EditText email_address;
    EditText edt_password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for testing putExtra and getStringExtra(passing data two activity)
//        String title = getIntent().getStringExtra("TITLE");
//        Log.i("TESTPASSINGDATA",title);

        mAuth = FirebaseAuth.getInstance();
        initiView();
        initEvent();
    }

    private void initiView() {
        btn_login = findViewById(R.id.btn_login);
        txt_register = findViewById(R.id.register);
        email_address = findViewById(R.id.email_address);
        edt_password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);
        img_visible = findViewById(R.id.img_visible);
    }

    private void initEvent() {
        img_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_password.setTransformationMethod(null);
                } else {
                    edt_password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        txt_register.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_address.getText().toString();
                String password = edt_password.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email)) {
                    email_address.setError("Email cannot be empty");
                    email_address.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    edt_password.setError("Password cannot be empty");
                    edt_password.requestFocus();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Login Fiall! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}