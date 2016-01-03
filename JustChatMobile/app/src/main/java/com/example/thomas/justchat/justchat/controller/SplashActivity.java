package com.example.thomas.justchat.justchat.controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.thomas.justchat.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SplashActivity extends AppCompatActivity {

    ImageView animContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animContainer = (ImageView) findViewById(R.id.imageview_animation_list_filling);

        startAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                          String username = null;

                                          FileInputStream inputStream = null;

                                          try {
                                              FileInputStream fis = openFileInput("justStore.txt");
                                              InputStreamReader isr = new InputStreamReader(fis);
                                              BufferedReader bufferedReader = new BufferedReader(isr);
                                              String line;
                                              while ((line = bufferedReader.readLine()) != null) {
                                                  username = line;
                                              }
                                          } catch (Exception e) {
                                              e.printStackTrace();
                                          } finally {
                                              try {
                                                  if (inputStream != null)
                                                      inputStream.close();
                                              } catch (IOException e) {
                                                  e.printStackTrace();
                                              }
                                          }

                                          Intent i;
                                          if (username == null)
                                              i = new Intent(SplashActivity.this, RegisterActivity.class);
                                          else {
                                              i = new Intent(SplashActivity.this, MainActivity.class);
                                              i.putExtra("username", username);
                                          }
                                          startActivity(i);
                                      } catch (ActivityNotFoundException e) {
                                          Log.i("nn", "ERROR: " + e.getMessage());
                                      } finally {
                                          finish();
                                      }
                                  }
                              }

                );
            }
        }

                , 2440);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AnimationDrawable anim = (AnimationDrawable) animContainer.getBackground();
        int count = anim.getNumberOfFrames();
        for (int i = 0; i < count; i++) {
            anim.getFrame(i).setCallback(null);
        }
        animContainer.getBackground().setCallback(null);
        animContainer = null;
    }

    private void startAnimation() {
        ((AnimationDrawable) animContainer.getBackground()).start();
    }
}
