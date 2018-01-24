<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>医生模块</title>

<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
</head>
<body style="background-color: #f2f9fd;">
	<div class="header bg-main">
		<div class="logo margin-big-left fadein-top">
			<h1>
				<img src="images/dc-logo.png" class="radius-circle rotate-hover"
					height="50" alt="" />医院后台管理中心
			</h1>
		</div>
		<div class="head-l">
			<a class="button button-little bg-green" href="" target="_blank"><span
				class="icon-home"></span> 登录用户：admin</a> &nbsp;&nbsp;<a href="##"
				class="button button-little bg-blue"><span class="icon-user"></span>
				用户角色:医生</a> &nbsp;&nbsp;<a class="button button-little bg-red"
				href="login.html"><span class="icon-power-off"></span> 退出登录</a>
		</div>
	</div>
	<div class="leftnav " style="overflow:scroll;">
		<div class="leftnav-title">
			<strong><span class="icon-list "></span>菜单列表</strong>
		</div>
		<h2>
			<span class="icon-user"></span>病人信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/patient/skipToAdd" target="right"><span
					class="icon-caret-right"></span>添加病人信息</a></li>
			<li><a href="patient/list" target="right"><span
					class="icon-caret-right"></span>管理病人信息</a></li>
		</ul>
		
		<h2>
			<span class="icon-user"></span>医生信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/patient/skipToAdd" target="right"><span
					class="icon-caret-right"></span>添加医生信息</a></li>
			<li><a href="patient/list" target="right"><span
					class="icon-caret-right"></span>管理医生信息</a></li>
		</ul>
		
		<h2>
			<span class="icon-user"></span>药品管理员管理
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/patient/skipToAdd" target="right"><span
					class="icon-caret-right"></span>添加药品管理员信息</a></li>
			<li><a href="patient/list" target="right"><span
					class="icon-caret-right"></span>管理药品管理员信息</a></li>
		</ul>
		<h2>
			<span class="icon-stethoscope"></span>诊断信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/diagnosis/skipToAdd" target="right"><span
					class="icon-caret-right"></span>添加诊断信息</a></li>
			<li><a href="diagnosis/list" target="right"><span
					class="icon-caret-right"></span>管理诊断信息</a></li>
		</ul>
		<h2>
			<span class="icon-key"></span>处方信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>添加处方信息</a></li>
			<li><a href="pass.html" target="right"><span
					class="icon-caret-right"></span>管理处方信息</a></li>
		</ul>
		<h2>
			<span class="icon-search"></span>查看健康档案
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>健康档案信息</a></li>
		</ul>
		<h2>
			<span class="icon-wheelchair"></span>特殊病人管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>查看特殊病人信息</a></li>
		</ul>
		
		<h2>
			<span class="icon-user"></span>基础信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>供药商管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>客户管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>仓库管理</a></li>
		</ul>
		
		<h2>
			<span class="icon-hospital-o"></span>医药管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>医药种类管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>医药信息管理</a></li>
		</ul>
		<h2>
			<span class="icon-hospital-o"></span>采药管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>采药单管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>退药单管理</a></li>
		</ul>
		<h2>
			<span class="icon-hospital-o"></span>库存管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
			
					class="icon-caret-right"></span>入库单管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>库存下限预警</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>医药有效期预警</a></li>
		</ul>
		<h2>
			<span class="icon-hospital-o"></span>销药管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>销药单管理</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>退药单管理</a></li>
					
		</ul>
		<h2>
			<span class="icon-hospital-o"></span>进销药统计分析
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>进药分析</a></li>
					<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>销药分析</a></li>
					
		</ul>
		<h2>
			<span class="icon-wrench"></span>系统管理
		</h2>
		
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>系统用户管理</a></li>
		</ul>
		<h2>
			<span class="icon-rss-square"></span>系统公告
		</h2>
	
		<ul style="display: none">
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>添加公告</a></li>
			<li><a href="info.html" target="right"><span
					class="icon-caret-right"></span>管理公告</a></li>
		</ul>
		<h2>
			<span class="icon-cog"></span>基本设置
		</h2>
		
		<ul style="display: none">
			<li><a href="doctor-pass-reset.jsp" target="right"><span
					class="icon-caret-right"></span>修改密码</a></li>	
			<li><a href="doctor-pass-reset.jsp" target="right"><span
					class="icon-caret-right"></span>个人基本信息管理</a></li>	
		</ul>
		
	</div>
	<script type="text/javascript">
		$(function() {
			$(".leftnav h2").click(function() {
				$("#a_leader_txt").text("");
				$(this).next().slideToggle(200);
				$(this).toggleClass("on");
				$("#a_leader").text($(this).text());
				$(this).addClass("on");
				$(".leftnav ul li a").click(function() {
					$("#a_leader_txt").text($(this).text());
					$(".leftnav ul li a").removeClass("on");
					$(this).addClass("on");
				})
				
			})
			

			
			
		});
	</script>
	<ul class="bread">
		<li><a href="#" target="right" class="icon-home">
				首页</a></li>
		<li><a href="#"  id="a_leader">
				</a></li>
		<li><a href="#"  id="a_leader_txt"></a></li>
		
	</ul>
	<div class="admin">
		 <iframe scrolling="auto" rameborder="0" src="wel.jsp" name="right"
			width="100%" height="100%"></iframe>		
	</div>
</body>
</html>