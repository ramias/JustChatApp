package com.example.thomas.justchat.justchat.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
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
import com.example.thomas.justchat.justchat.model.Message;
import com.example.thomas.justchat.justchat.model.MessageClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Thomas on 2015-12-30.
 */
public class ChatActivity extends AppCompatActivity {


    private TextView txtChatWith;
    private Button btnSend, btnClear, btnCam;
    private EditText edtInput;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> messageList;
    private ArrayList<Message> messageObjList;
    private String friendName, username;
    private File file;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            if (extras.getBoolean("isPendingIntent")) {
                // ChatActivity startades utifrån efter en push notis
                friendName = extras.getString("sender");
                Log.i("push", friendName);
            } else {
                // ChatActivity startades från mainActivity
                friendName = extras.getString("item");
                username = extras.getString("username");
            }

            txtChatWith = (TextView) findViewById(R.id.txtChatWith);
            txtChatWith.setText("In chat with " + friendName);

        }

        messageList = new ArrayList<>();
        messageObjList = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.textview_message, messageList);
        listView = (ListView) findViewById(R.id.lv_chatHistory);
        edtInput = (EditText) findViewById(R.id.edt_input);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnClear = (Button) findViewById(R.id.btnClear);

        listView.setAdapter(adapter);
        btnSend.setOnClickListener(new OnSendBtnClickListener());
        btnClear.setOnClickListener(new OnClearBtnClickListener());

        btnCam = (Button) findViewById(R.id.btnCam);
        btnCam.setOnClickListener(new OnCamTestBtnClickListener());
        file = new File(Environment.getExternalStorageDirectory(), "test_pic.jpg");
        Uri outputFileUri = Uri.fromFile(file);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        new AsyncTask<Void, Void, ArrayList>() {
            @Override
            protected ArrayList doInBackground(Void... params) {
                ArrayList<Message> messages = MessageClient.getChatMessages(username, friendName);
                ArrayList<String> parsedMessages = new ArrayList<>();
                if (messages != null) {
                    for (Message m : messages) {
                        parsedMessages.add(m.getTimestamp() + " - " + m.getSender() + ": " + m.getBody());
                    }
                }
                return parsedMessages;
            }

            @Override
            protected void onPostExecute(ArrayList result) {
                Log.i("message", "resultset: " + result);
                if (result != null) {
                    messageList.addAll(result);
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute(null, null, null);
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
            if (edtInput.getText().toString().equals("")) {
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
                Toast.makeText(getApplicationContext(), "Enter a message!", Toast.LENGTH_LONG).show();
            } else {
                Message msg = new Message();
                msg.setSender(username);
                msg.setReceiver(friendName);
                msg.setBody(edtInput.getText().toString());
                new AsyncTask<Message, Void, Message>() {
                    @Override
                    protected Message doInBackground(Message... params) {
                        if (MessageClient.sendMessage(params[0]) != "-1")
                            return params[0];
                        else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Message result) {
                        Log.i("message", "resultset: " + result);
                        if (result != null) {
                            messageList.add(getTime() + " - " + result.getSender() + ": " + result.getBody());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }.execute(msg, null, null);


                edtInput.setText("");
            }
        }
    }

    // Listener for Cam button.
    private class OnCamTestBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            startActivityForResult(intent, 1);
        }
    }


    private String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }
}
