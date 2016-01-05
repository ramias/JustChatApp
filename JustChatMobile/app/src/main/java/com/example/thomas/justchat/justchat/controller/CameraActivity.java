package com.example.thomas.justchat.justchat.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thomas.justchat.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thomas on 2016-01-05.
 */
public class CameraActivity extends AppCompatActivity {


    private File file;
    private Intent intent;
    private Button btnCam, btnSendImg;
    private String friendName;
    private ImageView thumbnail;
    private Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnCam = (Button) findViewById(R.id.btnCam);
        btnSendImg = (Button) findViewById(R.id.btnSendImg);

        btnCam.setOnClickListener(new OnCameraBtnClickListener());
        btnSendImg.setOnClickListener(new OnSendImgBtnClickListener());

        btnSendImg.setEnabled(false); // False tills det finns en thumbnail
        thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        thumbnail.setImageResource(R.drawable.thumbnailicon);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            friendName = extras.getString("friendName");
        }
    }

    // Listener for Cam button.
    private class OnCameraBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            file = new File(Environment.getExternalStorageDirectory(), fileNameGenerator());
            outputFileUri = Uri.fromFile(file);
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, 1);
        }
    }

    // Listener for Send Image button.
    private class OnSendImgBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Toast.makeText(getApplicationContext(), "Sending image to "+friendName, Toast.LENGTH_LONG).show();
            // httpPost request -->
        }
    }

    // exempel på filnamn som genereras: 160105_IMG7235.jpg
    private String fileNameGenerator() {
        String pattern = "yyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fileName = simpleDateFormat.format(new Date());
        fileName = fileName.concat("_IMG"+((int)(Math.random()*8999+1000))+".jpg");
        return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(data!=null){
                if(data.hasExtra("data")){
                    Bitmap thumbnailImg = data.getParcelableExtra("data");
                }
            }else{

                //Resize

                int width = thumbnail.getWidth();
                int height = thumbnail.getHeight();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(outputFileUri.getPath(), options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                int scaleFactor = Math.min(imageWidth/width, imageHeight/height);

                // Decode

                options.inJustDecodeBounds = false;
                options.inSampleSize = scaleFactor;
                options.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(),options);

                thumbnail.setImageBitmap(bitmap);
            }
        }
    }
}
