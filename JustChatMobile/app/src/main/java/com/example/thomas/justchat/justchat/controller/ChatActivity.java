package com.example.thomas.justchat.justchat.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.justchat.R;
import com.example.thomas.justchat.justchat.model.Message;
import com.example.thomas.justchat.justchat.model.MessageClient;

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
    private static ArrayAdapter<String> adapter;
    private static ArrayList<String> messageList;
    private String friendName, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            if (extras.getString("isPendingIntent").equals("true")) {
                // ChatActivity startades utifrån efter en push notis
                friendName = extras.getString("sender");
                username = extras.getString("receiver");
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
        adapter = new ArrayAdapter(this, R.layout.textview_message, messageList);
        listView = (ListView) findViewById(R.id.lv_chatHistory);
        edtInput = (EditText) findViewById(R.id.edt_input);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnClear = (Button) findViewById(R.id.btnClear);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String quote = ((TextView) view).getText().toString().substring(8);
                String time = ((TextView) view).getText().toString().substring(0, 5);
                edtInput.setText("Quote: '" + quote + "' at: " + time);
            }
        });

        btnSend.setOnClickListener(new OnSendBtnClickListener());
        btnClear.setOnClickListener(new OnClearBtnClickListener());

        btnCam = (Button) findViewById(R.id.btnCam);
        btnCam.setOnClickListener(new OnCameraBtnClickListener());

       getChatHistory();
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
                msg.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
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
                        updateChat(result);
                    }
                }.execute(msg, null, null);


                edtInput.setText("");
            }
        }
    }

    // Listener for Cam button.
    private class OnCameraBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent i = new Intent(getBaseContext(), CameraActivity.class);
            i.putExtra("friendName", friendName);
            startActivity(i);
        }
    }

    private void getChatHistory() {
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

    public static void updateChat(Message msg){
        if (msg != null) {
            messageList.add(formatTime(Calendar.getInstance().getTimeInMillis()) + " - " + msg.getSender() + ": " + msg.getBody());
            adapter.notifyDataSetChanged();
        }

    }
    private static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
}
