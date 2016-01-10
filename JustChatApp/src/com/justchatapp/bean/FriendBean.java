package com.justchatapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;

@ViewScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	private String friendUser = "";
	private ArrayList<String> friendList;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(String friendUser) {
		this.friendUser = friendUser;
	}

	public String addFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getGmail());
		friend.put("friend", friendUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getFriendList() {
		RestClient client = new RestClient();
		Resource res = client.resource(path + "friend/friendlist?user=" + userBean.getGmail());
		String jsonFriends = res.accept("application/json").get(String.class);
		Gson gson = new Gson();
		friendList = gson.fromJson(jsonFriends, ArrayList.class);
		return friendList;
	}
}