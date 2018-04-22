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
			<strong><span class="icon-pencil-square-o"></span>修改仓库信息信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="warehouseUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${warehouse.id }" />
				<input type="hidden" id="warehouseNo" name="warehouseNo" value="${warehouse.warehouseNo }" />				
				<input type="hidden" id="createTime" name="createTime" value="${warehouse.createTime }" />
				
				<div class="form-group">
					<div class="label">
						<label>仓库名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${warehouse.warehouseName}" name="warehouseName"
							data-validate="required:请输入仓库名称" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>仓库位置：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${warehouse.location}" name="location"
							data-validate="required:请输入仓库位置" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>管理员：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${warehouse.manager}" name="manager"
							data-validate="required:请输入管理员" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>管理员手机号码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${warehouse.phone}" name="phone"
							data-validate="required:请输入联络人电话" />
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
			$.post("${pageContext.request.contextPath }/warehouse/update",
							$("#warehouseUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，修改成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/warehouse/list";
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