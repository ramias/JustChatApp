package com.justchatapp.vm;

import java.sql.Date;

public class UserViewModel {
	private String username;
	private String name;
	private Date birthDate;
	private String gender;
	
	public UserViewModel(String username,String name,Date birthdate,String gender) {
		this.username = username;
		this.name = name;
		this.birthDate = birthdate;
		this.gender = gender;
	}
	
	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public String getGender() {
		return gender;
	}
}
