package com.justchatapp.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {
	private String path = "http://130.237.84.211:8080/Faceoogle2/rest/";
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String name;
	private Date birthdate;
	private String gender;
	private String loginMessage, registerMessage;
	private boolean loggedIn;

	public java.util.Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(java.util.Date birthdate) {
		this.birthdate = new java.sql.Date(birthdate.getTime());
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getRegisterMessage() {
		return registerMessage;
	}

	public void setRegisterMessage(String registerMessage) {
		this.registerMessage = registerMessage;
	}
	
	public String login() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("username", username);
		user.put("password", password);
		Gson gson = new Gson();
		String json = gson.toJson(user);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "user/login");
		String response = resource.contentType("application/json").accept("text/plain").post(String.class, json);
		if (response.equals("200")) {
			loginMessage = "";
			loggedIn = true;
			return "index.xhtml";
		} else {
			loginMessage = "Wrong username or password";
			loggedIn = false;
			return "login.xhtml";
		}
	}

	public String register() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("username", username);
		user.put("password", password);
		user.put("name", name);
		user.put("birthdate", String.valueOf(birthdate));
		user.put("gender", gender);
		Gson gson = new Gson();
		String json = gson.toJson(user);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "user/register");
		String response = resource.contentType("application/json").accept("text/plain").post(String.class, json);
		System.out.println("AAWDO " + response);
		
		if (response.equals("200")) {
			registerMessage = "";
			return "index.xhtml";
		} else {
			registerMessage = "Please fill in all the fields in the correct format";
			return "register.xhtml";
		}
	}

	public String nodejs() {
		return "nodejs.xhtml";
	}
	
	public String vertx() {
		return "vertx.xhtml";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		loggedIn = false;
		return "login.xhtml";
	}

	public String gotoMyProfile() {
		return "profile.xhtml?faces-redirect=true" + "&user=" + username;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
