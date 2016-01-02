package com.example.thomas.justchat.justchat.controller;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.justchat.R;

import java.util.ArrayList;


/**
 * Created by Thomas on 2015-12-30.
 */
public class ChatActivity  extends AppCompatActivity {


    private TextView txtChatWith;
    private Button btnSend, btnClear;
    private EditText edtInput;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> messageList;
    private String friendName, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            friendName = extras.getString("item");
            username = extras.getString("username");
            Log.i("nn", "Name: " + friendName);
            txtChatWith = (TextView) findViewById(R.id.txtChatWith);
            txtChatWith.setText("In chat with " + friendName);
        }

        messageList = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.textview_message,messageList);
        listView = (ListView) findViewById(R.id.lv_chatHistory);
        edtInput = (EditText) findViewById(R.id.edt_input);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnClear = (Button) findViewById(R.id.btnClear);

        listView.setAdapter(adapter);
        btnSend.setOnClickListener(new OnSendBtnClickListener());
        btnClear.setOnClickListener(new OnClearBtnClickListener());

    }

    // Listener for clear button.
    private class OnClearBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            edtInput.setText("");
        }
    }

    // Listener for send button.
    private class OnSendBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if(edtInput.getText().toString().equals("")){
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
                Toast.makeText(getApplicationContext(), "Enter a message!", Toast.LENGTH_LONG).show();
            }else {
                // Call SendMsg() -->
                newMessageToListView(username,edtInput.getText().toString());
                edtInput.setText("");
            }
        }
    }

    private void newMessageToListView(String user, String msg) {
        messageList.add(user+": "+msg);
        adapter.notifyDataSetChanged();
    }
}
