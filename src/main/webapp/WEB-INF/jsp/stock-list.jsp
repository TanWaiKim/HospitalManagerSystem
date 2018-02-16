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
							<option value="${warehouseCondition.warehouseNo }" selected="selected">${warehouseCondition.warehouseName }</option>
							<option value="">未选择</option>
							<c:forEach
							items="${warehouseList}" var="warehouse" >
							<option value="${warehouse.warehouseNo }">${warehouse.warehouseName }</option>
							</c:forEach>
						</select>
					</li>
					<li>
						医药名称
						<input type="text" name="drugName" value="${drugName }" />  
					</li>
					<li>
						药品编号
						<input type="text" name="drugNo" value="${drugNo }" />  
					</li>	
					<li>
						操作员
						<input type="text" name="operator" value="${operator }" />  
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
						<th width="90" style="text-align: left; padding-left: 20px;">序号</th>
						<th width="50" >所属仓库</th>
						<th width="90" >医药种类</th>
						<th width="90" >医药名称</th>
						<th width="90" >医药编号</th>
						<th width="50" >规格</th>
						<th width="50" >单位</th>
						<th width="100" >生产日期</th>
						<th width="100" >有效期至</th>
						<th width="50">采药单价</th>
						<th width="50">销药单价</th>
						<th width="50">当前库存</th>
						<th width="50">库存下限</th>
						<th width="50">库存上限</th>
						<th width="100">创建时间</th>
						<th width="100">更新时间</th>
						<th width="80">操作人</th>
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
							<td>${stock.drug.spec }</td>
							<td>${stock.drug.unit }</td>
							<td>${stock.drug.producedTime }</td>
							<td>${stock.drug.validTime }</td>
							<td>${stock.drug.purchasePrice }</td>
							<td>${stock.drug.salePrice }</td>
							<td>${stock.stockQuantity }</td>
							<td>${stock.minQuantity }</td>
							<td>${stock.maxQuantity }</td>
							<td><fmt:formatDate type="date" value="${stock.createTime }"/></td>
							<td><fmt:formatDate type="date" value="${stock.updateTime }"/></td>
							<td>${stock.operator }</td>
							
						</tr>
					</c:forEach>
					<tr>
						<td colspan="17">
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
		//搜索
		function changesearch() {
			$('#currentPage').val('1');
			$('#listform').submit();
		}

	</script>
</body>
</html>