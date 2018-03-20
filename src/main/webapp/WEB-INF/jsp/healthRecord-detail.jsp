<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

	<div class="panel">
		<div class="panel-head">
			<strong>健康档案详细信息</strong>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td style="width:300px;border:1px solid cadetblue;">病人编号：</td>
					<td style="border:1px solid cadetblue;">${patient.patientId }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">病人姓名：</td>
					<td style="border:1px solid cadetblue;">${patient.name }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">性别：</td>
					<td style="border:1px solid cadetblue;">${patient.sex }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">年龄：</td>
					<td style="border:1px solid cadetblue;">${patient.age }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">住址：</td>
					<td style="border:1px solid cadetblue;">${patient.address }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">入院时间：</td>
					<td style="border:1px solid cadetblue;"><fmt:formatDate type="date" value="${patient.stayHospital.time }"/></td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">联系电话：</td>
					<td style="border:1px solid cadetblue;">${patient.phone }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">症状：</td>
					<td style="border:1px solid cadetblue;">${patient.diagnosis.symptom }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">诊断疾病：</td>
					<td style="border:1px solid cadetblue;">${patient.diagnosis.disease }</td>
				</tr>
				<tr>
					<td style="border:1px solid cadetblue;">身体情况：</td>
					<td style="border:1px solid cadetblue;">${patient.diagnosis.bodyStatus }</td>
				</tr>
				
			</tbody>
		</table>
		
	</div>

</body>
</html>