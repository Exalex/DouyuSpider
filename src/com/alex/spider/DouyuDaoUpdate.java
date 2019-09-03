package com.alex.spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;


public class DouyuDaoUpdate {
	
	@Test
	public  void update() throws ClassNotFoundException, SQLException {

//				String sql = "SELECT * FROM douyu";
//				String sql = "SELECT * FROM douyu WHERE block is NULL AND nl<=17";
				String sql = "UPDATE douyu SET block=1 WHERE block is NULL";

				System.out.println(sql);
				//1.加载驱动
				Class.forName("com.mysql.jdbc.Driver");
				//2.获取链接对象
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider", "root", "006134");
				//3.创建语句对象
				Statement st = con.createStatement();
				//4.执行sql
				int rs = st.executeUpdate(sql);
	
				System.out.println(rs);
				
				//6.关闭资源
				st.close();
				con.close();
				

	}
	
}
