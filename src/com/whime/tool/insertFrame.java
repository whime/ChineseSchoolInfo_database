package com.whime.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class insertFrame extends queryFrame {

	/**
	 * 插入记录窗口,和updateFrame差不多。。
	 * 2018/11/26
	 * @author whime
	 */
	private static final long serialVersionUID = 621751068237533883L;

	public insertFrame()
	{
		super();
		this.setTitle("输入插入学校的信息");
		this.setVisible(true);
		
		//模糊查询按钮不再需要
		fuzzyMatch.setVisible(false);
		query.setText("插入");
		query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int m,n;
				m=JOptionPane.showConfirmDialog(null, "确定插入吗？");
				if(m==JOptionPane.OK_OPTION)
				{
					formSql();//重新定义sql语句的组成方法
//					System.out.println(sql);
					
					if(!schoolName.getText().equals("学校名称"))
					{
						
						try {
							
							Statement stmt=conn.createStatement();
							n=stmt.executeUpdate(sql);
							if(n==0)
							{
								/*感觉不大可能在操作界面上出现插入问题，因为只要sql语句信息没错，都可以
								 * 插入，如果是学校名相同插入不了，那么异常会被捕获，也不会执行到这个if语句
								 */
								JOptionPane.showMessageDialog(null, "插入了0个记录！请检查插入信息。", "error", JOptionPane.WARNING_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "插入成功!", "插入结果", JOptionPane.PLAIN_MESSAGE);
							}
						} catch (SQLException e1) {
							//在网上找到一个方法处理插入相同学校名引发的异常
							if(e1.getMessage().contains("for key 'PRIMARY'")){
								JOptionPane.showMessageDialog(null, "该学校记录已存在！尝试修改!", "插入异常",JOptionPane.WARNING_MESSAGE);
							}
							
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
			sql="insert into school values('"+schoolName.getText()+"',";
			
			if(!(city.getText().equals("XX市")))
			{
				sql+="'"+city.getText()+"',";
				
			}
			else
			{
				sql+=null+",";
			}
			
			if(Is211.isSelected())
			{
				sql+="'是',";
			}
			else
			{
				sql+="'否',";
			}
			if(Is985.isSelected())
			{
				sql+="'是',";
			}
			else
			{
				sql+="'否',";
			}
			if(IsStateRun.isSelected())
			{
				sql+="'是',";
			}
			else
			{
				sql+="'否',";
			}
			
			
			//注意这里的单引号！！！
			
			if(!(subjectType.getSelectedItem().toString().equals("--学科类型--")))
			{
				String subT=subjectType.getSelectedItem().toString();
				sql+="'"+subT+"',";
			}
			else
			{
				sql+="'综合类院校'"+",";
			}
			
			/*由于设计上的错误，一开始没有考虑学校的隶属，其实是懒。。
			 * 所以这里统一为插入的记录指定为隶属于教育部。
			 * 这个系统瑕疵太多了，作为一个不完整的示例就行吧，不大想改。
			 */
			sql+="'教育部',";
			
			if(!(collegeType.getSelectedItem().toString().equals("--院校类型--")))
			{
				String colT=collegeType.getSelectedItem().toString();
				sql+="'"+colT+"');";
			}
			else
			{
				sql+="'大学'"+");";
			}
			
		}
	}

}
