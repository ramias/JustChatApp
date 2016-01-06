package com.example.thomas.justchat.justchat.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rami on 2016-01-02.
 */
public class MessageClient {


    public static String sendMessage(Message message) {
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("sender", message.getSender());
        msgMap.put("receiver", message.getReceiver());
        msgMap.put("body", message.getBody());
        msgMap.put("timestamp", message.getTimestamp());
        Log.i("encodeing", "message: " + msgMap.get("body"));
        Log.i("encodeing", "map :" + msgMap.get("body"));
        Gson gson = new Gson();
        String json = gson.toJson(msgMap);
        return Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/message/sendmessage", json, "POST");

    }

    public static ArrayList<Message> getChatMessages(String sender, String receiver) {
        String jsonMessages = Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/message/history?sender=" + sender + "&receiver=" + receiver, null, "GET");
        if (jsonMessages != null) {
            Gson gson = new Gson();
            return messageParser(gson.fromJson(jsonMessages, ArrayList.class));
        } else return null;
    }

    private static ArrayList<Message> messageParser(ArrayList<LinkedTreeMap> unparsed) {
        ArrayList<Message> messageList = new ArrayList<>();
        for (LinkedTreeMap h : unparsed) {
            Message m = new Message();
            m.setSender((String) h.get("sender"));
            m.setBody((String) h.get("body"));
            m.setTimestamp(dateFormater((String) h.get("timestamp")));
            messageList.add(m);
        }
        return messageList;
    }

    private static String dateFormater(String date) {
        Date d = new Date();
        Log.i("date", "" + date);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"), sqf = new SimpleDateFormat("yyyy-mm-dd HH:mm:SSSS");
        try {
            d = sqf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(d);
    }


}
