package com.alex.spider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dy")
public class SpiderMainServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String cmd = req.getParameter("cmd");
		String blockString = "blockAll";
		String spiderString = "spider";
		System.out.println(cmd);
		if (blockString.equals(cmd)){ //常量放前面防止空指针异常
			//执行sql操作
			DouyuDaoUpdate up = new DouyuDaoUpdate();
			try {
				up.update();
				System.out.println("开始操作");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("111111111111111");
			resp.sendRedirect("/dy");
			
			return;
			
		}else if (spiderString.equals(cmd)) {
			SpiderMain6 spiderMain6 = new SpiderMain6();
			try {
				
				spiderMain6.spider();
				resp.sendRedirect("/dy");
				return;
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
//			req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
			System.out.println("2222222222222222");
		}
			
//			
//			List<Product> products = dao.list();
			
			DouyuDao dao = new DouyuDao();
			
			ArrayList<Rl> rl = new ArrayList<Rl>();
			
			Rl r = new Rl();
			
			try {
				 rl = dao.list();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			req.setAttribute("rl", rl);
			
			req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
//			
			
}
}


