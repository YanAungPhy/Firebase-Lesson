package com.yap.testapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yap.testapplication.R;
import com.yap.testapplication.activity.MainActivity;
import com.yap.testapplication.event.DialogEvent;
import com.yap.testapplication.modal.ResponseStatus;
import com.yap.testapplication.network.RetrofitStatusAPI;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    public static int SELECT_IMAGE_CODE = 21;
    Button btn_selectImage;
    Button btn_uploadImage;
    Button dialog_btn;
    ImageView firebaseImage;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

//https://www.youtube.com/watch?v=g2Iibnnqga0
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_home, container, false);

        dialog_btn = rootview.findViewById(R.id.btn_click);
        firebaseImage = rootview.findViewById(R.id.firebaseImage);
        btn_selectImage = rootview.findViewById(R.id.selectImageBtn);
        btn_uploadImage = rootview.findViewById(R.id.uploadImageBtn);

        Log.i("DEVICEID----->>>>>", Build.DEVICE);

        //firebaseInstanceMessaging();

        btn_selectImage.setOnClickListener(v -> selectImage());
        btn_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri == null){
                    Toast.makeText(getContext(), "Please select your image", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                }
            }
        });

        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegisterShowDialog();
            }
        });

        return rootview;
    }

    private void firebaseInstanceMessaging() {
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
                        Log.d("TEST--->>", token);
                        Toast.makeText(getContext(), "Your device registration token is "+token, Toast.LENGTH_SHORT).show();

                        TextView accessTokenTxt = rootview.findViewById(R.id.accessTokenTxt);
                        accessTokenTxt.setText(token);
                        Log.i("------->>>>",token);
                    }
                });
    }

    //upload in firebase
    private void uploadImage() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyy_mm_dd_HH_mm_ss", Locale.UK);
        Date now = new Date();
        String fileName = formatter.format(now);

        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //firebaseImage.setImageURI(null);
                        Toast.makeText(getContext(), "Image Upload Sucess!", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getContext(), "Image Upload Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE_CODE);
    }

    private void userRegisterShowDialog() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.dg8923.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Log.i("CHECKINGRESPONSE",retrofit+"");

        //instance for interface
        RetrofitStatusAPI statusAPI = retrofit.create(RetrofitStatusAPI.class);
        Call<ResponseStatus> call = statusAPI.getData();
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {

                String status = response.body().getStatus().toString();
                Log.i("CHECKINGRESPONSE", status);
                EventBus.getDefault().post(new DialogEvent(status));


            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Log.i("CHECKINGRESPONSE", t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE && data != null && data.getData() != null) {
            imageUri = data.getData();
            firebaseImage.setImageURI(imageUri);
        }
    }
}


