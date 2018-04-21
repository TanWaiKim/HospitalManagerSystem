<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>医药管理子系统</title>

<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
</head>
<body style="background-color: #f2f9fd;">
	<div class="header bg-main">
		<div class="logo margin-big-left fadein-top">
			<h1>
				<img src="images/drug-logo.jpg" class="radius-circle rotate-hover"
					height="50" alt="" />医药管理系统
			</h1>
		</div>
		<div class="head-l">
			<a class="button button-little bg-green" href="" target="_blank"><span
				class="icon-home"></span> 登录用户：${currentUser.username }</a> &nbsp;&nbsp;<a href="##"
				class="button button-little bg-blue"><span class="icon-user"></span>
				用户角色:药品员</a> &nbsp;&nbsp;<a class="button button-little bg-red"
				href="login.html"><span class="icon-power-off"></span> 退出登录</a>
		</div>
	</div>
	<div class="leftnav " style="overflow:scroll;">
		<div class="leftnav-title">
			<strong><span class="icon-list "></span>菜单列表</strong>
		</div>

		<h2>
			<span class="icon-user"></span>基础信息管理
		</h2>
		
		<ul style="display: none">
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>供药商管理
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/provider/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加供药商信息
						</a>
					</li>
					<li>
						<a href="provider/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理供药商信息
						</a>
					</li>
				</ul>
			</li>
					
					
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>仓库管理
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/warehouse/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加仓库信息
						</a>
					</li>
					<li>
						<a href="warehouse/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理仓库信息
						</a>
					</li>
				</ul>
			</li>
		</ul>
		
		<h2>
			<span class="icon-medkit"></span>医药管理
		</h2>
		
		<ul style="display: none">
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>医药种类管理
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/drugtype/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加医药种类
						</a>
					</li>
					<li>
						<a href="drugtype/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理医药种类
						</a>
					</li>
				</ul>
			</li>
					
					
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>医药信息管理
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/drug/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加医药信息
						</a>
					</li>
					<li>
						<a href="drug/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理医药信息
						</a>
					</li>
				</ul>
			</li>
		</ul>
		
		<h2>
			<span class="icon-plus-square"></span>采药管理
		</h2>
		
		<ul style="display: none">
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>进药入库
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/purchase/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加采药单
						</a>
					</li>
					<li>
						<a href="purchase/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理采药单
						</a>
					</li>
				</ul>
			</li>
					
					
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>进药退回
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="${pageContext.request.contextPath }/drug/skipToAdd" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>添加退药单
						</a>
					</li>
					<li>
						<a href="drug/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理退药单
						</a>
					</li>
				</ul>
			</li>
		</ul>
		
		<h2>
			<span class="icon-home"></span>库存管理
		</h2>
		
		<ul style="display: none">
			<li>
				<a href="stock/list" target="right">
					<span class="icon-caret-right"></span>查看库存
				</a>
			</li>
			<li>
				<a href="stock/limtList" target="right">
					<span class="icon-caret-right"></span>限制库存调整价格
				</a>
			</li>
			<li>
				<a href="stock/quantityWaring" target="right">
					<span class="icon-caret-right"></span>库存上下限预警
				</a>
			</li>
			<li>
				<a href="stock/validWaring" target="right">
					<span class="icon-caret-right"></span>医药有效期预警
				</a>
			</li>
		</ul>
		<h2>
			<span class="icon-minus-square"></span>销药管理
		</h2>
		
		<ul style="display: none">
			<li>
				<h5>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-right"></span>销药出库
				</h5>
		
				<ul style="display: none">
					<li>
						<a href="prescription/list-data" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>处方药品
						</a>
					</li>
					<li>
						<a href="sales/list" target="right">
							&nbsp;&nbsp;
							<span class="icon-caret-right"></span>管理销药单
						</a>
					</li>
				</ul>
			</li>
					
			<li>
				<a href="info.html" target="right">
					<span class="icon-caret-right"></span>销药退出
				</a>
			</li>
					
		</ul>
		<h2>
			<span class="icon-mobile"></span>统计分析
		</h2>
		
		<ul style="display: none">
			<li>
				<a href="statistics/category" target="right">
					<span class="icon-caret-right"></span>种类统计
				</a>
			</li>
			<li>
				<a href="statistics/purchase" target="right">
					<span class="icon-caret-right"></span>采药分析
				</a>
			</li>
			<li>
				<a href="statistics/priceChange" target="right">
					<span class="icon-caret-right"></span>价格异动
				</a>
			</li>
			<li>
				<a href="info.html" target="right">
					<span class="icon-caret-right"></span>销药分析
				</a>
			</li>					
		</ul>

		<h2>
			<span class="icon-cog"></span>系统设置
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
		 <iframe scrolling="auto" rameborder="0" src="drug-index.jsp" name="right"
			width="100%" height="100%"></iframe>		
	</div>
</body>
</html>