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
	
	$(function()  {
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
			<strong><span class="icon-pencil-square-o"></span>修改退回药品信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="backItemUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${backDto.id }" />
				<input type="hidden" id="backNo" name="backNo" value="${backDto.backNo }" />
				<input type="hidden" id="drugId" name="drugId" value="${backDto.drugId }" />
				<input type="hidden" id="providerId" name="providerId" value="${backDto.providerId }" />
				<input type="hidden" id="oldBackItemQuantity" name="oldBackItemQuantity" value="${backDto.oldBackItemQuantity }" />
				
				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${backDto.drugName }" name="drugName" readonly="readonly"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>产品批号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${backDto.batchNo }" name="" readonly="readonly"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${backDto.quantity }"
							id="quantity" name="quantity" data-validate="required:请输入数量" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>退药原因：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${backDto.reason }"
							id="reason" name="reason" data-validate="required:请输入退药原因" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button class="button bg-main icon-check-square-o"
							id="updateSubmit" onclick="updateForm()">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function updateForm() {
			//ajax的post方式提交表单
			//$("#patientUpdateForm").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/back/updateItem",
							$("#backItemUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，修改成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/back/findByBackNo?backNo=${backDto.backNo }";
										}
									});
									d.showModal();
								} else if (data.status == 500) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '很抱歉，修改失败!',

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