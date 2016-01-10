package com.example.thomas.justchat.justchat.model;

import java.io.Serializable;

/**
 * Created by Rami on 2016-01-03.
 */
public class User implements Serializable {
    private String username;
    private String regId;
    private String phonenumber;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }


    public User(String username, String regId, String phonenumber) {
        this.username = username;
        this.regId = regId;
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        return username.equals(obj.toString());
    }
}
