package com.tap.model;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;

public class User {
	int userid;
	private String userName;
	private	String password;
	private	String email;
	private	String address;
	private	String role;
	private	Timestamp createddate;
	private	Timestamp lastlogindate;
	public User() {
		
	}
	
	public User(int userid, String userName, String password, String email, String address, String role,
			Timestamp createddate, Timestamp lastlogindate) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
		this.createddate = createddate;
		this.lastlogindate = lastlogindate;
	}

	public User( String userName, String password, String email, String address, String role
			) {
		super();
		
	this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
		
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	public Timestamp getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(Timestamp lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", address=" + address + ", role=" + role + ", createddate=" + createddate + ", lastlogindate="
				+ lastlogindate + "]";
	}
	
	
	
	
}