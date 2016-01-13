package com.justchatapp.vm;


public class ChatViewModel {
	private int id;
	private String sender;
	private String receiver;
	private String body;
	private String timestamp;
	
	public ChatViewModel(int id, String sender, String receiver, String body, String timestamp) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.body = body;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	
	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getBody() {
		return body;
	}

	public String getTimestamp() {
		return timestamp;
	}

}