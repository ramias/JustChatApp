package com.example.thomas.justchat.justchat.model;

import java.io.Serializable;

/**
 * Created by Rami on 2016-01-02.
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;

    private String sender;
    private String receiver;
    private String body;
    private String timestamp;

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return body;
    }
}
