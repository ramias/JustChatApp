package com.justchatapp.vm;


public class ChatViewModel {
	private String chater;
	private String chatee;
	private String message;
	
	public ChatViewModel(String chater, String chatee, String message) {
		this.chater = chater;
		this.chatee = chatee;
		this.message = message;
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
}