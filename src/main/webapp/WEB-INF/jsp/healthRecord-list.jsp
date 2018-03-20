<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/pintuer.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/all.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
<script src="${pageContext.request.contextPath }/js/list.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath }/healthRecord/pageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 健康档案列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li>搜索：</li>
					<li>病人姓名 <input type="text"
						name="patientName" value="${patientName }" /> 
					</li>

					<li>
						<a href="javascript:void(0)"
						class="button border-main icon-search" name="keySearch"
						onclick="changesearch()"> 搜索</a></li>
				</ul>

			</div>
			<table class="table table-hover text-center table-bordered">
				<tr>
					<th width="90" style="text-align: left; padding-left: 20px;">序号</th>
					<th width="120" >病人编号</th>
					<th width="120" >病人姓名</th>
					<th width="120" >病人性别</th>
					<th width="200" >病人症状</th>
					<th width="270">操作</th>
				</tr>
				 <c:forEach
					items="${diagnosisList}" var="diagnosis" varStatus="status">
					<tr>
						<td style="text-align: left; padding-left: 20px;"><span>${(page.currentPage-1)*3+status.count}</span>
						</td>
						
						<td>${diagnosis.patient.patientId}</td>
						<td>${diagnosis.patient.name }</td>
						<td>${diagnosis.patient.sex }</td>
						<td>${diagnosis.symptom }</td>
						
						
						
						<td><div class="button-group">
								<a class="button border-main"
									href="${pageContext.request.contextPath }/healthRecord/findById/${diagnosis.patient.patientId }"><span
									class="icon-edit"></span> 详情</a>
							</div></td>
					</tr>
				</c:forEach>

				<tr>
					<td colspan="5" style="border-style:none;">
						<div class='page fix'>
							共 <b>${page.totalNumber}</b> 条
							<c:if test="${page.currentPage != 1}">
								<a href="javascript:changeCurrentPage('1')" class='first'>首页</a>
								<a href="javascript:changeCurrentPage('${page.currentPage-1}')"
									class='pre'>上一页</a>
							</c:if>
							当前第<span>${page.currentPage}/${page.totalPage}</span>页
							<c:if test="${page.currentPage != page.totalPage}">
								<a href="javascript:changeCurrentPage('${page.currentPage+1}')"
									class='next'>下一页</a>
								<a href="javascript:changeCurrentPage('${page.totalPage}')"
									class='last'>末页</a>
							</c:if>
							跳至&nbsp;<input id="currentPageText" type='text'
								value='${page.currentPage}' class='allInput w28' />&nbsp;页&nbsp;
							<a
								href="javascript:changeCurrentPage($('#currentPageText').val())"
								class='go'>GO</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
	//搜索
	function changesearch() {
		$('#currentPage').val('1');
		$('#listform').submit();
	}
	
	</script>
</body>
</html>