package com.example.thomas.justchat.justchat.model;

import java.io.Serializable;

/**
 * Created by Rami on 2016-01-03.
 */
public class User implements Serializable {
    private String username;
    private String regid;
    private String phonenumber;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }


    public User(String username, String regid, String phonenumber) {
        this.username = username;
        this.regid = regid;
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
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
