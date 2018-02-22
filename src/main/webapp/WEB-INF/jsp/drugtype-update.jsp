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
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改医药种类</strong>
		</div>
		<div class="body-content">
			<form method="post" id="drugtypeUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${drugtype.id }" />
				<input type="hidden" id="createTime" name="createTime" value="${drugtype.createTime }" />
				
				<div class="form-group">
					<div class="label">
						<label>医药种类名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drugtype.drugtypeName}" name="drugtypeName"
							data-validate="required:请输入医药种类名称" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>医药种类简介：</label>
					</div>
					<div class="field">
						<input type="text" class="input w60" value="${drugtype.remarks}" name="remarks" data-validate="required:请输入医药种类简介"/>
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
			$.post("${pageContext.request.contextPath }/drugtype/update",
							$("#drugtypeUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/drugtype/list";
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