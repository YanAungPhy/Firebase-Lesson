package com.yap.testapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yap.testapplication.R;

public class SplashActivity extends AppCompatActivity {

    TextView wellcome_text;
    LottieAnimationView lottieAnimationView;
    private FirebaseAuth firebaseAuth;
    //FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);
        firebaseAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wellcome_text = findViewById(R.id.wellcome_text);
        lottieAnimationView = findViewById(R.id.lottie);

        wellcome_text.animate().translationX(-1400).setDuration(2700).setStartDelay(0);
        lottieAnimationView.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        new Handler().postDelayed(() -> {

            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser  mfirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            if (mfirebaseUser != null) {
                Toast.makeText(getApplicationContext(), "User register", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "No register", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
            finish();
        },3000);
    }


}