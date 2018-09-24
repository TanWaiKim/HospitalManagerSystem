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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://jqueryui.com/resources/demos/style.css">
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath }/stock/limitPageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">库存调整信息列表</strong> 
			</div>
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li>搜索：</li>
					<li>
						医药名称
						<input type="text" placeholder="请输入医药名称" id="drugName" name="drugName" value="${drugName }" />  
					</li>	
		
					<li>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="button border-main icon-search" name="keySearch" onclick="changesearch()"> 
							搜索
						</a>
					</li>
				</ul>

			</div>
			<div class="text-little">
				<table class="table table-hover text-center table-bordered">
					<tr>
						<th width="100" style="text-align: left; padding-left: 20px;">序号</th>
						<th width="300" >医药名称</th>
						<th width="200" >计量单位</th>
						<th width="200">当前库存数量</th>
						<th width="150">库存下限</th>
						<th width="150">库存上限</th>
						<th width="200" >预售均价（元）</th>
					    <th width="600" >操作</th>
					</tr>
					<c:forEach items="${stockList}" var="stock"  varStatus="status">
						<tr>
							<td style="text-align: left; padding-left: 20px;">
								<span>
									&nbsp;&nbsp;${(page.currentPage-1)*5+status.count}
								</span>
							</td>
							<td>${stock.drug.drugName }</td>
							<td>${stock.drug.unit }</td>
							<td>${stock.stockQuantity }</td>
							<td>${stock.minQuantity }</td>
							<td>${stock.maxQuantity }</td>
							<td>${stock.drug.salePrice }</td>							
							<td>
								<div class="button-group">
									<a class="button border-main" href="${pageContext.request.contextPath }/stock/findByDrugName?drugname=${stock.drugname }">
										<span class="icon-edit">设置上下限</span> 
									</a> 
								</div>
								<div class="button-group">
									<a class="button border-main" href="${pageContext.request.contextPath }/stock/findByDrugName1?drugname=${stock.drugname }">
										<span class="icon-edit">调整价格</span> 
									</a> 
								</div>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8">
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
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		
		$("#drugName").autocomplete({
			source : "<c:url value="/drug/auto"/>",
			minLength : 1
		});
		//搜索
		function changesearch() {
			$('#currentPage').val('1');
			$('#listform').submit();
		}

	</script>
</body>
</html>