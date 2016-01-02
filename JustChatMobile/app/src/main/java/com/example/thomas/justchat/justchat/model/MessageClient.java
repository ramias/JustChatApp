package com.example.thomas.justchat.justchat.model;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rami on 2016-01-02.
 */
public class MessageClient {
    private static void sendMessage(String sender, String receiver, String body) {
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("sender", sender);
        msg.put("receiver", receiver);
        msg.put("body", body);
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        makeRequest("http://130.237.84.211:8080/Faceoogle2/rest/chat/sendmessage", json);
    }

    // source: http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient ; user: JJD
    public static HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
