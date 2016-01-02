package com.example.thomas.justchat.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.justchat.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private ListView memberList;
    private ArrayList<String> memberNameList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWelcome = (TextView) findViewById(R.id.txtViewWelcome);
        memberList = (ListView) findViewById(R.id.lv_members);

        memberNameList = new ArrayList<>();
        memberNameList.add("KALLE");
        memberNameList.add("PELLE");
        memberNameList.add("PETER");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, memberNameList);

        memberList.setAdapter(adapter);
        memberList.setOnItemClickListener(new MemberListListener());
        txtWelcome.setText("Welcome ");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("username");
            Log.i("nn", "Name: " + name);
            txtWelcome.setText("Welcome " + name);
        }
    }

    private class MemberListListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getBaseContext(), ChatActivity.class);
            i.putExtra("item", adapter.getItem(position));
            startActivity(i);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
