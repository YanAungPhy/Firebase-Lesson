package com.yap.testapplication.fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yap.testapplication.R;
public class SearchFragment extends Fragment {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button mCaptureBtn;
    ImageView mImageView;
    Uri image_uri;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        initView();
        initEvent();
        return rootView;
    }


    private void initView() {
        mImageView = rootView.findViewById(R.id.cmdImage);
        mCaptureBtn = rootView.findViewById(R.id.btnCapture);
    }


    private void initEvent() {
        mCaptureBtn.setOnClickListener(v -> {
            //if System os is >= marshmallow, request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) ==
                                PackageManager.PERMISSION_DENIED) {

                    //permission not enabled, requested it
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    //permission already granted
                    openCamera();
                }
            } else {
                //system os <  marshmallow
                openCamera();
            }
        });
    }

    private void openCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "New Picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        //Camera Intent
        Intent cameraIntet = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntet.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntet, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from premssion Request Popup
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission from popu  p was granted
                openCamera();
            } else {
                //permission from popup was denied
                Toast.makeText(getContext(), "Permission denied......", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_CAPTURE_CODE) {
            mImageView.setImageURI(image_uri);
        }else {
            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
}