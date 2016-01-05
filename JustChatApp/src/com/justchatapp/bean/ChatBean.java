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

import com.justchatapp.vm.ChatViewModel;

@ViewScoped
@ManagedBean(name = "chatBean")
public class ChatBean implements Serializable {
	private String path = "http://130.237.84.211:8080/Faceoogle2/rest/";
	private static final long serialVersionUID = 1L;
	private List<ChatViewModel> chatMessages = new ArrayList<ChatViewModel>();
	private String paramUser = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
			.getRequest()).getParameter("user");
	private String message;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChatViewModel> getChatMessages() {
		RestClient client = new RestClient();
		Resource res = client.resource(path + "chat/history?chater=" + userBean.getUsername() + "&chatee="+paramUser);
		String jsonChat = res.accept("application/json").get(String.class);
		Gson gson = new Gson();
		chatMessages = gson.fromJson(jsonChat, ArrayList.class);
		return chatMessages;
	}

	public void setChatMessages(List<ChatViewModel> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public String getParamUser() {
		return paramUser;
	}

	public void setParamUser(String paramUser) {
		this.paramUser = paramUser;
	}
	
	public String sendMessage() {
		if(paramUser == null) {
			return "index.xhtml";
		}
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("chater", userBean.getUsername());
		msg.put("chatee", paramUser);
		msg.put("message", message);
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "chat/sendmessage");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
