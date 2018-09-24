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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://jqueryui.com/resources/demos/style.css">
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
<script src="${pageContext.request.contextPath }/js/list.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath }/stock/pageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 库存信息列表</strong> 
			</div>
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li>搜索：</li>
					<li>
						仓库
						<select name="warehouseNo" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block"  >
							<c:if test="${warehouseCondition.warehouseNo != null}">
								<option value="${warehouseCondition.warehouseNo }" selected="selected">${warehouseCondition.warehouseName }</option>
							</c:if>
							
							<option value="">未选择</option>
							<c:forEach
							items="${warehouseList}" var="warehouse" >
							<c:if test="${warehouseCondition.warehouseNo != warehouse.warehouseNo}">
								<option value="${warehouse.warehouseNo }">${warehouse.warehouseName }</option>
							</c:if>
							</c:forEach>
						</select>
					</li>

					<li>
						医药名称
						<input type="text" placeholder="请输入医药名称" id="drugName" name="drugname" value="${drugname }" />  
					</li>
			
					<li>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="button border-main icon-search" name="keySearch" onclick="changesearch()"> 
							搜索
						</a>
					</li>
				</ul>

			</div>
			<div class="text-little">
				<table class="table table-hover text-center table-bordered">
					<tr>
						<th width="70" style="text-align: left; padding-left: 20px;">序号</th>
						<th width="70" >存储仓库</th>
						<th width="90" >医药种类</th>
						<th width="90" >医药名称</th>
						<th width="90" >产品批号</th>
						<th width="100" >生产日期</th>
						<th width="100" >有效期至</th>
						<th width="70">采药单价（元）</th>
						<th width="70">销药单价（元）</th>
						<th width="70">当前库存</th>
						<th width="50" >单位</th>
						<th width="50" >规格</th>
						<th width="100">入库时间</th>
						<th width="100">更新时间</th>
					</tr>
					<c:forEach items="${stockList}" var="stock"  varStatus="status">
						<tr>
							<td style="text-align: left; padding-left: 20px;">
								<span>
									&nbsp;&nbsp;${(page.currentPage-1)*5+status.count}
								</span>
							</td>
							<td>${stock.warehouse.warehouseName }</td>
							<td>${stock.drug.drugtype.drugtypeName }</td>
							<td>${stock.drug.drugName }</td>
							<td>${stock.drug.drugNo }</td>
							<td>${stock.drug.produceTime }</td>
							<td>${stock.drug.validTime }</td>
							<td>${stock.drug.purchasePrice }</td>
							<td>${stock.drug.salePrice }</td>
							<td>${stock.stockQuantity }</td>
							<td>${stock.drug.unit }</td>
							<td>${stock.drug.spec }</td>
							<td><fmt:formatDate type="date" value="${stock.createTime }"/></td>
							<td><fmt:formatDate type="date" value="${stock.updateTime }"/></td>
							
						</tr>
					</c:forEach>
					<tr>
						<td colspan="13" style="border-style:none;">
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