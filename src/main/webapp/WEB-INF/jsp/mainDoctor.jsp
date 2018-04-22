<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				class="icon-home"></span> 登录用户：${doctorInfo.name }</a> &nbsp;&nbsp;<a href="##"
				class="button button-little bg-blue"><span class="icon-user"></span>
				用户角色:${postOffice }</a> &nbsp;&nbsp;<a class="button button-little bg-red"
				href="${pageContext.request.contextPath }/dLogout"><span class="icon-power-off"></span> 退出登录</a>
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
			<li><a href="prescription/skipToAdd" target="right"><span
					class="icon-caret-right"></span>添加处方信息</a></li>
			<li><a href="prescription/list" target="right"><span
					class="icon-caret-right"></span>管理处方信息</a></li>
		</ul>
		<h2>
			<span class="icon-search"></span>查看健康档案
		</h2>
		
		<ul style="display: none">
			<li><a href="healthRecord/list" target="right"><span
					class="icon-caret-right"></span>健康档案信息</a></li>
		</ul>
		<h2>
			<span class="icon-wheelchair"></span>特殊病人查询
		</h2>
		
		<ul style="display: none">
			<li><a href="specialPatient/list" target="right"><span
					class="icon-caret-right"></span>查看特殊病人信息</a></li>
		</ul>
		
		<h2>
			<span class="icon-wrench"></span>病人就诊统计
		</h2>
		
		<ul style="display: none">
			<li><a href="patientStatistics/category" target="right"><span
					class="icon-caret-right"></span>人群类型分析</a></li>
			<li><a href="patientStatistics/month" target="right"><span
					class="icon-caret-right"></span>月来访统计</a></li>
		</ul>
		
		
		<h2>
			<span class="icon-cog"></span>基本信息设置
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/dPassReset" target="right"><span
					class="icon-caret-right"></span>修改密码</a></li>	
			<li><a href="${pageContext.request.contextPath }/personalInfo" target="right"><span
					class="icon-caret-right"></span>查看个人信息</a></li>	
		</ul>
		
		<h2>
			<span class="icon-cog"></span>其他信息管理
		</h2>
		
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/sickbed/list" target="right"><span
					class="icon-caret-right"></span>病床管理</a></li>	
			<li><a href="${pageContext.request.contextPath }/stayHospital/list" target="right"><span
					class="icon-caret-right"></span>住院信息管理</a></li>
		</ul>
		
		<h2>
			<span class="icon-rss-square"></span>系统公告
		</h2>
	
		<ul style="display: none">
			<li><a href="${pageContext.request.contextPath }/notice/list" target="right"><span
					class="icon-caret-right"></span>查看公告</a></li>
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
	
		$(function() {
			$(".leftnav h5").click(function() {
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