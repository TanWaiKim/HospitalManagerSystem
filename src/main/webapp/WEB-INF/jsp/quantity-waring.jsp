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
		action="${pageContext.request.contextPath }/stock/quantityWaringPageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 库存上下限预警信息列表</strong> 
			</div>
			<div class="text-little">
				<table class="table table-hover text-center table-bordered">
					<tr>
						<th width="100" style="text-align: left; padding-left: 20px;">序号</th>
						<th width="100" >医药种类</th>
						<th width="200" >医药名称</th>
						<th width="100">当前库存数量</th>
						<th width="100">库存下限</th>
						<th width="100">库存上限</th>
						<th width="100" >计量单位</th>
						<th width="200">预警信息</th>
						<th width="200">操作</th>
					</tr>
					<c:forEach items="${stockList}" var="stock"  varStatus="status">
						<tr>
							<td style="text-align: left; padding-left: 20px;">
								<span>
									&nbsp;&nbsp;${(page.currentPage-1)*6+status.count}
								</span>
							</td>
							<td>${stock.drug.drugtype.drugtypeName }</td>
							<td>${stock.drug.drugName }</td>
							<td>${stock.stockQuantity }</td>
							<td>${stock.minQuantity }</td>
							<td>${stock.maxQuantity }</td>
							<td>${stock.drug.unit }</td>
							<td>${stock.quantityWaring }</td>
							<td>
								<div class="button-group">
									<a class="button border-main"
										href="${pageContext.request.contextPath }/purchase/skipToAdd"><span
										class="icon-plus"></span>采药</a> <a class="button border-red"
										href="${pageContext.request.contextPath }/back/skipToAdd?type=1"><span
										class="icon-minus"></span> 退药</a>
								</div>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="9" style="border-style:none;">
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
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</table>
			</div>
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