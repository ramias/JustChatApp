package com.justchatapp.vm;

public class UserViewModel {
	private String username;
	private String regid;
	
	public UserViewModel(String username,String regid) {
		this.username = username;
		this.regid = regid;
	}
	
	public String getUsername() {
		return username;
	}

	public String getRegid() {
		return regid;
	}
}
