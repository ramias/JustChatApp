package com.example.thomas.justchat.justchat.model;

import android.util.Log;

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

/**
 * Created by Rami on 2016-01-03.
 */
public class Requester {

    public static String makeRequest(String uri, String json, String method) {
        try {
            if (method.equals("POST")) {
                Log.i("rest", "POST EXECUTING");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader("Content-type", "application/json");
                String response = convertInputStreamToString((httpClient.execute(httpPost)).getEntity().getContent());
                Log.i("rest", "Post response: " + response);
                return response;
            } else if (method.equals("GET")) {
                Log.i("rest", "GET EXECUTING");
                String jsonResult = null;
                InputStream inputStream = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(uri);
                HttpResponse httpResponse = httpclient.execute(httpGet);
                if (httpResponse.getEntity() != null)
                    inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null)
                    jsonResult = convertInputStreamToString(inputStream);
                Log.i("rest", "result: " + jsonResult);
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        return result;

    }
}
