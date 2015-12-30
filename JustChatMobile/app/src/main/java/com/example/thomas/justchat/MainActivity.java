package com.example.thomas.justchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        txtWelcome.setText("Welcome ");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("username");
            Log.i("nn", "Name: " + name);
            txtWelcome.setText("Welcome " + name);
        }



    }
}
