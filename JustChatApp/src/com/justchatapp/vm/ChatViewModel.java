package com.justchatapp.vm;


public class ChatViewModel {
	private int id;
	private String chater;
	private String chatee;
	private String message;
	private String timestamp;
	
	public ChatViewModel(int id, String chater, String chatee, String message, String timestamp) {
		this.id = id;
		this.chater = chater;
		this.chatee = chatee;
		this.message = message;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getChater() {
		return chater;
	}

	public String getChatee() {
		return chatee;
	}

	public String getMessage() {
		return message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}