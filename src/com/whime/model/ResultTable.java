package com.whime.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.whime.tool.ConnectToMysql;
import com.whime.tool.MyTableCellRenderer;
/*处理查询的数据的类,可在表格上修改数据
 * 2018/11/25
 * @author whime
 */
public class ResultTable extends JPanel{
	
	private static final long serialVersionUID = 4260846605538214235L;
	int rows;
	int cols=8;
	String firstRowTitle[]= {"学校名称","城市","211","985","公办","学科类型","隶属于","院校类型"};
	public JTable table;
	String [][] data;
	String school,city,is211,is985,isStateRun,subjectType,subOrdinated_to,collegeType;
	public JButton deleteRecord,insertRecord;
	Connection conn;
	DefaultTableModel tableModel;
	String schoolStr;//用于保存插入数据时输入的值
	
	public ResultTable(ResultSet rs) throws SQLException
	{
		
		
		deleteRecord=new JButton("删除选中记录");
		deleteRecord.setVisible(true);
		
		insertRecord=new JButton("插入一条记录");
		insertRecord.setVisible(true);
		
		try {
			conn=new ConnectToMysql("root","root").connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(rs!=null)
		{
			//查询结果不为空
			//获取结果集的行数
			rs.last();
			rows=rs.getRow();
			
			
			if(rows==0)
			{
				JOptionPane.showMessageDialog(null, "没有相关信息！请检查查询条件。","message" , JOptionPane.WARNING_MESSAGE);
			}
			rs.beforeFirst();//游标移动到第一行之前
			
			data=new String[rows][8];
			for(int i=0;i<rows;i++)
			{
				data[i]=new String[8];
			}
			int i=0,j,k;
			while(rs.next())
			{
				j=0;
				k=1;
				for(;j<8;)
				{
					data[i][j]=rs.getString(k);
					k++;
					j++;
//					System.out.println(i);
//					System.out.println(String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(k));
				}
				i++;
				
			}
			
			//使用表格模型
			tableModel=new DefaultTableModel(data,firstRowTitle);
					
			table=new JTable(tableModel){
				/**
				 * 通过deleteRecord的可见与否设置单元格的可编辑性。
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row,int column)
				{
					if(!deleteRecord.isVisible())
					{
						return false;
					}
					else
					{
						return true;
					}
				}
			};
			tableModel.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e)
				{
					//参考https://blog.csdn.net/xietansheng/article/details/78079806
					// 获取 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
	                int firstRow = e.getFirstRow();
	                int lastRow = e.getLastRow();
	    	       
	                //获取被改变的列
	                int column=e.getColumn();
	                //     TableModelEvent.INSERT   新行或新列的添加
	                //     TableModelEvent.UPDATE   现有数据的更改
	                //     TableModelEvent.DELETE   有行或列被移除
	                int type = e.getType();
	                if(type==TableModelEvent.UPDATE)
	                {
	                	if(column==0)
	                	{
	                		JOptionPane.showMessageDialog(null, "学校名称不允许修改，尝试删除再插入相关记录！","错误", JOptionPane.WARNING_MESSAGE);
	                		return;
	                	}
	                	for(int row=firstRow;row<=lastRow;row++)
	                	{
	                		String school=(String)tableModel.getValueAt(row, 0);
	                		String cityStr=(String) tableModel.getValueAt(row, 1);
	                		String is211Str=(String) tableModel.getValueAt(row, 2);
	                		String is985Str=(String) tableModel.getValueAt(row, 3);
	                		String stateRunStr=(String) tableModel.getValueAt(row, 4);
	                		String subjectTypeStr=(String) tableModel.getValueAt(row, 5);
	                		String subOrdinatedToStr=(String) tableModel.getValueAt(row, 6);
	                		String collegeTypeStr=(String) tableModel.getValueAt(row, 7);
	                		
	                		String sql="update school set city='"
	                				+cityStr+"',is211='"
	                				+is211Str+"',is985='"
	                				+is985Str+"',state_run='"
	                				+stateRunStr+"',subjectType='"
	                				+subjectTypeStr+"',subordinated_to='"
	                				+subOrdinatedToStr+"',collegeType='"
	                				+collegeTypeStr+"' where schoolName='"
	                				+school+"';";
	                		
	                		Statement stmt;
							try {
								stmt = conn.createStatement();
								int n=stmt.executeUpdate(sql);
								
								if(n==0)
								{
									JOptionPane.showMessageDialog(null, "操作失败！请查看该记录是否仍存在！","更新失败！", JOptionPane.WARNING_MESSAGE);
								}
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                		
	                	}
	                }
	                
	                else if(type==TableModelEvent.INSERT)
	                {
	                	
	                	/*这个插入记录使用一种迂回策略，就是为了避免使用太多的输入框录入数据
	                	 * 只弹出一个primary key的输入框，输入学校名字之后先把记录写入数据库，其他字段值无所谓，随便指定
	                	 * 由于插入行的时候addRow方法只有一个学校名，所以我们在操作界面只看到一个字段，其他的字段可以继续录入
	                	 * 这时候用户会以为在插入一条数据，其实是在更新数据了。
	                	 * 所以在这里我们只需要实现插入一条primary key为输入值的记录就行了。
	                	 */
                		String sql="insert into school values('"+schoolStr+"','北京市','否','否','否','综合类院校','教育部','大学');";
                		Statement stmt;
						try {
							stmt = conn.createStatement();
							stmt.executeUpdate(sql);
													
							} catch (SQLException e1) {
							if(e1.getMessage().contains("for key 'PRIMARY'")){
									JOptionPane.showMessageDialog(null, "该学校记录已存在！尝试修改!", "插入异常",JOptionPane.WARNING_MESSAGE);
									tableModel.removeRow(tableModel.getRowCount()-1);
									return;
							}
						}
						
						JOptionPane.showMessageDialog(null, "请继续完善学校信息。");
	                }
	                
				}
			});
			
			 // 创建单元格渲染器
	        MyTableCellRenderer renderer = new MyTableCellRenderer();

	        // 遍历表格的每一列，分别给每一列设置单元格渲染器
	        for (int h = 0; h < firstRowTitle.length; h++) {
	            // 根据 列名 获取 表格列
	            TableColumn tableColumn = table.getColumn(firstRowTitle[h]);
	            // 设置 表格列 的 单元格渲染器
	            tableColumn.setCellRenderer(renderer);
	        }
	
			//设置表格内容颜色
			table.setForeground(Color.BLACK);                   // 字体颜色
	        table.setFont(new Font(null, Font.PLAIN, 15));      // 字体样式
	        table.setSelectionForeground(Color.LIGHT_GRAY);      		// 选中后字体颜色为红色
	        table.setSelectionBackground(Color.BLUE);     // 选中后字体背景
	        table.setGridColor(Color.GRAY);                     // 网格颜色

	        // 设置表头
	        table.getTableHeader().setFont(new Font(null, Font.BOLD, 15));  // 设置表头名称字体样式
	        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
	        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
	        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

	        // 设置行高
	        table.setRowHeight(40);
	        table.setPreferredScrollableViewportSize(new Dimension(1600,700));
	        
	        //设置各自的列宽
	        TableColumnModel model=table.getColumnModel();
	        //索引从0开始！！！
	        TableColumn column=model.getColumn(0);
	        column.setPreferredWidth(300);
	        
	        column=model.getColumn(1);
	        column.setPreferredWidth(240);
	        
	        column=model.getColumn(2);
	        column.setPreferredWidth(60);
	        
	        column=model.getColumn(3);
	        column.setPreferredWidth(60);
	        
	        column=model.getColumn(4);
	        column.setPreferredWidth(60);
	        
	        column=model.getColumn(5);
	        column.setPreferredWidth(180);
	        
	        column=model.getColumn(6);
	        column.setPreferredWidth(300);
	        
	        column=model.getColumn(7);
	        column.setPreferredWidth(430);


	        JScrollPane scrollPane=new JScrollPane(table);
	        scrollPane.setVisible(true);
	        this.add(scrollPane);
	        this.add(deleteRecord);
	        this.add(insertRecord);
	        addButtonListener();
	        
		}
		else
		{
			JOptionPane.showMessageDialog(null, "没有相关记录", "查询结果", JOptionPane.PLAIN_MESSAGE);
		}
			
	}
	public void addButtonListener()
	{
		//为三个按钮设置监听事件
		deleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//获取选中的行,取出primary key,逐个删除。
				int[] deleteRows=table.getSelectedRows();
				//获取删除的记录数
				int recordNum=deleteRows.length;
				if(recordNum==0)
				{
					JOptionPane.showMessageDialog(null, "没有选中记录！", "无选中记录", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					int option=JOptionPane.showConfirmDialog(null, "将要删除"+recordNum+"个记录！确定？");
					if(option==JOptionPane.OK_OPTION)
					{	
						//组成sql语句
						String sql="delete from school where schoolName='"+table.getValueAt(deleteRows[0], 0)+"' ";
						for(int i=1;i<recordNum;i++)
						{
							sql+="or schoolName='"+table.getValueAt(deleteRows[i], 0).toString()+"' ";
						}
						sql+=";";
						
						try {
							//执行删除操作
							Statement stmt=conn.createStatement();
							int n=stmt.executeUpdate(sql);
							if(n!=0)
							{
								//移除表格所删除的数据
								for(int i=0;i<recordNum;i++)
								{
									tableModel.removeRow(table.getSelectedRow());
								}
								JOptionPane.showMessageDialog(null, "删除了"+n+"个记录！", "删除成功！", JOptionPane.PLAIN_MESSAGE);
								
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "删除了"+n+"个记录！", "删除失败！", JOptionPane.PLAIN_MESSAGE);
							}
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				
			}
		});
		
		//插入一条记录
		insertRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//先录入学校名
				schoolStr=JOptionPane.showInputDialog(null, "输入学校名称", "输入框", JOptionPane.PLAIN_MESSAGE);
				
				if(schoolStr==null)
				{
					return;//没有输入直接按返回按钮
				}
				else if(schoolStr.equals(""))
				{
					JOptionPane.showMessageDialog(null, "学校名不能为空！", "插入出错", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					
					String[] arr= {schoolStr};
					tableModel.addRow(arr);
					
					
					
//					System.out.println(table.getRowCount());
				}
				
			}
		});
	}
	//返回数据和表头
	public String[][] returnData(){
		return data;
	}
	public String[] returnTitle() {
		return firstRowTitle;
	}
	
	
}
