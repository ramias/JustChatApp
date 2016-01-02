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
                            //// TODO: 2015-12-30 if registred skip this step
                            Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            Log.i("nn", "ERROR: " + e.getMessage());
                        }finally {
                            finish();
                        }
                    }
                });
            }
        }, 1500);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        AnimationDrawable anim = (AnimationDrawable) animContainer.getBackground();
        int count = anim.getNumberOfFrames();
        for(int i=0;i<count;i++){
            anim.getFrame(i).setCallback(null);
        }
        animContainer.getBackground().setCallback(null);
        animContainer = null;
    }

    private void startAnimation() {
        ((AnimationDrawable) animContainer.getBackground()).start();
    }
}
