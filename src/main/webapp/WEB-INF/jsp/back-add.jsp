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
		$("#batchNo").blur(function() {
			var reg = /^[0-9]+.?[0-9]*$/;
			var batchNo = document.getElementById("batchNo");
			if (!reg.test(batchNo.value) || batchNo.value.toString().length != 8) {
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
		$("#backSum").blur(function() {
			var reg = /^[0-9]+.?[0-9]*$/;
			var backSum = document.getElementById("backSum");
			if (!reg.test(backSum.value)) {
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
			<strong><span class="icon-pencil-square-o"></span>添加退药单</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="backAddForm"
				onsubmit="return false;">
		
				<div class="form-group">
					<div class="label">
						<label>退药类型：</label>
					</div>
					
					<div class="field">
						<select name="backType" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请选择退药类型">
							
							<c:if test="${type == 1}">
								<option value="药商退药" selected="selected">药商退药</option>
							</c:if>
							
							<c:if test="${type == 2 }">
								<option value="病人退药" selected="selected">病人退药</option>
							</c:if>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<c:if test="${type == 1}">
					<div class="form-group">
						<div class="label">
							<label>药商名称：</label>
						</div>
						<div class="field">
							<select name="backObject" class="input" 
								style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入药商名称" >
								<option value="">选择</option>
								<c:forEach
								items="${providerList}" var="provider" >
								<option value="${provider.providerName }">${provider.providerName }</option>
								</c:forEach>
							</select>
							<div class="tips"></div>
						</div>
					</div>
				
				</c:if>
				
				<c:if test="${type == 2}">
					<div class="form-group">
						<div class="label">
							<label>病人名称：</label>
						</div>
						<div class="field">
							<select name="backObject" class="input" 
								style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入病人名称" >
								<option value="">选择</option>
								<c:forEach
								items="${patientList}" var="patient" >
								<option value="${patient.patientId }">${patient.name }</option>
								</c:forEach>
							</select>
							<div class="tips"></div>
						</div>
					</div>
				
				</c:if>
				
				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<select name="drugId" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入药品名称" >
							<option value="">选择</option>
							<c:forEach
							items="${drugList}" var="drug" >
							<option value="${drug.id }">${drug.drugName }</option>
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
						<input type="text" class="input w50" id="batchNo" value=""
							name="batchNo" data-validate="required:请输入产品批号" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" id="backSum" value=""
							name="backSum" data-validate="required:请输入数量" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>退药原因：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="backReason" data-validate="required:请输入退药原因" />
						<div class="tips"></div>
					</div>
				</div>		
				
				<div class="form-group">
					<div class="label">
						<label>操作员：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${operator }"
							name="operator" readonly="readonly" />
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
			$.post("${pageContext.request.contextPath }/back/add",
							$("#backAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/back/listPatientBack";
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