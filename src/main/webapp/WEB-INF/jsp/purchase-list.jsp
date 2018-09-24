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
		action="${pageContext.request.contextPath }/purchase/pageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">采药单列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li><a class="button border-main icon-plus-square-o"
						href="${pageContext.request.contextPath }/purchase/skipToAdd">
							添加采药单</a></li>
					<li>搜索：</li>
					<li>
						供药商
						<select name="providerId" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block"  >
							<c:if test="${providerCondition.id != null}">
								<option value="${providerCondition.id }" selected="selected">${providerCondition.providerName }</option>
							</c:if>
							
							<option value="">未选择</option>
							
							<c:forEach
							items="${providerList}" var="provider" >
							<c:if test="${providerCondition.id != provider.id}">
								<option value="${provider.id }">${provider.providerName }</option>
							</c:if>
							</c:forEach>
						</select>
					</li>
					<li>
						入库状态
						<select name="isStock" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block"  >
							<c:if test="${isStock != null}">
								
							 <c:if test="${isStock == 1}">
								<option value="1" selected="selected">是</option>
							</c:if>
							<c:if test="${isStock == 0}">
								<option value="0" selected="selected">否</option>
							</c:if>
								
								
							</c:if>
							
							<option value="">未选择</option>
							<c:if test="${isStock != null}">
								<c:if test="${isStock == 1}">
									<option value="0">否</option>
								</c:if>
								<c:if test="${isStock == 0}">
									<option value="1">是</option>
								</c:if>
							</c:if>
							<c:if test="${isStock == null}">
								<option value="0">否</option>
								<option value="1">是</option>
							</c:if>
						</select>
					</li>
					<li>
						采购单编号
						<input type="text" placeholder="请输入采购单编号" name="purchaseNo" value="${purchaseNo }" style= "width:120px"/>  
					</li>

					<li>
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; 
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; 
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" class="button border-main icon-search" name="keySearch" onclick="changesearch()"> 
							搜索
						</a>
					</li>
				</ul>

			</div>
			<table class="table table-hover text-center table-bordered">
				<tr>
					<th width="80" style="text-align: left; padding-left: 20px;">序号</th>
					<th width="200" >采药单编号</th>
					<th width="250" >供药商名称</th>
					<th width="100" >总价格（元）</th>
					<th width="100" >备注</th>
					<th width="100" >入库状态</th>					
					<th width="90" >操作员</th>
					<th width="300" >操作</th>
				</tr>
				<c:forEach items="${purchaseList}" var="purchase"  varStatus="status">
					<tr>
						<td style="text-align: left; padding-left: 20px;">
							<input type="checkbox" name="id[]" value="${purchase.id}" />
								<span>
									${(page.currentPage-1)*5+status.count}
								</span>
						</td>

						<td>${purchase.purchaseNo }</td>
						<td>${purchase.provider.providerName }</td>
						<td>${purchase.totalPrice }</td>
						<td>${purchase.remark }</td>
						<td>
							<c:if test="${purchase.isStock == 1}">
								已入库
							</c:if>
							<c:if test="${purchase.isStock == 0}">
								未入库
							</c:if>
						</td>
						
						<td>${purchase.drugAdmin.username }</td>
						
						<td>
							<div class="button-group">
								<a class="button border-green"
									href="${pageContext.request.contextPath }/purchase/findByPurchaseNo?purchaseNo=${purchase.purchaseNo }"><span
									class="icon-edit"></span> 查看
								</a> 
								<a class="button border-blue"
									href="${pageContext.request.contextPath }/purchase/updateByPurchaseNo?purchaseNo=${purchase.purchaseNo }">
									<span class="icon-database"></span> 修改
								</a>	
								<a class="button border-red"
									href="javascript:judgeDelete(${purchase.id })"><span
									class="icon-trash-o"></span> 删除
								</a>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td style="text-align: left; padding: 19px 0; padding-left: 20px;border-style:none;"><input
						type="checkbox" id="checkall" /> 全选</td>
					<td colspan="5" style="text-align: left; padding-left: 20px;border-style:none;"><a
						href="javascript:deleteBatch()"
						class="button border-red icon-trash-o" style="padding: 5px 15px;" >
							删除</a></td>

				</tr>
				<tr>
					<td colspan="6" style="border-style:none;">
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
	 	function getSelectionsIds(){
	 		var obj=document.getElementsByName('id[]'); //选择所有name="'id[]'"的对象，返回数组 
	 		//取到对象数组后，我们来循环检测它是不是被选中 
	 		var s=''; 
	 		for(var i=0; i<obj.length; i++){ 
	 		if(obj[i].checked){
	 			s+=obj[i].value+','; //如果选中，将value添加到变量s中 
	 			} 	
	 		} 
	 		//那么现在来检测s的值就知道选中的复选框的值了 
	 		//alert(s==''?'你还没有选择任何内容！':s); 
	 		return s;
	    }
	 	
		function judgeDelete(id) {
			var d = dialog({
				title: '温馨提示',
				content: '确定要删除该条记录吗？',
				okValue: '确定',
				ok: function () {
					var params = {"id":id};
					$.post("${pageContext.request.contextPath }/purchase/deleteOne",params ,function(data){
	        			if(data.status == 200){
							var d1 = dialog({
								okValue: '确定',
								title: '温馨提示',
								content: '恭喜您，删除成功!',

								width: 200,
								height: 50,
								ok: function () {
									location.href = "${pageContext.request.contextPath }/purchase/list";
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
									location.href = "${pageContext.request.contextPath }/purchase/list";
								}
							});
							d1.showModal();
	        			}
	        		});
				},
				cancelValue: '取消',
				cancel: function () {
					location.href = "${pageContext.request.contextPath }/purchase/list";
				}
			});
			d.showModal();
		}

		//搜索
		function changesearch() {
			$('#currentPage').val('1');
			$('#listform').submit();
		}

		//全选
		$("#checkall").click(function() {
			$("input[name='id[]']").each(function() {
				if (this.checked) {
					this.checked = false;
				} else {
					this.checked = true;
				}
			});
		});

		//批量删除
		function deleteBatch() {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {
				var d = dialog({
					title: '温馨提示',
					content: '确定要删除该批记录吗？',
					okValue: '确定',
					ok: function () {
						var ids = getSelectionsIds();
						var params = {"ids":ids};
						$.post("${pageContext.request.contextPath }/purchase/deleteBatch",params ,function(data){
		        			if(data.status == 200){
								var d1 = dialog({
									okValue: '确定',
									title: '温馨提示',
									content: '恭喜您，删除成功!',

									width: 200,
									height: 50,
									ok: function () {
										location.href = "${pageContext.request.contextPath }/purchase/list";
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
										location.href = "${pageContext.request.contextPath }/purchase/list";
									}
								});
								d1.showModal();
		        			}
		        		});
					},
					cancelValue: '取消',
					cancel: function () {
						location.href = "${pageContext.request.contextPath }/purchase/list";
					}
				});
				d.showModal();
			} else {
				var d = dialog({
					okValue: '确定',
					title: '温馨提示',
					content: '请选择您要删除的内容！',

					width: 200,
					height: 50,
					ok: function () {
						
					}
				});
				d.showModal();
				return false;
			}
		}
	</script>
</body>
</html>