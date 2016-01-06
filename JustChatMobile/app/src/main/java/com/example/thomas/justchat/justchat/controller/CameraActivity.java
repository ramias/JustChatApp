package com.example.thomas.justchat.justchat.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thomas.justchat.R;
import com.example.thomas.justchat.justchat.model.UserClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thomas on 2016-01-05.
 */
public class CameraActivity extends AppCompatActivity {
    private Intent intent;
    private Button btnCam, btnSendImg;
    private String friendName, phoneNumber;
    private ImageView thumbnail;
    private Uri outputFileUri;
    private int width, height;
    private EditText edtImgInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnCam = (Button) findViewById(R.id.btnCam);
        btnSendImg = (Button) findViewById(R.id.btnSendImg);

        edtImgInput = (EditText) findViewById(R.id.edt_imgInput);
        edtImgInput.setText("");
        btnCam.setOnClickListener(new OnCameraBtnClickListener());
        btnSendImg.setOnClickListener(new OnSendImgBtnClickListener());

        btnSendImg.setEnabled(false); // False tills det finns en thumbnail
        thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        thumbnail.setImageResource(R.drawable.thumbnailicon);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            friendName = extras.getString("friendName");
            setPhoneNumber();
        }
    }

    private void setPhoneNumber() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return UserClient.getPhoneNumber(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("mms", "resultset: " + result);
                if (result != null) {
                    phoneNumber =  result;
                } else {
                    phoneNumber = null;
                }
            }
        }.execute(friendName, null, null);
    }


    // Listener for Cam button.
    private class OnCameraBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            outputFileUri = getOutputMediaFile();
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            btnSendImg.setEnabled(true);
            startActivityForResult(intent, 100);
        }
    }

    // Listener for Send Image button.
    private class OnSendImgBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if(phoneNumber != null) {
                Toast.makeText(getApplicationContext(), "Sending image to " + friendName, Toast.LENGTH_LONG).show();
                createMMS(edtImgInput.getText().toString()); // skicka med teleNr som arg.
            } else {
                Toast.makeText(getApplicationContext(), friendName + " does not want to receive images", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createMMS(String imageDescription) {

        Intent mmsIntent = new Intent(Intent.ACTION_SEND, outputFileUri);
        mmsIntent.putExtra("sms_body", imageDescription);
        mmsIntent.putExtra("address", phoneNumber); // test
        mmsIntent.putExtra(Intent.EXTRA_STREAM, outputFileUri);
        mmsIntent.setType("image/jpeg");
        startActivity(mmsIntent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                thumbnail.setImageBitmap(imageBitmap);
            } else {
                //Resize
                width = thumbnail.getWidth();
                height = thumbnail.getHeight();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Log.i("cameraPath", "cam path: " + outputFileUri);

                BitmapFactory.decodeFile(this.outputFileUri.getPath(), options);

                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

                // Decode

                options.inJustDecodeBounds = false;
                options.inSampleSize = scaleFactor;
                options.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(), options);

                thumbnail.setImageBitmap(rotateImage(bitmap, 90));
            }
        } else {
            Log.i("camera", "result: " + resultCode);
            btnSendImg.setEnabled(false);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }


    /**
     * Create a File for saving an image or video http://developer.android.com/guide/topics/media/camera.html#saving-media
     */
    private static Uri getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "JustChat");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }
}
