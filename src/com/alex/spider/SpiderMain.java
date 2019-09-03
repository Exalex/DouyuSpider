package com.alex.spider;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;



public class SpiderMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
			
			String url = "https://www.douyu.com/gapi/rkc/directory/3_790/1";
	        
			//获取请求链接的响应html
//			Document document = Jsoup.connect(url).ignoreContentType(true)
//	                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
//	                .get();  
			
			Document document = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
			
			//取得body标签中的Json字符串  
	        String text = document.body().text(); 
	        System.out.println(text);
	        
	        //模型化Json字符串
	        JsonMain js = JSONObject.parseObject(text, JsonMain.class);
	       
	        
	        for (int i = 0; i < js.getData().getRl().size(); i++) {

		        System.out.println(js.getData().getRl().get(i).getRn());
//		        System.out.println(js.getData().getRl().get(i).getUid());
		        System.out.println(js.getData().getRl().get(i).getRid());
		        System.out.println(js.getData().getRl().get(i).getNn());
		        System.out.println(js.getData().getRl().get(i).getRs1());
		        
//		        String sqlInsert = "INSERT INTO douyu (rid) VALUES("+js.getData().getRl().get(i).getRid()+")";
		        String sqlInsert = "INSERT INTO douyu (rid,rn,rsl,nn) VALUES("+js.getData().getRl().get(i).getRid()
		        		+","+"'"+js.getData().getRl().get(i).getRn()+"'"
		        		+","+"'"+js.getData().getRl().get(i).getRs1()+"'"
		        		+","+"'"+js.getData().getRl().get(i).getNn()+"'"
		        		+")";
		        System.out.println(sqlInsert);
		        
//				String sqlDelete = "DELETE FROM t_student WHERE id=1";
//				String sqlUpdate = "UPDATE t_student SET name='Li',age='10' WHERE id = 3";
				//1.加载驱动
				Class.forName("com.mysql.jdbc.Driver");
				//2.获取链接对象
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider?useUnicode=true&characterEncoding=utf8", "root", "006134");
				
				//3.创建语句对象
				Statement st = con.createStatement();
				//4.执行sql
				int row = st.executeUpdate(sqlInsert);
				System.out.println(row);
				//5.关闭资源
				st.close();
				con.close();
				
			}
	        
	       
	        

	}

}
