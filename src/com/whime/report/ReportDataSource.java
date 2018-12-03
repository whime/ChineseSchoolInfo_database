package com.whime.report;
//参考https://blog.csdn.net/acmman/article/details/51814243

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
/**
*  dataSource类(也就是数据填充类)，实现JRDataSource接口
*  通过放在list里面的Map对象迭代，实现数据对应
*/
public class ReportDataSource implements JRDataSource{
 
    private Iterator<Map<String,Object>> iter;
    
    //创建一个，map对象用与数据对应
    Map map = new HashMap<String,Object>();
    
   
    
    //构造函数，用于数据初始化
    public ReportDataSource(){
        //通过性别获取相应用户的数据
        List<Map<String,Object>> datas=DateSourceBaseFactory.createBeanCollection();
        //要将List中的数据迭代，需要使用Iterator迭代对象
        iter=datas.iterator();
    }
    
    //通过key获取value值
    public Object getFieldValue(JRField arg0) throws JRException {
        return map.get(arg0.getName());
    }
 
    //接口JRDataSource的方法，判断是否有下一个数据
    public boolean next() throws JRException {
        if(iter.hasNext()){
            map=(Map<String,Object>)iter.next();
            return true;
        }
        return false;
    }
 
}
