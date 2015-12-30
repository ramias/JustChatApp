package com.example.thomas.justchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWelcome = (TextView) findViewById(R.id.txtVeiwWelcome);
        txtWelcome.setText("Welcome ");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("username");
            Log.i("nn", "Name: " + name);
            txtWelcome.setText("Welcome " + name);
        }
    }
}
