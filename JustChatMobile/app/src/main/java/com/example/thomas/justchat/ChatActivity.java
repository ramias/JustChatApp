package com.example.thomas.justchat;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Thomas on 2015-12-30.
 */
public class ChatActivity  extends AppCompatActivity {


    private TextView txtChatWith;
    private Button btnSend, btnClear;
    private EditText edtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("item");
            Log.i("nn", "Name: " + name);
            txtChatWith = (TextView) findViewById(R.id.txtChatWith);
            txtChatWith.setText("In chat with " + name);
        }

        edtInput = (EditText) findViewById(R.id.edt_input);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnClear = (Button) findViewById(R.id.btnClear);

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

                edtInput.setText("");
            }
        }
    }
}
