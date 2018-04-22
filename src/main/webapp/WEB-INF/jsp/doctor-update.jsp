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
<script type="text/javascript">
$(function()  {
	$("#age").blur(function() {
		var reg = /^[0-9]+.?[0-9]*$/;
		var a = document.getElementById("age");
		if (!reg.test(a.value)) {
			alert("年龄必须为0-150之间的整数!");
		}
		else if(a.value < 0 || a.value > 150){
			alert("年龄必须为0-150之间的整数!");
		}
	})
});
$(function()  {
	$("#phone").blur(function() {
		var reg = /^[0-9]+.?[0-9]*$/;
		var phone = document.getElementById("phone");
		if (!reg.test(phone.value)) {
			alert("请输入11位数字!");
		}
		else if(phone.value.toString().length != 11 ){
			alert("手机号码格式错误！(11位数字)");
		}
	})
});
</script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改医生信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="doctorUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${doctor.id }" />
				<input type="hidden" id="created" name="created" value="${doctor.created }" />
				<div class="form-group">
					<div class="label">
						<label>医生ID：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${doctor.doctorId }" readonly="readonly"
							name="doctorId" data-validate="required:请输入医生编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>医生名字：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${doctor.name }"
							name="name" data-validate="required:请输入医生名字" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>医生性别：</label>
					</div>

					<div class="xingbie">
						<c:if test="${doctor.sex == '男'}">
							<input type="radio" name="sex" checked="checked" value="男">男
							<input type="radio" name="sex" value="女">女
						</c:if>
						<c:if test="${doctor.sex  == '女'}">
							<input type="radio" name="sex" value="男">男
							<input type="radio" name="sex" checked="checked" value="女">女
						</c:if>
					</div>


				</div>
				<div class="form-group">
					<div class="label">
						<label>医生年龄：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${doctor.age }"
							id="age" name="age" data-validate="required:请输入医生年龄" />
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>联系方式：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${doctor.phone }"
							id="phone" maxlength="11" name="phone"
							data-validate="required:请输入联系方式" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>所属科别：</label>
					</div>
					<div class="field">
						<select name="mcName" class="input" id="mcname"
							style="width: 95px; line-height: 17px; display: inline-block">
							<option value="">未选择</option>
							
							<c:forEach
								items="${medicalcoursesNameList}" var="medicalcoursesName" >
									<option value="${medicalcoursesName}"
									<c:if test="${doctor.mcName == medicalcoursesName}">selected="selected"</c:if>>${medicalcoursesName}</option>
							</c:forEach>

						</select>
					</div>
				</div>
			

				<div class="form-group">
					<div class="label">
						<label>登录账号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${doctor.loginName }"
							name="loginName" data-validate="required:请输入医生登录账号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>登录密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50"
							value="${doctor.loginPassword }" name="loginPassword"
							data-validate="required:请输入医生登录密码" />
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
			//$("#doctorUpdateForm").serialize()将表单序列号为key-value形式的字符串
			$
					.post(
							"${pageContext.request.contextPath }/doctor/update",
							$("#doctorUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/doctor/list";
								} else if (data.status == 500) {
									alert(data.msg);
								} else if (data.status == 505) {
									alert(data.msg);
								}
							});
		}
	</script>
</body>
</html>