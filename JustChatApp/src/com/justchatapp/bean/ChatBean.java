package com.justchatapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;

import com.justchatapp.vm.ChatViewModel;

@ViewScoped
@ManagedBean(name = "chatBean")
public class ChatBean implements Serializable {
	private static final long serialVersionUID = 1L;
			
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	private ArrayList<ChatViewModel> chatMessages = new ArrayList<ChatViewModel>();
	private String paramUser;
	private String message;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ChatViewModel> getChatMessages() {
		if(paramUser != null) {
			RestClient client = new RestClient();
			Resource res = client.resource(path + "message/history?sender=" + userBean.getGmail() + "&receiver=" + paramUser);
			String jsonChat = res.accept("application/json").get(String.class);
			Gson gson = new Gson();
			chatMessages = gson.fromJson(jsonChat, ArrayList.class);
		}
		return chatMessages;
	}

	public void setChatMessages(ArrayList<ChatViewModel> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public String getParamUser() {
		return paramUser;
	}

	public void setParamUser() {
		this.paramUser = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("paramUser");
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String sendMessage() {
		if(paramUser == null) {
			return "index.jsf";
		}
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("sender", userBean.getGmail());
		msg.put("receiver", paramUser);
		msg.put("body", message);
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "message/sendmessage");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return null;
	}
}
