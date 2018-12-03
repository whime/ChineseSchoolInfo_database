package com.whime.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.whime.model.ResultTable;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class deleteFrame extends queryFrame {
	/**
	 * 删除视窗，在queryFrame上修改一下按钮
	 * 2018/11/24
	 * @author whime
	 */
	private static final long serialVersionUID = 8238689106117459220L;
	
	ResultSet[] rs;
	ResultTable rt;
	JFrame jf;
	public deleteFrame(){
		
		super();
		//模糊查询按钮不再需要
		fuzzyMatch.setVisible(false);
		
		query.setText("删除");
		this.setTitle("请填写要删除的信息:");
		query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1)
			{
				jf=new JFrame();
				modifySql();
				rs=execQuery();
				try {
					if(rs[0]!=null)
					{
						rt=new ResultTable(rs[0]);
						jf.add(rt);
						jf.setTitle("将要删除的数据");
						jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						jf.setSize(1700, 850);
						jf.setVisible(true);
//						JOptionPane.showMessageDialog(jf, "确定删除吗？", "删除记录", JOptionPane.OK_CANCEL_OPTION);
						int n=JOptionPane.showConfirmDialog(jf, "确定删除吗？", "删除记录", JOptionPane.OK_CANCEL_OPTION);
						if(n==JOptionPane.CANCEL_OPTION||n==JOptionPane.CLOSED_OPTION)
						{
							jf.dispose();
						}
						else
						{
							jf.dispose();
							execDel();
						}
						
					}
					
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	}
	private void modifySql()
	{
		//修改sql语句
		if(!(schoolName.getText().equals("学校名称")))
		sql="select * from school where schoolName='"+schoolName.getText()+"' and";//重新组织sql语句。
	}
	public void execDel()
	{
		//执行删除操作
		sql="delete from school where schoolName='"+schoolName.getText()+"' and";//重新组织sql语句。
		if(!(schoolName.getText().equals("学校名称")))
		{
			formSql();
			int n=0;
			try {
				//执行删除操作
				Statement stmt=conn.createStatement();
				n=stmt.executeUpdate(sql);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(this, "删除成功！删除了"+n+"个记录", "提示", JOptionPane.PLAIN_MESSAGE);
			this.dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(this,"学校名称不能为空！", "空值提醒", JOptionPane.PLAIN_MESSAGE);
			
		}
		
	}
	
	
	
//	public static void main(String[] args)
//	{
//		deleteFrame df=new deleteFrame();
//	}
}
