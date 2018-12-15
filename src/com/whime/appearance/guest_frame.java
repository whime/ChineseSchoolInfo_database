package com.whime.appearance;
/**
 * 访客窗口
 * @author whime
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.whime.dialog.aboutDialog;
import com.whime.dialog.helpDialog;
import com.whime.model.ResultTable;
import com.whime.report.GenerateReport;
import com.whime.tool.handleCityInfo;
import com.whime.tool.outExcel;
import com.whime.tool.queryFrame;

public class guest_frame extends JFrame{
	private static final long serialVersionUID = 4731767058984483864L;
	
	JMenuBar menubar;
	JMenu menu1,menu2,menu3,menu4;
	JMenuItem item1,item2,item3;
	double WindowHeight,WindowWidth;
	Font font;
	JButton queryButton;//作为左侧导航栏
	JButton updateButton;
	JButton deleteButton;
	JButton insertButton;
	JButton exportButton;
	
	JPanel leftPanel;
	Box vbox;
	JSplitPane innerPane;
	JSplitPane outerPane;
	JTabbedPane tabbedPane;
	JTextArea cityDescription;//当有按城市查询时在下方显示城市相关信息。
	
	
	//结果集
	ResultSet[] rs=null;
	//结果显示表格
	ResultTable resultTable;
	
	
	
	public guest_frame(){
		font=new Font("宋体",Font.BOLD,20);
		
		menubar=new JMenuBar();
		menu1=new JMenu("导出当前表格");
		menu2=new JMenu("关于");
		menu3=new JMenu("帮助");
		menu4=new JMenu("退出");
		queryButton=new JButton("查询操作");
		updateButton=new JButton("更新操作");
		deleteButton=new JButton("删除操作");
		insertButton=new JButton("插入操作");
		exportButton=new JButton("导出报表");
		
		
		leftPanel=new JPanel();
		vbox=Box.createVerticalBox();
		tabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
		
		//为城市描述栏设置背景颜色
		cityDescription=new JTextArea();
		cityDescription.setText("相关城市信息....");
		cityDescription.setBackground(Color.lightGray);
		cityDescription.setFont(new Font("宋体",Font.BOLD,20));
		cityDescription.setEditable(false);
		
		menu1.setFont(font);
		menu1.setBackground(Color.lightGray);
		menu2.setFont(font);
		menu3.setFont(font);
		menu4.setFont(font);
		
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		menubar.add(menu4);
		menubar.setFont(font);
		this.setJMenuBar(menubar);
		this.setLayout(null);
		
		queryButton.setSize(200,100);
		//guest窗口只有查询操作
		deleteButton.setSize(200,100);
		deleteButton.setVisible(false);
		updateButton.setSize(200,100);
		updateButton.setVisible(false);
		insertButton.setSize(200,100);
		insertButton.setVisible(false);
		exportButton.setSize(200,100);
		exportButton.setVisible(true);

		vbox.add(queryButton);
		vbox.add(deleteButton);
		vbox.add(updateButton);
		vbox.add(insertButton);
		vbox.add(exportButton);
	}
	public void mainPane()
	{
		//上部的分割线
		innerPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,vbox,tabbedPane);
		innerPane.setBackground(Color.BLACK);
		innerPane.setDividerSize(100);//设置分割线大小
		innerPane.setOneTouchExpandable(true);
		//设置另一条垂直方向的分割线
		outerPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,innerPane,cityDescription);
		
		outerPane.setDividerLocation(900);
		add(outerPane);
	}
	
	public void startWork()
	{
		mainPane();
		setAppearance();
		//为菜单按钮设置监听
		//JMenu不响应actionListener事件,使用MenuListener
//		menu2.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				new aboutDialog();
//			}
//		});
		menu1.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e){
                
            }
            public void menuDeselected(MenuEvent e){
                
            } 
            public void menuSelected(MenuEvent e){
            	//导出当前选中的标签页索引对应的表格
//                 int selectedIndex=tabbedPane.getSelectedIndex();
                //获取当前组件,使用第一条语句不行，原因不知
//               ResultTable RT=(ResultTable) tabbedPane.getTabComponentAt(selectedIndex);
                 ResultTable RT=(ResultTable) tabbedPane.getSelectedComponent();
                 String[][] data=RT.returnData();
                 String[] title=RT.returnTitle();
                 new outExcel(title,data);
            }
		});
		menu2.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e){
                
            }
            public void menuDeselected(MenuEvent e){
                
            } 
            public void menuSelected(MenuEvent e){
                   new aboutDialog();
            }
		});
		
		menu3.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e){
                
            }
            public void menuDeselected(MenuEvent e){
                
            } 
			 public void menuSelected(MenuEvent e){
					new helpDialog();
          }
		});
		
		menu4.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e){
                
            }
            public void menuDeselected(MenuEvent e){
                
            } 
			 public void menuSelected(MenuEvent e){
					dispose();//关闭窗口，退出
          }
		});
		
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				queryFrame qf=new queryFrame();
				//模糊查询,使用like '%'
				qf.fuzzyMatch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						if(!(qf.schoolName.getText().equals("学校名称")))
						qf.sql+=" schoolName like '%"+qf.schoolName.getText()+"%' and";
						
						rs=qf.execQuery();
						//将结果rs传递给表格对象显示出来
						try {
							resultTable=new ResultTable(rs[0]);
							if(!updateButton.isVisible())
							{
								//设置管理员界面才有的更新按钮不可见
								resultTable.deleteRecord.setVisible(false);
								resultTable.insertRecord.setVisible(false);
								
							}

							//查询结果不为零个才创建表格
							if(rs[0]!=null&&resultTable.table.getRowCount()!=0)
							{
								int n=resultTable.table.getRowCount();
								tabbedPane.addTab("结果"+"("+n+"个记录"+")", resultTable);
								//设置新建的选项卡选中
								tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
							}
							
							//存在rs[1]时将城市信息显示在下方文本框
							if(rs[1]!=null)
							{
								handleCityInfo cityInfo=new handleCityInfo(rs[1]);
								String citymeg=cityInfo.returncityInfo();
								cityDescription.setText(citymeg);
							}
							
							
							qf.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						qf.dispose();//关闭查询窗口
					}
				});
				
				//精确查询
				qf.query.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						if(!(qf.schoolName.getText().equals("学校名称")))
						qf.sql+=" schoolName='"+qf.schoolName.getText()+"' and";
						rs=qf.execQuery();
						//将结果rs传递给表格对象显示出来
						try {
							resultTable=new ResultTable(rs[0]);
							//通过左侧updateButton的可见性区分是访客窗口还是管理员窗口
							if(!updateButton.isVisible())
							{
								//设置管理员界面才有的更新按钮不可见
								resultTable.deleteRecord.setVisible(false);
								resultTable.insertRecord.setVisible(false);
								
							}
							
							if(rs[0]!=null&&resultTable.table.getRowCount()!=0)
							{
								int n=resultTable.table.getRowCount();
								tabbedPane.addTab("结果"+"("+n+"个记录"+")",resultTable);
								
								//设置新建的选项卡选中
								tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
							}
							//存在rs[1]时将城市信息显示在下方文本框
							if(rs[1]!=null)
							{
								handleCityInfo cityInfo=new handleCityInfo(rs[1]);
								String citymeg=cityInfo.returncityInfo();
								cityDescription.setText(citymeg);
							}
							
							qf.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						qf.dispose();//关闭查询窗口
					}
				});
			}
		});
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				GenerateReport report=new GenerateReport();
				//没有处理生成不成功的情况。。。
				JOptionPane.showMessageDialog(null, "报表保存在D盘根目录。", "导出成功！", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
	}
	public void setAppearance()
	{
		
//		Font font=new Font("宋体",Font.BOLD,50);
//		UIManager.put("Menu.font", font);
//		
		this.setLayout(new GridLayout());
		WindowHeight=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		WindowWidth=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.setSize((int)WindowWidth,(int)WindowHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		this.setTitle("访客窗口");
		this.setLayout(new GridLayout());
		this.setVisible(true);
	}
}
