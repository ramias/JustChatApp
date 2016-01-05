package com.justchatapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;

@ViewScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable {
	private String path = "http://130.237.84.211:8080/Faceoogle2/rest/";
	private static final long serialVersionUID = 1L;
	private String isMyFriend;
	private String paramUser = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
			.getRequest()).getParameter("user");
	private List<String> friendList;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String addFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getUsername());
		friend.put("friend", paramUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "profile.xhtml?faces-redirect=true" + "&user=" + paramUser;
	}

	public String removeFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getUsername());
		friend.put("friend", paramUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/removefriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "profile.xhtml?faces-redirect=true" + "&user=" + paramUser;
	}

	public String getIsMyFriend() {
		if(paramUser == null) {
			return "OWN";
		}
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getUsername());
		friend.put("friend", paramUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/isfriend");
		isMyFriend = resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return isMyFriend;
	}

	@SuppressWarnings("unchecked")
	public List<String> getFriendList() {
		RestClient client = new RestClient();
		Resource res = client.resource(path + "friend/friendlist?user=" + userBean.getUsername());
		String jsonFriends = res.accept("application/json").get(String.class);
		Gson gson = new Gson();
		friendList = gson.fromJson(jsonFriends, ArrayList.class);
		return friendList;
	}
}