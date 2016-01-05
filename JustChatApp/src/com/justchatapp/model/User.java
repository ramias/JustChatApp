package com.justchatapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users", catalog = "faceoogle")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String regid;
	private List<User> friends;

	public User() {}

	public User(String username) {
		this.username = username;
	}

	public User(String username, String regid) {
		this.username = username;
		this.regid = regid;
		friends = new ArrayList<User>();
	}
	
	public User(String username, String regid, List<User> friends) {
		this.username = username;
		this.regid = regid;
		this.friends = friends;
	}

	@Id
	@Column(name = "Username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public void addFriend(User friend) {
		friends.add(friend);
	}

	public void removeFriend(User friend) {
		friends.remove(friend);
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "friends", catalog = "faceoogle", joinColumns = {
			@JoinColumn(name = "User", nullable = false, updatable = true, referencedColumnName = "Username") }, inverseJoinColumns = {
					@JoinColumn(name = "Friend", nullable = false, updatable = true, referencedColumnName = "Username") })
	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return username;
	}

	@Override
	public boolean equals(Object obj) {
		return username.equals(obj.toString());
	}
}
