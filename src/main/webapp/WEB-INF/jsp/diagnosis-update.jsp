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
			<strong><span class="icon-pencil-square-o"></span>修改诊断信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="diagnosisUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="diagnosisId" name="diagnosisId" value="${diagnosis.diagnosisId }" />
				<input type="hidden" id="patientId" name="patientId" value="${diagnosis.patientId }" />
				<input type="hidden" id="doctorId" name="doctorId" value="${diagnosis.doctorId }" />
				<div class="form-group">
					<div class="label">
						<label>病人名字：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${diagnosis.patient.name } " readonly="readonly"
							name="name"  />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>病人症状：</label>
					</div>
					<div class="field">
						
						<textarea rows="5" class="input" name="content" style="height:200px;" >${diagnosis.symptom }</textarea>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>诊断疾病：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${diagnosis.disease }"
							name="disease" data-validate="required:请输入诊断疾病" />
						<div class="tips"></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="label">
						<label>病人身体情况：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${diagnosis.bodyStatus }"
							name="bodyStatus" data-validate="required:请输入病人身体情况" />
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
			$.post("${pageContext.request.contextPath }/diagnosis/update",
							$("#diagnosisUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/diagnosis/list";
								} else if (data.status == 500) {
									alert(data.msg);
								} 
							});
		}
	</script>
</body>
</html>