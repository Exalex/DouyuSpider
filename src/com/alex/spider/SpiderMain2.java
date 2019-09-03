package com.alex.spider;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.alibaba.fastjson.JSONObject;



public class SpiderMain2 {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InterruptedException {
			
			
			
			//循环爬取每一页
			for (int a = 1; a < 10; a++) {
				
				String url = "https://www.douyu.com/gapi/rkc/directory/3_790/"+a;
				
				//获取请求链接的响应html
				Document document = Jsoup.connect(url).ignoreContentType(true)
		                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
		                .get();  
				
//				Document document = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
				
				//取得body标签中的Json字符串  
		        String text = document.body().text(); 
//		        System.out.println(text);
		        
		        

		        //睡眠50秒
//		        Thread.sleep(50000);
		        
		        if (text!="") {
					
		        	System.out.println("SUCCESS");
					
					//模型化Json字符串
			        JsonMain js = JSONObject.parseObject(text, JsonMain.class);
			       
			        //遍历模型中的数据，插入数据库
			        for (int i = 0; i < js.getData().getRl().size(); i++) {

//				        System.out.println(js.getData().getRl().get(i).getRn());
				    
				        String sqlInsert = "INSERT INTO douyu (rid,rn,rsl,nn) VALUES("+js.getData().getRl().get(i).getRid()
				        		+","+"'"+js.getData().getRl().get(i).getRn()+"'"
				        		+","+"'"+js.getData().getRl().get(i).getRs1()+"'"
				        		+","+"'"+js.getData().getRl().get(i).getNn()+"'"
				        		+")";
				        
				        
//				        System.out.println(sqlInsert);
				        
				        ArrayList<Integer> rid = new ArrayList<Integer>();
						//预加载数据，去重用
				        String sqlCheck = "select rid FROM douyu";
				        //1.加载驱动
						Class.forName("com.mysql.jdbc.Driver");
						//2.获取链接对象
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider?useUnicode=true&characterEncoding=utf8", "root", "006134");
						
						//3.创建语句对象
						
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery(sqlCheck);

						while (rs.next()) { //结果集有内容
//							System.out.println(rs.getInt("rid"));
							
							//把结果集放入
							rid.add((Integer)rs.getInt("rid"));
							
						}
						
						System.out.println(rid.toString());

//				        Thread.sleep(2000);
						
						
						Statement st2 = con.createStatement();
						//4.执行sql(插入数据)
						
						String ridC = js.getData().getRl().get(i).getRid();
						
						System.out.println(ridC);

						//去重再入库（要同数据类型对比，否则出错）
						if (rid.contains(Integer.valueOf(ridC))) {
							System.out.println("重复");

						}else {
							System.out.println("不重复");

							int row = st2.executeUpdate(sqlInsert);
							
							System.out.println("插入成功");
						}
						
						//5.关闭资源
						st.close();
						con.close();
						
					}
				}
		        
		        //睡眠5秒
		        Thread.sleep(5000);
		        
		    	
				
			}
			
			
	}
	
}
