package com.example.thomas.justchat.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thomas.justchat.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


/**
 * Created by Thomas on 2015-12-28.
 */
public class RegisterActivity extends Activity {

    private ImageView gcmImage;
    private Button btnAppSend, btnQuit;
    private GoogleCloudMessaging gcm;
    private String regId;
    private final String PROJECT_NUMBER = "370652955246";
    private EditText edtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Image: "Google cloud messaging"-logo
        gcmImage = (ImageView) findViewById(R.id.gcmImg);

        // EditText field
        edtUsername = (EditText) findViewById(R.id.edtUsername);

        // Buttons
        btnAppSend = (Button) findViewById(R.id.btnRegAppServer);
        btnQuit = (Button) findViewById(R.id.btnQuit);

        btnAppSend.setOnClickListener(new OnAppSendBtnClickListener());
        btnQuit.setOnClickListener(new OnQuitdBtnClickListener());
    }

    // Listener for "Send reg-ID to server"-button.
    private class OnAppSendBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (TextUtils.isEmpty(edtUsername.getText())) {
                Toast.makeText(getApplicationContext(), "Specify username!", Toast.LENGTH_LONG).show();
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
            } else {
                if (TextUtils.isEmpty(regId)) {
                    getRegId();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Already Registered with GCM Server!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // Listener for quit button.
    private class OnQuitdBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            finish();
            System.exit(0);
            return;
        }
    }

    public void getRegId() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regId;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("username", edtUsername.getText().toString());
                startActivity(i);
                finish();
                return;
            }
        }.execute(null, null, null);
    }
}
