package com.yap.testapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yap.testapplication.R;
import com.yap.testapplication.event.DialogEvent;
import com.yap.testapplication.fragment.HomeFragment;
import com.yap.testapplication.fragment.ProfileFragment;
import com.yap.testapplication.fragment.SearchFragment;
import com.yap.testapplication.fragment.SettingFragment;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class MainActivity extends AppCompatActivity{


    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        initView();
        initEvent();
        //userRegisterShowDialog();

        fireBaseMessaging();

    }

    private void fireBaseMessaging() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TEST--->>", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("TEST", token);
                        Toast.makeText(MainActivity.this, "Your device registration token is "+token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        fab = findViewById(R.id.btn_fab);

    }
    private void initEvent() {

        replaceFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    return true;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    return true;
                case R.id.setting:
                    replaceFragment(new SettingFragment());
                    return true;
            }
            return true;
        });


        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ImageCropTextCopyActivity.class)));

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(DialogEvent event) {
        String status = event.status;
        Log.d("EVENTBUS",status);
        Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.custom_dialog_with_lottie);
        TextView txtStatus = dialog.findViewById(R.id.dialog_txt);
        ImageView close = dialog.findViewById(R.id.close);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txtStatus.setText(status);

        close.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}