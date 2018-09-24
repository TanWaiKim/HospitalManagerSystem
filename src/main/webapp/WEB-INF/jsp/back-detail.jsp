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
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath }/back/pageByCondition1?backNo=${back.backNo }"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-align-left">退药单信息</strong>
			</div>
			<div class="padding border-bottom">
				<table class="table table-hover text-center table-bordered">
					<tr class="bg-blue-light">
						<th width="200" >退药单编号</th>
						<th width="300" >供药商名称</th>
						<th width="100" >合计金额（元）</th>					
						<th width="150" >创建时间</th>
					</tr>
					<tr>
						<td>
							${back.backNo }
						</td>
						<td>
							${back.provider.providerName }
						</td>
						<td>
							${back.totalPrice }
						</td>
						<td>
							<fmt:formatDate type="date" value="${back.createTime }"/>
						</td>
					</tr>
				</table>	
				<br/>	
				
				<br/>
				<ul class="search" style="padding-left: 10px;">
					<li>
						<a class="button border-main icon-plus-square-o" href="${pageContext.request.contextPath }/back/skipToAddDrug?backNo=${back.backNo }&&providerId=${back.provider.id }">
							添加药品项目
						</a>
					</li>
				</ul>	
					
			</div>
			

			
			<div class="panel-head">
				<strong class="icon-reorder">药品清单</strong>
			</div>
			
			<table class="table table-hover text-center table-bordered">
				<tr class="bg-blue-light">
					<th width="70" style="text-align: left; padding-left: 20px;">序号</th>
					<th width="400" >医药名称</th>
					<th width="150" >产品批号</th>
					<th width="100" >数量</th>
					<th width="100" >单位</th>	
					<th width="200" >退药单价（元）</th>		
					<th width="150" >总价（元）</th>
					<th width="260" >退药原因</th>
					<th width="300" >操作</th>
				</tr>
				<c:forEach items="${backList}" var="backItem"  varStatus="status">
					<tr>
						<td style="text-align: left; padding-left: 20px;">
								<span>
									${(page.currentPage-1)*3+status.count}
								</span>
						</td>

						<td>${backItem.drug.drugName }</td>
						<td>${backItem.drug.drugNo }</td>
						<td>${backItem.quantity }</td>
						<td>${backItem.drug.unit }</td>
						<td>${backItem.drug.purchasePrice }</td>
						<td>${backItem.totalPrice }</td>
						<td>${backItem.reason }</td>
						
						<td><div class="button-group">
								<a class="button border-main"
									href="${pageContext.request.contextPath }/back/updateByBackNoAndDrugId?drugId=${backItem.drugId }&&backNo=${back.backNo}">
									<span class="icon-edit"></span> 修改
								</a> 							
								<a class="button border-red"
									href="javascript:judgeDelete(${backItem.drugId },'${backItem.backNo }')">
									<span class="icon-trash-o"></span> 删除
								</a>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="10" style="border-style:none;">
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

	
							   
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		function judgeDelete(drugId,backNo) {
			var d = dialog({
				title: '温馨提示',
				content: '确定要删除该条记录吗？',
				okValue: '确定',
				ok: function () {
					var params = {"drugId":drugId,"backNo":backNo};
					$.post("${pageContext.request.contextPath }/back/deleteOneByBackNoAndDrugId",params ,function(data){
	        			if(data.status == 200){
							var d1 = dialog({
								okValue: '确定',
								title: '温馨提示',
								content: '恭喜您，删除成功!',

								width: 200,
								height: 50,
								ok: function () {
									location.href = "${pageContext.request.contextPath }/back/findByBackNo?backNo=${back.backNo }";
								}
							});
							d1.showModal();
	        			}
	        			if(data.status == 500){
	        				
							var d1 = dialog({
								okValue: '确定',
								title: '温馨提示',
								content: data.msg,

								width: 200,
								height: 50,
								ok: function () {
									location.href = "${pageContext.request.contextPath }/back/findByBackNo?backNo=${back.backNo }";
								}
							});
							d1.showModal();
	        			}
	        		});
				},
				cancelValue: '取消',
				cancel: function () {
					location.href = "${pageContext.request.contextPath }/back/findByBackNo?backNo=${back.backNo }";
				}
			});
			d.showModal();
		};
		
		//搜索
		function changesearch() {
			$('#currentPage').val('1');
			$('#listform').submit();
		}
	</script>
</body>
</html>