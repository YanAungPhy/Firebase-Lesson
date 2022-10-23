package com.yap.testapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.yap.testapplication.R;
import com.yap.testapplication.activity.LoginActivity;

import com.yap.testapplication.localization.LocalHelper;


public class SettingFragment extends Fragment {

    FirebaseAuth mauth;
    LocalHelper localHelper;
    View v;
    Button logout, burmese, english;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);

        localHelper = new LocalHelper(getContext());
        localHelper.loadLanguage();
        initView();
        initEvent();
        mauth = FirebaseAuth.getInstance();


        return v;
    }

    private void initView() {
        logout = v.findViewById(R.id.logout);
        burmese = v.findViewById(R.id.Burmese);
        english = v.findViewById(R.id.English);
    }

    private void initEvent() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        burmese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMMFont();
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setENGFont();
            }
        });
    }

    public void setENGFont() {
        localHelper.setLanguage("en-rUS");
        getActivity().recreate();
    }

    public void setMMFont() {
        localHelper.setLanguage("my");
        getActivity().recreate();
    }


}