package com.whime.appearance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.whime.model.login_info;
import com.whime.tool.loginHandler;

/**
 * 登陆界面，用于验证用户名和密码
 * @author whime
 */
public class login_appearance extends JFrame implements ActionListener {
	//登陆界面
	private static final long serialVersionUID = 1L;
	static login_info info;
	static JLabel mainTitle;
	static JTextField inputID;
	static JPasswordField inputPassword;
	static JLabel userLabel,passwdLabel;
	static JButton buttonLogin;
	static JButton guestLogin;
	boolean loginSuccess;
	guest_frame guestWindow;
	
	
	public static void main(String[] args) {
		//使用BeautyEye美化窗体，参考
		//https://github.com/JackJiang2011/beautyeye/wiki/BeautyEye-L&F%E7%AE%80%E6%98%8E%E5%BC%80%E5%8F%91%E8%80%85%E6%8C%87%E5%8D%97
		try
	    {
			 //设置本属性将改变窗口边框样式定义
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        //隐藏设置按钮
	        UIManager.put("RootPane.setupButtonVisible",false);
	        //改变JTabbedPane 左缩进
//	        UIManager.put("TabbedPane.tabAreaInsets"
//	        	    , new javax.swing.plaf.InsetsUIResource(3,20,2,20));
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		login_appearance appear=new login_appearance();
		
	}
	
	
	
	public login_appearance()
	{
		
		mainTitle=new JLabel("中国高校信息系统");
		mainTitle.setBounds(730,300,450,60);
		mainTitle.setFont(new Font("宋体",Font.ITALIC,50));
		mainTitle.setForeground(Color.ORANGE);
		
		
		info=new login_info();
		//user提示label
		userLabel=new JLabel("User:");
		userLabel.setFont(new Font("宋体",Font.BOLD,20));
		userLabel.setBounds(700,400,80,40);
		//userLabel.setPreferredSize(new Dimension(80,25));
		
		//用户名输入框
		inputID=new JTextField(20);
		inputID.setBounds(800,400,350,40);
		//inputID.setPreferredSize(new Dimension(160,25));
		
		//password提示label
		passwdLabel=new JLabel("password:");
		passwdLabel.setFont(new Font("宋体",Font.BOLD,20));
		passwdLabel.setBounds(700,480,100,40);
		//passwdLabel.setPreferredSize(new Dimension(80,25));
		
		//密码输入框
		inputPassword=new JPasswordField(20);
		inputPassword.setBounds(800,480,350,40);
		//inputPassword.setPreferredSize(new Dimension(160,25));
		
		//游客登陆按钮，只有查询权限
		guestLogin=new JButton("Guest");
		guestLogin.setBounds(900,580,100,40);
		guestLogin.addActionListener(this);
		
		buttonLogin=new JButton("Login");
		buttonLogin.setBounds(1050,580,100,40);
		buttonLogin.addActionListener(this);
		//使用下面的语句界面感觉更丑。		
		//JFrame.setDefaultLookAndFeelDecorated(true);
		
		setLayout(new GridLayout());
		this.setTitle("中国高校信息系统");
		
		double frameWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double frameHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setSize((int)frameWidth, (int)frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		JPanel panel=new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(500,400));
//		panel.setBackground(Color.gray);
		placeComponents(panel);
		add(panel,BorderLayout.CENTER);
		


	}
	
	private static void placeComponents(JPanel panel)
	{
		panel.add(mainTitle);
		panel.add(userLabel);
		panel.add(inputID);
		
		
		panel.add(passwdLabel);
		panel.add(inputPassword);
		
		panel.add(buttonLogin);
		panel.add(guestLogin);
		
	}
	
	public boolean isLoginSuccess()
	{
		return loginSuccess;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buttonLogin)
		{
			info.setID(inputID.getText());
			char[] pw=inputPassword.getPassword();
			info.setPassword(new String(pw));
			info=new loginHandler().loginVerify(info);
			
			//不能在登陆信息处填写guest用户名来登陆，不然会有管理员权限
			//比较字符串不能用！=,应该用equals。。。
			if(info.getLoginSuccess()==true&&!(info.getID().equals("guest")))
			{
				
				this.dispose();
				admin_frame adminWindow=new admin_frame();
				adminWindow.startWork();
				
			}
			else
			{
				JOptionPane.showMessageDialog(null,"用户名或密码错误！","登陆失败",JOptionPane.WARNING_MESSAGE);
			}
		}
		else {
			info.setID("guest");
			info.setPassword("guest");
			info=new loginHandler().loginVerify(info);
			if(info.getLoginSuccess()==true)
			{
				
				this.dispose();
				guest_frame guestWindow=new guest_frame();
				guestWindow.startWork();
				
			}
			else
			{
//				JOptionPane.showMessageDialog(null,"用户名或密码错误！","登陆失败",JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
}
