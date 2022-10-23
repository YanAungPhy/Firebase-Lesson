package com.yap.testapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yap.testapplication.R;

import java.io.IOException;

public class ImageCropTextCopyActivity extends AppCompatActivity {

    //https://www.youtube.com/watch?v=sjkDbxyoNW0

    Button btn_captrue;
    Button btn_copy;
    TextView textData;
    public static final int REQUEST_CAMERA_CODE = 1000;
    Bitmap bitmap;
    ImageView img;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop_text_copy);

        btn_captrue = findViewById(R.id.btn_capture);
        btn_copy = findViewById(R.id.btn_copy);
        //textData = findViewById(R.id.text_data);
        img = findViewById(R.id.img);

        checkCameraPermission();
        viewEvent();
    }

    private void viewEvent() {
        btn_captrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ImageCropTextCopyActivity.this);

            }
        });

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scanned_text = textData.getText().toString();
                copyToClipBoard(scanned_text );
            }
        });
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("GETDATA---->>",data.getData()+"");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.i("REQUESTCODEOK",resultCode+"");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    imageUri = data.getData();
                    img.setImageURI(imageUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //ရလာတဲ့ ပုံကနေ စာပြောင်းတဲ့ function
    private void getTextFromImage(Bitmap bitmap) {
        Log.i("CROPPINGGGG----->>>",bitmap+"");
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (recognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "Error Occured!!!", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            textData.setText(stringBuilder.toString());
            btn_captrue.setText("Retake");
            btn_copy.setVisibility(View.VISIBLE);
        }
    }

    private void copyToClipBoard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied data", text);
        clipboardManager.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
    }
}
