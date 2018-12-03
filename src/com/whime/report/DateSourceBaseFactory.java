package com.whime.report;
//参考https://blog.csdn.net/acmman/article/details/51814243
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whime.tool.ConnectToMysql;
/**
 * Map中的键值要与模板中的file值对应
 * */
public class DateSourceBaseFactory {
 
    public static List<Map<String,Object>> createBeanCollection() {
        
       
        ResultSet rs=null;
        Statement st=null;
        Connection con=null;
        List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
        
        try {
            con=new ConnectToMysql().connect();
            st=con.createStatement();
            rs=st.executeQuery("select * from school_density order by density asc");
            while(rs.next()){
                Map<String,Object> attris=new HashMap<String,Object>();
                attris.put("cityName",  rs.getString("cityName"));
                attris.put("schoolcount", rs.getInt("schoolcount"));
                attris.put("area", rs.getFloat("area"));
                attris.put("density", rs.getDouble("density"));
                datas.add(attris);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        
        return datas;
    }
 
}
