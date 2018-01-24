<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>error</title>
</head>
<body>
	
<div class="container">
	<div class="panel margin-big-top">
	<div class="text-center">
			<br>
			<br>
			<img src="http://www.pintuer.com/images/mm-1.jpg" class="radius" width="500" />
			<h2  class="padding-top">
				<stong>404错误！咦！该页面是被耗子们啃了？</stong>
			</h2>
			<div class="padding-big">
				<a href="${pageContext.request.contextPath }/patient/list" class="button bg-yellow">返回上一页</a>
				<a href="#" class="button">反馈</a>
			</div>
	</div>
	</div>
</div>
	
</body>
</html>