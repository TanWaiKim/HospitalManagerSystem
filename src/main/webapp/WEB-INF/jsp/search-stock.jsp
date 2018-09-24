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
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>药品入库</strong>
		</div>
		<div class="body-content">
			<form method="post" id="stockUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="drugId" name="drugId" value="${purchase.drugId }" />
				<input type="hidden" id="isStock" name="isStock" value="${purchase.isStock }" />
				<input type="hidden" id="purchaseNo" name="purchaseNo" value="${purchase.purchaseNo }" />
				
				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${purchase.drug.drugName }" name="" readonly="readonly"/>
					</div>	
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>入库数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${purchase.quantity }" readonly="readonly"
							id="quantity" name="quantity" data-validate="required:请输入库数量" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>储存仓库：</label>
					</div>
					<div class="field">
						<select name="warehouseNo" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block"  >
							<option value="">未选择</option>
							<c:forEach
							items="${warehouses}" var="warehouse" >
				                <option value="${warehouse.warehouseNo }">${warehouse.warehouseName }
				                </option>
							</c:forEach>
						</select>
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
			$.post("${pageContext.request.contextPath }/stock/add",
							$("#stockUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，入库成功!',

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