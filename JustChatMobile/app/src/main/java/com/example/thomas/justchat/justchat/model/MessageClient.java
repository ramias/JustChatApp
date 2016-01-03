package com.example.thomas.justchat.justchat.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rami on 2016-01-02.
 */
public class MessageClient {
    private static String json;
    private static ArrayList<Message> chatMessages;


    public static void sendMessage(Message message) {
        Map<String, String> msg = new HashMap<>();
        msg.put("chater", message.getSender());
        msg.put("chatee", message.getReceiver());
        msg.put("message", message.getBody());
        Gson gson = new Gson();
        json = gson.toJson(msg);
        PostWorker pw = new PostWorker();
        pw.execute("http://130.237.84.211:8080/Faceoogle2/rest/chat/sendmessage");

    }

    public static void getChatMessages(String sender, String receiver, ArrayList<String> messageList) {
        GetWorker gw = new GetWorker(messageList);
        gw.execute("http://130.237.84.211:8080/Faceoogle2/rest/chat/history?chater=" + sender + "&chatee=" + receiver);
    }

    // source: http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient ; user: JJD
    private static String makeRequest(String uri, String json, String method) {
        try {
            if (method.equals("POST")) {
                Log.i("rest", "POST EXECUTING");
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                return String.valueOf(new DefaultHttpClient().execute(httpPost));
            } else if (method.equals("GET")) {
                Log.i("rest", "GET EXECUTING");
                String jsonResult = "";
                InputStream inputStream;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(uri);
                HttpResponse httpResponse = httpclient.execute(httpGet);
                inputStream = httpResponse.getEntity().getContent();
                Log.i("inputstream", inputStream.toString());
                if (inputStream != null)
                    jsonResult = convertInputStreamToString(inputStream);
                else
                    jsonResult = "Did not work!";
                return jsonResult;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;

        }
        inputStream.close();
        return result;

    }

    private static class PostWorker extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Log.i("rest", params[0]);
            makeRequest(params[0], json, "POST");
            Log.i("rest", "Come here 2?");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("rest", "sent");
        }

    }

    private static class GetWorker extends AsyncTask<String, Void, String> {
        private static ArrayList<String> messageList;

        public GetWorker(ArrayList<String> messageList) {
            this.messageList = messageList;
        }

        @Override
        protected String doInBackground(String... params) {
            String result;
            result = makeRequest(params[0], null, "GET");
            Log.i("rest", "Come here 2?");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("rest", "received");
            Log.i("rest", "Result: " + result);
            Gson gson = new Gson();
            messageList = gson.fromJson(result, ArrayList.class);
            if (messageList != null)
                Log.i("rest", Arrays.toString(messageList.toArray()));

        }

    }
}
