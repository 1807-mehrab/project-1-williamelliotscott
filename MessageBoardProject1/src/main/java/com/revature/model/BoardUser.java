package com.revature.model;

public class BoardUser {
	private String username;
	private String userpass;
	private String userdesc;
	private boolean isAdmin;
	
	public BoardUser(String username, String userpass, String userdesc, boolean isAdmin) {
		super();
		this.username = username;
		this.userpass = userpass;
		this.userdesc = userdesc;
		this.isAdmin = isAdmin;
	}
	
	public BoardUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserdesc() {
		return userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc;
	}
	
	
}
