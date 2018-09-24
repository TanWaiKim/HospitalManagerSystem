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
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/pintuer.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/admin.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script> 
 
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>

<script type="text/javascript">

	$(function()  {
		$("#purchasePrice").blur(function() {
			var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
			var purchasePrice = document.getElementById("purchasePrice");
			if (!reg.test(purchasePrice.value) && purchasePrice.value.toString().length != 0) {
				var d = dialog({
					okValue: '确定',
					title: '温馨提示',
					content: '采购单价必须为数值类型!',
	
					width: 200,
					height: 50,
					ok: function () {
						
					}
				});
				d.showModal();
			}
		})
	});
	
	$(function()  {
		$("#salePrice").blur(function() {
			var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
			var salePrice = document.getElementById("salePrice");
			if (!reg.test(salePrice.value) && salePrice.value.toString().length != 0) {
				var d = dialog({
					okValue: '确定',
					title: '温馨提示',
					content: '销售单价必须为数值类型!',
	
					width: 200,
					height: 50,
					ok: function () {
						
					}
				});
				d.showModal();
			}
		})
	});
	
	$(function()  {
		$("#drugNo").blur(function() {
			var reg = /^[0-9]+.?[0-9]*$/;
			var drugNo = document.getElementById("drugNo");
			if ((!reg.test(drugNo.value) && drugNo.value.toString().length != 0) || drugNo.value.toString().length != 8) {
				var d = dialog({
					okValue: '确定',
					title: '温馨提示',
					content: '产品批号必须为8位数字!',

					width: 200,
					height: 50,
					ok: function () {
						
					}
				});
				d.showModal();
			}
		})
	});

	$(function()  {
		$("#quantity").blur(function() {
			var reg = /^[0-9]+.?[0-9]*$/;
			var quantity = document.getElementById("quantity");
			if (!reg.test(quantity.value) && quantity.value.toString().length != 0) {
				var d = dialog({
					okValue: '确定',
					title: '温馨提示',
					content: '数量必须为整数类型!',

					width: 200,
					height: 50,
					ok: function () {
						
					}
				});
				d.showModal();
			}
		})
	});
</script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加采药单</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="purchaseAddForm"
				onsubmit="return false;">
				<input type="hidden" id="drugAdminId" name="drugAdminId" value="${drugAdmin.id }" />
		
				<div class="form-group">
					<div class="label">
						<label>供药商：</label>
					</div>
					<div class="field">
						<select name="providerId" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入供药商" >
							<option value="">选择</option>
							<c:forEach
							items="${providerList}" var="provider" >
							<option value="${provider.id }">${provider.providerName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<select name="drugName" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入医药名称" >
							<option value="">选择</option>
							<c:forEach
							items="${drugList}" var="drug" >
							<option value="${drug.drugName }">${drug.drugName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>采购单价：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入采购单价(数值类型)" class="input w50" value="" id="purchasePrice"
							name="purchasePrice" data-validate="required:请输入采购单价(数值类型)" />元
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>销售单价：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入销售单价(数值类型)" class="input w50" value="" id="salePrice"
							name="salePrice" data-validate="required:请输入销售单价(数值类型)" />元
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>生产日期：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入生产日期" class="Wdate" value="" name="produceTime"  
						 data-validate="required:请输入生产日期"
						 onClick="WdatePicker({lang:'zh-cn'})" />  
						 <div class="tips"></div>
					</div>
					
				</div>						
	
				<div class="form-group">
					<div class="label">
						<label>有效期至：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入有效期至" class="Wdate" value="" name="validTime" 
						 data-validate="required:请输入有效期至"
						 onClick="WdatePicker({lang:'zh-cn'})" />  
						 <div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>产品批号：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入产品批号(8位数字)" class="input w50" value="" id="drugNo"
							name="drugNo" data-validate="required:请输入产品批号(8位数字)" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入数量(整数类型)" class="input w50" value="" id="quantity"
							name="quantity" data-validate="required:请输入数量(整数类型)" />
						<div class="tips"></div>
					</div>
				</div>	
				
				<div class="form-group">
					<div class="label">
						<label>备注：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入备注" class="input w50" value=""
							name="remark" data-validate="required:请输入备注" />
						<div class="tips"></div>
					</div>
				</div>				

				
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button class="button bg-main icon-check-square-o" id="addSubmit"
							onclick="addForm()">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function addForm() {
			//ajax的post方式提交表单
			//$("#patientAddForm").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/purchase/add",
							$("#purchaseAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/purchase/list";
										}
									});
									d.showModal();
								} else if (data.status == 500) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '很抱歉，添加失败!',

										width: 200,
										height: 50,
										ok: function () {
											
										}
									});
									d.showModal();
								} else if (data.status == 505) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: data.msg,

										width: 200,
										height: 50,
										ok: function () {
											
										}
									});
									d.showModal();
								}
							});
		}
	</script>
</body>
</html>