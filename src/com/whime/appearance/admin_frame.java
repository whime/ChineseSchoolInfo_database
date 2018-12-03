package com.whime.appearance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.whime.tool.deleteFrame;
import com.whime.tool.insertFrame;
import com.whime.tool.updateFrame;

public class admin_frame extends guest_frame {
	/**
	 * 管理员主窗口，有增删改查的功能
	 * 2018/11/24
	 * @author whime
	 */
	private static final long serialVersionUID = 6764137886050460322L;

	public admin_frame()
	{
		super();
		this.setVisible(true);
	}
	public void startWork()
	{
		super.startWork();
		deleteButton.setVisible(true);
		updateButton.setVisible(true);
		insertButton.setVisible(true);
		this.setTitle("管理员窗口");
		//为其他按钮增加监听
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new deleteFrame();
				//窗口手动关闭吧。。
				//也可以在外部加监听，不过好像会覆盖类内部的。
				//变量设置为public，把监听放在这里？想guest_frame里查询窗口一样
			}
		});
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new updateFrame();
			}
		});
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new insertFrame();
			}
		});
		
	}
	
//	public static void main(String[] args)
//	{
//		admin_frame af=new admin_frame();
//	}

}
