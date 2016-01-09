package com.justchatapp.bean;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	private static final long serialVersionUID = 1L;
	private String fullname;
	private String gmail;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String login() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put("username", gmail);
		user.put("phonenumber", fullname);
		Gson gson = new Gson();
		String json = gson.toJson(user);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "user/login");
		String response = resource.contentType("application/json").accept("text/plain").post(String.class, json);
		if (response.equals("200")) {
			return "index.xhtml";
		} else {
			return "login.jsf";
		}
	}

}