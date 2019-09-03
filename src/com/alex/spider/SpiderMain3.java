package com.alex.spider;



import java.io.IOException;
import java.net.URL;
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
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.alibaba.fastjson.JSONObject;



public class SpiderMain3 {

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
		        //Thread.sleep(50000);
		        
		        if (text!="") {
					
		        	System.out.println("SUCCESS");
					
					//模型化Json字符串
			        JsonMain js = JSONObject.parseObject(text, JsonMain.class);
			       
			        //遍历模型中的数据，获取详情页地址，再次请求
			        for (int i = 0; i < js.getData().getRl().size(); i++) {
			        	
			        	String rid = js.getData().getRl().get(i).getRid();			        	
			        	String dyString = "https://www.douyu.com/betard/";			        	
			        	String newDyString = dyString+rid;
			        	
			        	System.out.println(newDyString);
		        	
			        	Document document2 = Jsoup.connect(newDyString).ignoreContentType(true)
				                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
				                .get();  
			        	
				        String text2 = document2.body().text(); 
				        System.out.println(text2);
				
				        //继续要做的功能：用正则匹配到地域和等级		 
				        //匹配双引号的正则表达式  "cityname": "南京市",   "next_level": 5,				      
				        String pattStr = "cityname\":\"(.+?)\"";	
				       			        
				        //创建Pattern并进行匹配
				        Pattern pattern= Pattern.compile(pattStr);
				        Matcher matcher=pattern.matcher(text2);
				        //将所有匹配的结果打印输出
				        while(matcher.find()) {
				            System.out.println(matcher.group(1));
				        }
				        
				        String pattStr2 = "next_level\":(.+?),";
				        Pattern pattern2= Pattern.compile(pattStr2);
				        Matcher matcher2=pattern2.matcher(text2);
				        while(matcher2.find()) {
				            System.out.println(matcher2.group(1));
				        }
				        
			        	Thread.sleep(50000);
				        
				      }
			        	
				        	

				        		
//			        	System.out.println(text2);
			        	
			        	
			        	
				
			}
		        
		        }
			}
	}
	
	
			
	
	

