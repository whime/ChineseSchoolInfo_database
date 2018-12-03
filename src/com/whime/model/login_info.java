package com.whime.model;

public class login_info {
	boolean loginSuccess=false;
	String id;
	String password;
	
	public void setID(String id)
	{
		this.id=id;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getID()
	{
		return id;
	}
	public String getPassword()
	{
		return password;
	}
	public void setLoginSuccess(boolean bo)
	{
		this.loginSuccess=bo;
	}
	public boolean getLoginSuccess()
	{
		return loginSuccess;
	}
}
