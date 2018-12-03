/*
 * 处理登陆信息
 */
package com.whime.tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.whime.model.login_info;;
public class loginHandler {
	Connection conn;
	PreparedStatement presql;
	ResultSet rs;
	
	public loginHandler()
	{
		try {
			conn=new ConnectToMysql("root","root").connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public login_info loginVerify(login_info INFO)
	{
		//返回一个login_info对象，修改了loginSuccess字段
		String id=INFO.getID();
		String pw=INFO.getPassword();
		String sqlStr="select userName,passwd from user where"+" userName=? and passwd=?";
		try {
			presql=conn.prepareStatement(sqlStr);
			presql.setString(1, id);
			presql.setString(2, pw);
			rs=presql.executeQuery();
			if(rs.next()==true)
			{
				INFO.setLoginSuccess(true);
				
			}
			else
			{
				INFO.setLoginSuccess(false);
	
			}
			conn.close();
		}
		catch(SQLException e)
		{
			
		}
		return INFO;
	}
}
