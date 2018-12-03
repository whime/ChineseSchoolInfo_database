package com.whime.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class queryFrame extends JFrame{
	/**
	 * 查询的主窗口，用以输入信息
	 * @author whime
	 * 2018/11/23
	 */
	private static final long serialVersionUID = -3566835163939282663L;
	JPanel panel;
	public JTextField schoolName;
	JTextField city;
	JCheckBox Is211,Is985,IsStateRun;
	JComboBox<String> subjectType,collegeType;
	String[] subjects= {"--学科类型--","综合类院校","语言类院校","工科类院校","医药类院校","体育类院校","政法类院校","农业类院校","财经类院校",
			"艺术类院校","师范类院校","林业类院校","民族类院校","军事类院校","政党类院校"};
	String[] colleges= {"--院校类型--","高等职业技术学校","高等专科学校","独立学院","大学","学院","高等学校分校","成人高等学校",
			"短期职业大学","管理干部学院","教育学院"};
	JLabel schoolLabel,cityLabel;
	public JButton fuzzyMatch;//模糊查询按钮和完全匹配查询按钮
	public JButton query;
		
	/**
	 * 数据库查询相关的变量
	 */
	public String sql="select * from school where";
	Connection conn;
	ResultSet[] rs=new ResultSet[2];//用于返回学校和城市的查询结果
	
	public queryFrame()
	{
		try {
			conn=new ConnectToMysql("root","root").connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//设置窗口关闭时只关闭查询窗口。
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
        GridBagConstraints c = null;                    // 约束

		panel=new JPanel(gridBag);
		panel.setSize(700,700);
		
		schoolLabel= new JLabel("学校");
		schoolLabel.setFont(new Font("宋体",Font.BOLD,14));
		cityLabel=new JLabel("城市");
		cityLabel.setFont(new Font("宋体",Font.BOLD,14));
		schoolName=new JTextField("学校名称");//相当于提示字符
		schoolName.setForeground(Color.gray);
		schoolName.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e)
			{
				JTextField src=(JTextField)e.getSource();
				src.setForeground(Color.BLACK);
				if(src.getText().equals("学校名称"))
				src.setText(null);
			}
			public void focusLost(FocusEvent e)
			{
				JTextField src=(JTextField)e.getSource();
				if(src.getText().trim().isEmpty())
				{
					src.setText("学校名称");
					src.setForeground(Color.gray);
				}
			}
		});
		
		city=new JTextField("XX市");//city输入框也设置相同的监听。
		city.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e)
			{
				JTextField src=(JTextField)e.getSource();
				src.setForeground(Color.BLACK);
				if(src.getText().equals("XX市"))
				src.setText(null);
			}
			public void focusLost(FocusEvent e)
			{
				JTextField src=(JTextField)e.getSource();
				if(src.getText().trim().isEmpty())
				{
					src.setText("XX市");
					src.setForeground(Color.gray);
				}
			}
		});
		//默认复选框选中
		Is211=new JCheckBox("211");
		Is211.setSelected(true);
		Is985=new JCheckBox("985");
		Is985.setSelected(true);
		IsStateRun=new JCheckBox("公办");
		IsStateRun.setSelected(true);
		
		subjectType=new JComboBox<String>(subjects);
		collegeType=new JComboBox<String>(colleges);
		//为模糊查询添加响应事件
		fuzzyMatch=new JButton("模糊查询");
//		fuzzyMatch.addActionListener(new  ActionListener() {
//			public void actionPerformed(ActionEvent e)
//			{
//				//完全匹配查询
//				sql+=" schoolName='"+schoolName.getText()+"'";
//			} 
//		});

		
		
		query=new JButton("精确查询");
//		query.addActionListener(new  ActionListener() {
//			public void actionPerformed(ActionEvent e)
//			{
//				//完全匹配查询
//				sql+=" schoolName='"+schoolName.getText()+"'";
//			} 
//		});
		
		//添加组件和约束到布局管理器
		
		//学校标签
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(18,50,10,0);
		gridBag.addLayoutComponent(schoolLabel,c);
		
		//学校文本框组件
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.weightx=2;
		c.weighty=1;
		c.insets=new Insets(10,90,10,100);
		c.gridwidth=GridBagConstraints.REMAINDER;
		c.gridheight=4;
		c.fill=GridBagConstraints.BOTH;
		gridBag.addLayoutComponent(schoolName,c);
		
		//城市标签
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=5;
		c.insets=new Insets(18,50,10,0);
		gridBag.addLayoutComponent(cityLabel,c);
		
		//城市名称文本框
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=5;
		c.weightx=2;
		c.weighty=1;
		c.insets=new Insets(10,90,5,100);
		c.gridheight=4;
		c.gridwidth=GridBagConstraints.REMAINDER;
		c.fill=GridBagConstraints.BOTH;
		gridBag.addLayoutComponent(city,c);
		
		//三个复选框放在一行
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=10;
		c.weightx=4;
		c.weighty=4;
		c.insets=new Insets(5,30,10,20);
		c.gridheight=2;
		c.gridwidth=2;
        gridBag.addLayoutComponent(Is211, c);
        
        c = new GridBagConstraints();
        c.gridx=5;
        c.gridy=10;
        c.weightx=4;
		c.weighty=4;
		c.insets=new Insets(5,20,10,20);
        c.gridheight=2;
        c.gridwidth=2;
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.addLayoutComponent(Is985, c);
        
        c = new GridBagConstraints();
        c.gridx=10;
        c.gridy=10;
        c.weightx=4;
		c.weighty=4;
		c.insets=new Insets(5,20,10,30);
        c.gridheight=2;
        c.gridwidth=2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(IsStateRun, c);
        
        //学科类型下拉列表
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=15;
        c.weightx=5;
		c.weighty=5;
		c.insets=new Insets(5,50,5,50);
        c.gridheight=4;
        c.gridwidth=GridBagConstraints.RELATIVE;
//        c.fill=GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(subjectType, c);
        
        
        //院校类型下拉列表
        c=new GridBagConstraints();
        c.gridx=20;
        c.gridy=15;
        c.weightx=5;
		c.weighty=5;
		c.insets=new Insets(5,50,5,50);
        c.gridheight=4;
        c.gridwidth=GridBagConstraints.REMAINDER;
//        c.fill=GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(collegeType,c);
        
     //模糊查询按钮
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=19;
        c.insets=new Insets(5,100,2,50);
        c.gridwidth=GridBagConstraints.RELATIVE;
//        c.anchor=GridBagConstraints.WEST;
        gridBag.addLayoutComponent(fuzzyMatch,c); 
        
        //完全匹配查询按钮
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=19;
        c.insets=new Insets(5,300,2,50);
        c.gridwidth=GridBagConstraints.REMAINDER;
//        c.anchor=GridBagConstraints.EAST;
        gridBag.addLayoutComponent(query,c);
 
        //添加组件到内容面板
        panel.add(schoolLabel);
        panel.add(schoolName);
        panel.add(cityLabel);
        panel.add(city);
        panel.add(Is211);
        panel.add(Is985);
        panel.add(IsStateRun);
        panel.add(subjectType);
        panel.add(collegeType);
        panel.add(fuzzyMatch);
        panel.add(query);
        
        
		this.setContentPane(panel);
		this.setLocationRelativeTo(null);
		this.setLocation(600, 300);
		this.setTitle("查询");
		this.pack();
		this.setSize(550, 400);
//		this.setResizable(true);
		this.setVisible(true);
		
	}
	
	//执行Sql语句并返回ResultSet
	public ResultSet[] execQuery()
	{
		formSql();
		try {
			//返回一个可滚动的数据集以便使用游标
			Statement querySQL1=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement querySQL2=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs[0]=querySQL1.executeQuery(sql);
			if(!(city.getText().equals("XX市")))
			{
				//在这里发现一个问题，就是一个Statement最好对应一个ResultSet,
				//除非即使处理了前面的resultset，否则再度executeQuery之后前面的resultset会失去连接，
				//出现 Operation not allowed after ResultSet closed 的错误。
				String cityText=city.getText();
				rs[1]=querySQL2.executeQuery("select * from city where cityName='"+cityText+"';");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	
	
		
		return rs;
	}
	
	//组成sql语句
	public void formSql()
	{
		if(Is211.isSelected())
		{
			sql+=" is211='是' and";
		}
		else
		{
			sql+=" is211='否' and";
		}
		if(Is985.isSelected())
		{
			sql+=" is985='是' and";
		}
		else
		{
			sql+=" is985='否' and";
		}
		if(IsStateRun.isSelected())
		{
			sql+=" state_run='是' and";
		}
		else
		{
			sql+=" state_run='否' and";
		}
		
		
		//注意这里的单引号！！！
		
		if(!(subjectType.getSelectedItem().toString().equals("--学科类型--")))
		{
			String subT=subjectType.getSelectedItem().toString();
			sql+=" subjectType='"+subT+"' and";
		}
		if(!(collegeType.getSelectedItem().toString().equals("--院校类型--")))
		{
			String colT=collegeType.getSelectedItem().toString();
			sql+=" collegeType='"+colT+"' and";
		}
			
		//如果涉及到城市字段，则会在下方文本框显示城市相关信息，需要返回多个resultset。
		if(!(city.getText().equals("XX市")))
		{
			sql+=" city='"+city.getText()+"';";
			
			
		}
		
		//需要去掉后面的可能多余的and
		if(sql.endsWith("and"))
		{
			sql=sql.substring(0,sql.length()-3).trim();
			sql+=";";
//			System.out.println(sql);
		}
		
	}
	public void close() throws SQLException
	{
		conn.close();
		rs[0].close();
		
	}
//	public static void main(String[] args)
//	{
//		queryFrame Qf=new queryFrame();
//	}
}
