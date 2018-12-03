package com.whime.tool;

import java.sql.ResultSet;
import java.sql.SQLException;

public class handleCityInfo {
	//预设值null,可能有的城市没有相关信息。。。
	
	String city="null",province="null";
	int urban_area=0;
	float urban_population=0,metropolitan_area=0,metropolitan_population=0,built_up_area=0;
	String cityMeg=null;
	public handleCityInfo(ResultSet rs)
	{
		if(rs!=null)
		{
			try {
				while(rs.next())
				{
					city=rs.getString(1);
					province=rs.getString(2);
					urban_area=rs.getInt(3);
					urban_population=rs.getFloat(4);
					metropolitan_area=rs.getFloat(5);
					metropolitan_population=rs.getFloat(6);
					built_up_area=rs.getFloat(7);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		cityMeg="城市："+city+"                             "
				+"所在省份: "+province+"                             "
						+"建成区面积: "+built_up_area+"km^2"+"\n"
						+"市区面积: "+urban_area+"km^2"+"                                     "
						+"市区人口: "+urban_population+"万人"+"\n"
						+"城区面积: "+metropolitan_area+"km^2"+"                                   "
						+"城区人口: "+metropolitan_population+"万人";
				

	}
	
	public String returncityInfo()
	{
		return cityMeg;
	}
}
