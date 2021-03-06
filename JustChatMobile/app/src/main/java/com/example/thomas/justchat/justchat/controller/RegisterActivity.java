package com.example.thomas.justchat.justchat.controller;

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
import com.example.thomas.justchat.justchat.model.User;
import com.example.thomas.justchat.justchat.model.UserClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Thomas on 2015-12-28.
 */
public class RegisterActivity extends Activity {

    private ImageView gcmImage;
    private Button btnRegAppServer, btnQuit;
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
        btnRegAppServer = (Button) findViewById(R.id.btnRegAppServer);
        btnQuit = (Button) findViewById(R.id.btnQuit);

        btnRegAppServer.setOnClickListener(new OnRegBtnClickListener());
        btnQuit.setOnClickListener(new OnQuitBtnClickListener());
    }

    // Listener for "Login"-button.
    private class OnRegBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (TextUtils.isEmpty(edtUsername.getText())) {
                Toast.makeText(getApplicationContext(), "Specify username without whitespaces!", Toast.LENGTH_LONG).show();
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
            } else {
                if(edtUsername.getText().toString().contains(" ")){
                    edtUsername.setText(edtUsername.getText().toString().replaceAll("\\s","_"));
                }
                doLogin();
            }
        }
    }

    // Listener for quit button.
    private class OnQuitBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            finish();
            System.exit(0);
            return;
        }
    }

    public void doLogin() {
        edtUsername.setEnabled(false);
        btnRegAppServer.setEnabled(false);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String username = params[0];
                Log.i("login", "Username: " + username);
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());

                    }
                    regId = gcm.register(PROJECT_NUMBER);
                    User user = new User(username, regId, "0000000000");
                    if (!UserClient.register(user).equals("200")) {
                        return null;
                    }

                } catch (IOException ex) {
                    Log.i("error", "Error :" + ex.getMessage());

                }
                return regId;
            }

            @Override
            protected void onPostExecute(String regId) {
                if (regId != null) {
                    String filename = "justStore.txt";
                    String username = edtUsername.getText().toString();
                    FileOutputStream outputStream = null;

                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(username.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (outputStream != null)
                                outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.putExtra("username", edtUsername.getText().toString());
                    startActivity(i);
                    finish();
                    return;
                } else {
                    edtUsername.setEnabled(true);
                    btnRegAppServer.setEnabled(true);
                    Toast.makeText(getApplicationContext(),
                            "Username is in taken!",
                            Toast.LENGTH_LONG).show();

                }
            }
        }.execute(edtUsername.getText().toString(), null, null);

    }
}
