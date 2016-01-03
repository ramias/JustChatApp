package com.example.thomas.justchat.justchat.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Rami on 2016-01-03.
 */
public class UserClient {
    public static String register(User user) {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("username", user.getUsername());
        userMap.put("regid", user.getRegid());
        userMap.put("phonenumber", user.getPhonenumber());
        Gson gson = new Gson();
        String json = gson.toJson(userMap);
        Log.i("register",json);
        return Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/user/register", json, "POST");
    }
}
