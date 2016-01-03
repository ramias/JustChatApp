package com.example.thomas.justchat.justchat.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
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
        Log.i("register", json);
        return Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/user/register", json, "POST");
    }

    public static String addFriend(String username, String friendName) {
        HashMap<String, String> friendMap = new HashMap<>();
        friendMap.put("user", username);
        friendMap.put("friend", friendName);
        Gson gson = new Gson();
        String json = gson.toJson(friendMap);
        Log.i("friend", json);
        return Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/friend/addfriend", json, "POST");
    }

    public static ArrayList<String> getFriendList(String username) {
        String jsonFriends = Requester.makeRequest("http://130.237.84.211:8080/justchat/rest/friend/friendlist?user=" + username, null, "GET");
        Gson gson = new Gson();
        return gson.fromJson(jsonFriends, ArrayList.class);
    }

}
