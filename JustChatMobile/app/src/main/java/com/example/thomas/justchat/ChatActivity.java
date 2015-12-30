package com.example.thomas.justchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by Thomas on 2015-12-30.
 */
public class ChatActivity  extends AppCompatActivity {


    private TextView txtChatWith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtChatWith = (TextView) findViewById(R.id.txtChatWith);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("item");
            Log.i("nn", "Name: " + name);
            txtChatWith.setText("In chat with " + name);
        }
    }
}
