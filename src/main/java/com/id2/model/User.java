package com.id2.model;

public class User {
	private int id;
	private String user;
	private String password;
	private String permissions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public User() {
		super();
	}
	public User(int id, String user, String password, String permissions) {
		super();
		this.id = id;
		this.user = user;
		this.password = password;
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "UserDao [id=" + id + ", user=" + user + ", password=" + password + ", permissions=" + permissions + "]";
	}
}
