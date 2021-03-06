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
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加诊断信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="diagnosisAddForm"
				onsubmit="return false;">

				<div class="form-group">
					<div class="label">
						<label>病人编号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" name="patientId"
							id="patientId" data-validate="required:请输入病人id" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>病人症状：</label>
					</div>
					<div class="field">
		
						<textarea rows="5" class="input" name="symptom" style="height:200px;" data-validate="required:请输入病人症状"></textarea>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>诊断疾病：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="disease"
							data-validate="required:请输入诊断疾病" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>病人身体情况：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="bodyStatus"
							data-validate="required:请输入病人身体情况" />
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
		$("#patientId").autocomplete({
			source : "<c:url value="/diagnosis/auto"/>",
			minLength : 1
		});

		function addForm() {
			//ajax的post方式提交表单
			//$("#diagnosisAddForm").serialize()将表单序列号为key-value形式的字符串
			$
					.post(
							"${pageContext.request.contextPath }/diagnosis/add",
							$("#diagnosisAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("添加成功!");
									var pId = document
											.getElementById('patientId').value;
									location.href = "${pageContext.request.contextPath }/prescription/skipToAdd1/"
											+ pId;
								} else if (data.status == 500) {
									alert(data.msg);
								} 
							});
		}
	</script>
</body>
</html>