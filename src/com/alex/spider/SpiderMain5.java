package com.alex.spider;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;



public class SpiderMain5 {

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
		        	
				        //数据库操作
	
				        ArrayList<Integer> rid1 = new ArrayList<Integer>();
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
							rid1.add((Integer)rs.getInt("rid"));
							
						}
						
//						System.out.println(rid1.toString());

//				        Thread.sleep(2000);
						
						
						Statement st2 = con.createStatement();
						//4.执行sql(插入数据)
						
						String ridC = js.getData().getRl().get(i).getRid();
						
						System.out.println(ridC);

						//去重再入库（要同数据类型对比，否则出错）
						if (rid1.contains(Integer.valueOf(ridC))) {
							System.out.println("重复，此主播已经在数据库中");
//							Thread.sleep(5000);

						}else {
							System.out.println("不重复，是新人");
							
							//请求直播页的数据,获取主播所在城市和等级
				        	String rid = js.getData().getRl().get(i).getRid();			        	
				        	String dyString = "https://www.douyu.com/betard/";			        	
				        	String newDyString = dyString+rid;
				        	
				        	System.out.println(newDyString);
			        	
				        	Document document2 = Jsoup.connect(newDyString).ignoreContentType(true).timeout(5000)
					                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
					                .get();  
				        	
					        String text2 = document2.body().text(); 
//					        System.out.println(text2);
					
					        //继续要做的功能：用正则匹配到地域和等级		 
					        //匹配双引号的正则表达式  "cityname": "南京市",   "next_level": 5,				      
					        String pattStr = "cityname\":\"(.+?)\"";	
					       			        
					        //创建Pattern并进行匹配
					        Pattern pattern= Pattern.compile(pattStr);
					        Matcher matcher=pattern.matcher(text2);
					        //将所有匹配的结果打印输出
					        String city="";
					        while(matcher.find()) {
					        	city = matcher.group(1);
//					            System.out.println(matcher.group(1));					            
					        }
					        
					        String nl="";
					        String pattStr2 = "next_level\":(.+?),";
					        Pattern pattern2 = Pattern.compile(pattStr2);
					        Matcher matcher2 =pattern2.matcher(text2);
					        while(matcher2.find()) {
					        	nl = matcher2.group(1);
//					            System.out.println(matcher2.group(1));					            
					        }
					        
					      //请求工会页的数据,获取主播所在工会		        	
				        	String unionStr = "https://www.douyu.com/ztCache/club/getanchorclubstatus?roomid=";			        	
				        	String newUnionStr = unionStr+rid;
				        	
//				        	System.out.println(newUnionStr);
			        	
				        	Document document3 = Jsoup.connect(newUnionStr).ignoreContentType(true).timeout(5000)
					                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
					                .get();  
				        	
					        String text3 = document3.body().text(); 
//					        System.out.println(text3);
					        
					      //继续要做的功能：用正则匹配到地域和等级		 
					        //匹配双引号的正则表达式  "cityname": "南京市",   "next_level": 5,				      
					        String pattStr3 = "org_sname\":\"(.+?)\"";	
					       
					        //创建Pattern并进行匹配
					        Pattern pattern3= Pattern.compile(pattStr3);
					        Matcher matcher3=pattern3.matcher(text3);
					        //将所有匹配的结果打印输出
					        String unionString = "";
					        while(matcher3.find()) {
					        	unionString = unicodeToCn(matcher3.group(1));
					        	
					            System.out.println(unicodeToCn(matcher3.group(1)));					            
					        }
					        
					        
					        Thread.sleep(500);
					        

					        //插入
					        
					        String sqlInsert = "INSERT INTO douyu (rid,rn,rsl,nn,nl,city,uni) VALUES("+js.getData().getRl().get(i).getRid()
					        		+","+"'"+js.getData().getRl().get(i).getRn()+"'"
					        		+","+"'"+js.getData().getRl().get(i).getRs1()+"'"
					        		+","+"'"+js.getData().getRl().get(i).getNn()+"'"
					        		+","+nl
					        		+","+"'"+city+"'"
									+","+"'"+unionString+"'"
					        		+")";
//					        
					        System.out.println(sqlInsert);
					        
							int row = st2.executeUpdate(sqlInsert);
							
							System.out.println("插入成功");
							
							Thread.sleep(500);
						}
						
						//5.关闭资源
						st.close();
						con.close();
						
					}
				}
		        
		        //for循环结束睡眠5秒
		        Thread.sleep(5000);

			}
						
	}
	
    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
          returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
	
}
