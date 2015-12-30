package com.example.thomas.justchat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {

    ImageView animContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //animContainer = (ImageView) findViewById(R.id.imageview_animation_list_filling);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       // startAnimation();

        Handler nHandler = new Handler();
        nHandler.postDelayed(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           // animContainer=null;
                            //// TODO: 2015-12-30 if registred skip this step
                            Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                            startActivity(i);
                            finish();
                        } catch (ActivityNotFoundException e) {
                            Log.i("nn", "ERROR: " + e.getMessage());
                        }
                    }
                });
            }
        }, 1500);

        return true;
    }

    private void startAnimation() {
        ((AnimationDrawable) animContainer.getBackground()).start();
    }
}
