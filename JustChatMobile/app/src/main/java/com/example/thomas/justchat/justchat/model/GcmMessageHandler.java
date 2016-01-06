package com.example.thomas.justchat.justchat.model;

/**
 * Created by Thomas on 2015-12-29.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.example.thomas.justchat.R;
import com.example.thomas.justchat.justchat.controller.ChatActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class GcmMessageHandler extends IntentService {

    private Handler handler;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }
    private Message msg;

    @Override
    public void onCreate() {

        super.onCreate();
        handler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        WakeUp.acquire(getApplicationContext());
        Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(200);
        msg = new Message();
        msg.setBody(extras.getString("body"));
        msg.setSender(extras.getString("sender"));
        msg.setReceiver(extras.getString("receiver"));
        msg.setTimestamp(extras.getString("timestamp"));

        Log.i("msg", "Recv: " + msg.getReceiver() + " send: " + msg.getSender());

        notifyNewMessage(msg);
        WakeUp.release();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ChatActivity.updateChat(msg);
            }
        });
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void notifyNewMessage(Message m) {

        String ticker = m.getBody();
        if(m!=null && m.getBody().length() >= 25){
            ticker = m.getBody().substring(0, 24).concat("...");
        }

        Intent i = new Intent(this, ChatActivity.class);
        // En idé..
        // om man kan få ut avsändaren i meddelandet så kan vi ladda in rätt chathistorik när vi kommer till ChatActivity
        // genom att lägga in username i intentet, putExtra("...").
        i.putExtra("sender",m.getSender());
        i.putExtra("receiver",m.getReceiver());
        // Inne i chatActivity bör vi kanske kolla om aktiviteten laddades från en push notis eller inte.
        i.putExtra("isPendingIntent","true");

        // Skapa ett avvaktande intent som triggas när en användare trycker på JustChat i sin status bar.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("JustChat - "+ticker)
                .setSmallIcon(R.drawable.notify_icon)
                .setContentTitle("New message from "+m.getSender()+"!") // You have a new message from ...
                .setContentText(ticker)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setLights(Color.BLUE, 500, 500)
                .setStyle(new NotificationCompat.InboxStyle())
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}