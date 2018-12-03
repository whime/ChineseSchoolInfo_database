*使用Mysql和JDBC开发了一个简陋的关于高校信息的数据库系统，实现了基本的增删改查功能，几个表，没有很多的复杂关系，用到三个触发器，属于很基本的数据库操作了。*

##### 平台
+ 操作系统：Windows10
+ 数据库: Mysql 社区版 8.0.12
+  开发工具：Eclipse
+  语言&接口：java ,JDBC

#### 界面概览
+ 登陆界面
![登陆界面](https://img-blog.csdnimg.cn/20181129100741107.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)
+ 管理员窗口
![管理员窗口](https://img-blog.csdnimg.cn/20181129100824406.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)
+ 访客窗口
![访客窗口](https://img-blog.csdnimg.cn/20181129100855701.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)
+ 录入信息窗口，修改信息等窗口大同小异
![录入信息窗口]
(https://img-blog.csdnimg.cn/20181129100948159.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)

#### 功能
+ 增删改查四个按钮，可以弹出一个窗口录入，更新，删除信息等。
+ 菜单栏只有简单的关于本软件的说明，帮助，退出按钮，和导出查询结果到excel的功能。
+ 查询出来的表使用JTable实现，使用DefaultTableModel 实现表的监听，管理员可以直接在上面编辑实现数据库的更新，插入一行到数据库，从数据库删除一行或几行。
+ 从登陆窗口可以使用访客登陆，无需密码，但是只有查询功能，没有修改增加记录的功能。也可以选择管理员账号登陆，只需在数据库插入相关账号就可以了。默认的Mysql数据库有一个表user,里面放着guest账户和一个我自己设定的管理员账户。guest账户不可以用来登陆系统（只是用于方可登陆时使用的身份）。
+ 主窗口下面有一个JTextArea显示查询高校时指定的城市的相关信息，所属省份，人口，面积等相关信息。
+  在左侧功能栏增加了一个导出报表的功能，可以导出各个城市的高校数量和密度情况。使用的jasperreport+ireport,中文处理花费了好大的力气。。
![导出pdf](https://img-blog.csdnimg.cn/20181203132827198.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)
+ 简单报表情况
![报表](https://img-blog.csdnimg.cn/20181203180750216.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3doaW1ld2Nt,size_16,color_FFFFFF,t_70)

#### 设计缺陷
+ 画查询窗口GUI的时候没有考虑周全，查询窗口不能制定学校的隶属关系，是否211，985，公办这些必须指明是或不是，也就是不能是和不是都查询出来。
+ 数据库底层默认制定了很多默认值，字段类型也全部可以使用String类型获取，太过单一。
+ 功能太过简单，数据库系统可能会有未知的bug和漏洞。
+ 数据库有一个school表，city表，school_density,user表四个表，但是GUI只实现了school表的查询等操作，这几个表分别表示高校信息，城市人口和面积信息(根据2016年城市统计年鉴，每个城市建成区里高校密度（高校数量/高校面积），用户id和密码。使用触发器实现对school_density的实时更新。
+  在实现报表的时候新建了一个表school_density,用于存储各个城市高校数量，建成区面积，和两者的比值，增加了三个trigger对school表的增删改进行监听并及时更新school_density表。基本上每次都需要对表school_density进行全面更新，虽然只有281个记录。
+  导出的报表会放在D盘根目录。

#### 参考
+  [java swing编程的很好很清晰的教程（感谢作者）](https://blog.csdn.net/xietansheng/article/details/72814492)
+ [用于界面美化的beautyEye包的简单使用](https://github.com/JackJiang2011/beautyeye/wiki/BeautyEye-L&F%E7%AE%80%E6%98%8E%E5%BC%80%E5%8F%91%E8%80%85%E6%8C%87%E5%8D%97)
+  [导出表为excel文件,也就是jxl.jar的使用](https://blog.csdn.net/cy96151/article/details/52320024)
+ 报表工具JasperReport使用教程

	1.http://www.open-open.com/doc/view/fe2068d9dfb64068bf5721d465562f8a
	2.https://blog.csdn.net/acmman/article/details/51814181
	3.https://blog.csdn.net/acmman/article/details/51814243
+  [关于export弃用setParameter的问题](https://stackoverflow.com/questions/24117878/jasperreports-5-6-jrxlsexporter-setparameter-is-deprecated)

#### 依赖的jar包
+ jxl.jar，导出为excel文件
+ mysql-connector-java-8.0.12.jar，连接数据库的驱动
+ SwingSets3(BeautyEyeLNFDemo).jar，界面美化
+ 导出报表需要使用的包，参考上面链接3操作

![报表所需依赖包](https://img-blog.csdnimg.cn/20181203133507730.png)

