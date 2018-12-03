/*返回一个java.sql.Connection
 * 只需要每次传入用户名和密码
 */
package com.whime.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectToMysql {
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost:3306/university?useSSL=false&serverTimezone=UTC";
	//使用root用户登录验证其他用户
	static String user="root";
	static String PASS="root";

	public ConnectToMysql(String user,String passwd)
	{
		ConnectToMysql.user=user;
		ConnectToMysql.PASS=passwd;
	}
	public ConnectToMysql()
	{
		
	}
	public Connection connect() throws ClassNotFoundException {
		Connection conn=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(DB_URL,user,PASS);

		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
