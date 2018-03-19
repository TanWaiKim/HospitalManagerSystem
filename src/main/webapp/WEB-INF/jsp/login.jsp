<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>医院信息化系统</title>
<link href="${pageContext.request.contextPath }/css/login.css"
	rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/login.js"></script>
<style type="text/css">
body {
	background-color: #1150af;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
 .title-name span{
	font-size: 50px;
	margin-left: 343px;
}
</style>

</head>
<body>
	<form method="post" id="loginAllForm"
		onsubmit="return false;">
		<table id="Table_01" width="1000" height="636" border="0"
			cellpadding="0" cellspacing="0">
			<tr width="1000" height="240">
				<td colspan="5" class="title-name"><span>医院管理系统</span></td>
			</tr>
			<tr>
				<td><img src="images/login/login_02.jpg" width="331" height="80"></td>
				<td width="432" bgcolor="#000000">
					<table width="100%" border="0" cellspacing="0" cellpadding="5">
						<tr>
							<td width="21%"><div align="right" class="white">用户:</div></td>
							<td width="50%"><input type="text" name="username" id="username"
								 styleClass="form"  /></td>
							<td width="29%" align="center"><input type="submit"
								class="button" id="loginsubmit" value="登录" /></td>
						</tr>
						<tr>
							<td><div align="right" class="white">密码:</div></td>
							<td><input type="password" name="password" id="password" styleClass="form"
								/></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" class="white">登录类型:</td>
							<td class="white">
							<input type="radio" name="postOffice"value="医生" checked="checked">医生 
							<input type="radio" name="postOffice" value="护士">护士 
							<input type="radio" name="postOffice" value="医药管理员">医药管理员</td>
						</tr>
					</table>
				</td>
				<td><img src="images/login/login_04.jpg" width="237" height="80"
					alt=""></td>
			</tr>
			<tr>
				<td colspan="5"><img src="images/login/login_05.jpg" width="1000"
					height="200" alt=""></td>
			</tr>
		</table>
	</form>
</body>
</html>