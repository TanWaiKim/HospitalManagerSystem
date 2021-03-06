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
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/pintuer.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改病床信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="sickbedAddForm" onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${sickbed.id }" />
				<input type="hidden" id="created" name="created" value="${sickbed.created }" />
				<div class="form-group">
					<div class="label">
						<label>病床编号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${sickbed.id }" name="id" readonly="readonly"
							data-validate="required:请输入病床编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>是否处理：</label>
					</div>
					<div class="field">
						<select name="isFree" class="input"
							style="width: 85px; line-height: 17px; display: inline-block">
							<option value="选择"
								<c:if test="${sickbed.isFree == ''}">selected="selected"</c:if>>选择
							</option>
							<option value="是"
								<c:if test="${sickbed.isFree == '是'}">selected="selected"</c:if>>是</option>
							<option value="否"
								<c:if test="${sickbed.isFree == '否'}">selected="selected"</c:if>>否</option>
						</select>
					</div>
				</div>
				
		<div class="form-group">
			<div class="label">
				<label></label>
			</div>
			<div class="field">
						<button class="button bg-main icon-check-square-o"
							id="addSubmit" onclick="addForm()">提交</button>
					</div>
		</div>
		</form>
	</div>
	</div>
	<script type="text/javascript">
		function addForm() {
			//ajax的post方式提交表单
			//$("#sickbedAddForm").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/sickbed/update",
							$("#sickbedAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/sickbed/list";
								} else if (data.status == 500) {
									alert(data.msg);
								} 
							});
		}
	</script>
</body>
</html>