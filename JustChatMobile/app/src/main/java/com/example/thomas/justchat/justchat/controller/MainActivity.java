package com.example.thomas.justchat.justchat.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.justchat.R;
import com.example.thomas.justchat.justchat.model.UserClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private ListView memberList;
    private Button btnAddFriend, btnAddPhoneNr;
    private ArrayList<String> memberNameList;
    private ArrayAdapter<String> adapter;
    private String username;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWelcome = (TextView) findViewById(R.id.txtViewWelcome);
        memberList = (ListView) findViewById(R.id.lv_members);
        btnAddFriend = (Button) findViewById(R.id.btn_addFriend);
        btnAddPhoneNr = (Button) findViewById(R.id.btnPhoneNr);
        btnAddFriend.setOnClickListener(new OnAddFriendListener());
        btnAddPhoneNr.setOnClickListener(new OnAddPhoneNrBtnClickListener());
        memberNameList = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.textview_friends, memberNameList);

        memberList.setAdapter(adapter);
        memberList.setOnItemClickListener(new MemberListListener());
        txtWelcome.setText("Welcome ");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            Log.i("nn", "Name: " + username);
            txtWelcome.setText("Welcome " + username);
        }
        getFriends();
    }

    private void getFriends() {
        new AsyncTask<Void, Void, ArrayList>() {
            @Override
            protected ArrayList doInBackground(Void... params) {
                return UserClient.getFriendList(username);
            }

            @Override
            protected void onPostExecute(ArrayList result) {
                Log.i("friend", "resultset: " + result);
                if (result != null) {
                    memberNameList.addAll(result);
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute(null, null, null);
    }

    // Listener for Add phone nr button.
    private class OnAddPhoneNrBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.prompt_addphone, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.et_phoneInput);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (userInput.length() > 9) {
                                        new AsyncTask<String, Void, String>() {
                                            @Override
                                            protected String doInBackground(String... params) {
                                                return UserClient.addPhone(username, params[0]);
                                            }

                                            @Override
                                            protected void onPostExecute(String result) {
                                                if (result.equals("200")) {
                                                    Toast.makeText(getApplicationContext(), "Number stored!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "This number is taken!", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        }.execute(userInput.getText().toString(), null, null);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    // Listener for clear button.
    private class OnAddFriendListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.prompt_addfriend, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.et_friendInput);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (userInput.length() > 0) {
                                        Toast.makeText(getApplicationContext(), "Establishing friendship with " + userInput.getText() + "...", Toast.LENGTH_LONG).show();
                                        new AsyncTask<String, Void, String>() {
                                            @Override
                                            protected String doInBackground(String... params) {
                                                return UserClient.addFriend(username, params[0]);
                                            }

                                            @Override
                                            protected void onPostExecute(String result) {
                                                if (result.equals("true")) {
                                                    memberNameList.add(userInput.getText().toString());
                                                    adapter.notifyDataSetChanged();
                                                    Toast.makeText(getApplicationContext(), "Friendship with " + userInput.getText() + " established!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "User: '" + userInput.getText() + "' does not exist", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        }.execute(userInput.getText().toString(), null, null);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }


    private class MemberListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getBaseContext(), ChatActivity.class);
            i.putExtra("item", adapter.getItem(position));
            i.putExtra("username", username);
            i.putExtra("isPendingIntent", "false");
            startActivity(i);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
