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
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改销售药品信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="salesItemUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${salesDto.id }" />
				<input type="hidden" id="salesNo" name="salesNo" value="${salesDto.salesNo }" />
				<input type="hidden" id="patientId" name="patientId" value="${salesDto.patientId }" />
				<input type="hidden" id="totalQuantity" name="totalQuantity" value="${salesDto.totalQuantity }" />
				<input type="hidden" id="totalPrice" name="totalPrice" value="${salesDto.totalPrice }" />
				<input type="hidden" id="operator" name="operator" value="${salesDto.operator }" />
				<input type="hidden" id="remarks" name="remarks" value="${salesDto.remarks }" />
				<input type="hidden" id="oldSalesItemQuantity" name="oldSalesItemQuantity" value="${salesDto.oldSalesItemQuantity }" />
				<input type="hidden" id="salePrice" name="salePrice" value="${salesDto.salePrice }" />
				<input type="hidden" id="batchNo" name="batchNo" value="${salesDto.batchNo }" />
				
				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<select name="drugId" class="input"
							style="width: 155px; line-height: 17px; display: inline-block" >
							<option value="${salesDto.drugId }" selected="selected">${salesDto.drugName }</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${salesDto.quantity }"
							name="quantity" data-validate="required:请输入数量" />
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
			$.post("${pageContext.request.contextPath }/salesItem/update",
							$("#salesItemUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/sales/findBySalesNo?salesNo=${salesDto.salesNo }";
								} else if (data.status == 500) {
									alert("修改失败!");
								} else if (data.status == 505) {
									alert(data.msg);
								}
							});
		}
	</script>
</body>
</html>