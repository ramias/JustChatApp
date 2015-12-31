package com.example.thomas.justchat;

import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by Thomas on 2015-12-28.
 */
public class RegisterActivity extends Activity{

    private ImageView gcmImage;
    private Button btnGCMRegister, btnAppSend, btnQuit;
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
        btnGCMRegister = (Button) findViewById(R.id.btnRegGcmServer);
        btnAppSend = (Button) findViewById(R.id.btnRegAppServer);
        btnQuit = (Button) findViewById(R.id.btnQuit);

        btnGCMRegister.setOnClickListener(new OnRegGCMBtnClickListener());
        btnAppSend.setOnClickListener(new OnAppSendBtnClickListener());
        btnQuit.setOnClickListener(new OnQuitdBtnClickListener());

        btnAppSend.setEnabled(false);
    }

    // Listener for register btn.
    private class OnRegGCMBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {

            if (TextUtils.isEmpty(regId)) {
                getRegId();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Already Registered with GCM Server!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    // Listener for "Send reg-ID to server"-button.
    private class OnAppSendBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (TextUtils.isEmpty(edtUsername.getText())) {
                Toast.makeText(getApplicationContext(), "Specify username!", Toast.LENGTH_LONG).show();
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
            }else{
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("username",edtUsername.getText().toString());
                startActivity(i);
                finish();
                return;
            }
        }
    }

    // Listener for quit button.
    private class OnQuitdBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            finish();
            System.exit(0); // nått fel här.. vill inte avsluta
            return;
        }
    }

    public void getRegId(){
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
                    Log.i("GCM",  msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getApplicationContext(), "Registered with GCM Server!", Toast.LENGTH_LONG).show();
                btnGCMRegister.setEnabled(false);
                btnAppSend.setEnabled(true);
            }
        }.execute(null, null, null);
    }
}
