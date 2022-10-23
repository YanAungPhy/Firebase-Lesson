package com.yap.testapplication.checkGooglePlayService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.yap.testapplication.R;

public class CheckPlayService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_play_service);

        if(isGooglePlayServiceAvailable()){
            Toast.makeText(this, "Your device have google service", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Google Service no have",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isGooglePlayServiceAvailable(){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        return  status == ConnectionResult.SUCCESS;
    }
}