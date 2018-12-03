package com.whime.dialog;

import javax.swing.JOptionPane;

public class aboutDialog{
	public aboutDialog()
	{
		String msg="版本：1.0\n"
				+ "开发平台：windows10\n"
				+ "数据库:Mysql\n"
				+ "开发接口：JDBC\n";
		JOptionPane.showMessageDialog(null,msg,"关于本程序",JOptionPane.PLAIN_MESSAGE);
	}
	
}
