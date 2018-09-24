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
		$("#drugNo").blur(function() {
			var reg = /^[0-9]+.?[0-9]*$/;
			var drugNo = document.getElementById("drugNo");
			if (!reg.test(drugNo.value) || drugNo.value.toString().length != 8) {
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
	
	$("#quantity").blur(function() {
		var reg = /^[0-9]+.?[0-9]*$/;
		var quantity = document.getElementById("quantity");
		if (!reg.test(quantity.value)) {
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
			<strong><span class="icon-pencil-square-o"></span>添加药品项目</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="backItemAddForm"
				onsubmit="return false;">
				<input type="hidden" id="backNo" name="backNo" value="${backNo }" />
				<input type="hidden" id="providerId" name="providerId" value="${providerId }" />

				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<select name="drugName" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入药品名称" >
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
						<label>产品批号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" id="drugNo" value=""
							name="batchNo" placeholder="请输入8位产品批号" data-validate="required:请输入产品批号" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" id="quantity" value=""
							name="quantity" placeholder="请输入数量，数值类型" data-validate="required:请输入数量" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>退药原因：</label>
					</div>
					<div class="field">
						<textarea rows="3" cols="5" class="input" name="reason" placeholder="请输入退药原因，不超过20个字" style="height:100px;" data-validate="required:请输入退药原因"></textarea>
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
			$.post("${pageContext.request.contextPath }/back/addDrug",
							$("#backItemAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/back/findByBackNo?backNo=${backNo }";
										}
									});
									d.showModal();
								} else if (data.status == 500) {
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