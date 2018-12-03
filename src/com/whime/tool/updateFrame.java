package com.whime.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JOptionPane;

public class updateFrame extends queryFrame {

	/**
	 * 更新记录窗口
	 * 2018/11/26
	 * @author whime
	 */
	private static final long serialVersionUID = 5269139827959079711L;
	public updateFrame()
	{
		super();
		this.setTitle("根据学校名称修改记录");
		this.setVisible(true);
		
		//模糊查询按钮不再需要
		fuzzyMatch.setVisible(false);
		query.setText("更新");
		query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int m,n;
				m=JOptionPane.showConfirmDialog(null, "确定更新吗？");
				if(m==JOptionPane.OK_OPTION)
				{
					formSql();//重新定义sql语句的组成方法
					if(!schoolName.getText().equals("学校名称"))
					{
						try {
							Statement stmt=conn.createStatement();
							n=stmt.executeUpdate(sql);
							if(n==0)
							{
								JOptionPane.showMessageDialog(null, "更新了0个记录！请检查学校名称是否出错。", "error", JOptionPane.WARNING_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "更新成功!", "更新结果", JOptionPane.PLAIN_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					else
					{
//						JOptionPane.showMessageDialog(null, "学校名称不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
					}
					

				}
				
			}
		});
		
	}
	
	public void formSql()
	{
		if(schoolName.getText().equals("学校名称"))
		{
			JOptionPane.showMessageDialog(this, "学校名称不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
			
		}
		else
		{
			sql="update school set";
			if(Is211.isSelected())
			{
				sql+=" is211='是',";
			}
			else
			{
				sql+=" is211='否',";
			}
			if(Is985.isSelected())
			{
				sql+=" is985='是',";
			}
			else
			{
				sql+=" is985='否',";
			}
			if(IsStateRun.isSelected())
			{
				sql+=" state_run='是',";
			}
			else
			{
				sql+=" state_run='否',";
			}
			
			
			//注意这里的单引号！！！
			
			if(!(subjectType.getSelectedItem().toString().equals("--学科类型--")))
			{
				String subT=subjectType.getSelectedItem().toString();
				sql+=" subjectType='"+subT+"',";
			}
			if(!(collegeType.getSelectedItem().toString().equals("--院校类型--")))
			{
				String colT=collegeType.getSelectedItem().toString();
				sql+=" collegeType='"+colT+"',";
			}
				
			
			if(!(city.getText().equals("XX市")))
			{
				sql+=" city='"+city.getText()+"'";
				System.out.println(sql);
				
			}
			
			
			//需要去掉后面的可能多余的逗号
			if(sql.endsWith(","))
			{
				sql=sql.substring(0,sql.length()-1).trim();
			}
			
			sql+=" where schoolName='"+schoolName.getText()+"';";
		}
	}
}
