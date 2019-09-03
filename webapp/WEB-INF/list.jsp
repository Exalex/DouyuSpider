<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<c:forEach items="${rl}" var="p" varStatus="vs">
				<tr>
					<td>${p.nn}</td>
					<td>${p.rid}</td>
					<td><a href="https://www.douyu.com/${p.rid}" target="_Blank">${p.rn} </a></td>
					<td><a href="/shop?cmd=edit&id=">编辑</a> | <a
						href="/shop?cmd=delete&id=">删除</a>
					</td>
				</tr>
				</br>
			</c:forEach>
			
			<a href="/dy?cmd=blockAll">归档全部主播</a>
			<a href="/dy?cmd=spider">爬取新主播</a>



</body>
</html>