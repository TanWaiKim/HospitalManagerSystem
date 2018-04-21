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
			<strong><span class="icon-pencil-square-o"></span>修改病人信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="patientUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${patient.id }" />

				<div class="form-group">
					<div class="label">
						<label>病人ID：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.patientId }" readonly="readonly"
							name="patientId" data-validate="required:请输入病人编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人名字：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.name }"
							name="name" data-validate="required:请输入病人名字" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人性别：</label>
					</div>

					<div class="xingbie">
						<c:if test="${patient.sex == '男'}">
							<input type="radio" name="sex" checked="checked" value="男">男
							<input type="radio" name="sex" value="女">女
						</c:if>
						<c:if test="${patient.sex  == '女'}">
							<input type="radio" name="sex" value="男">男
							<input type="radio" name="sex" checked="checked" value="女">女
						</c:if>
					</div>


				</div>
				<div class="form-group">
					<div class="label">
						<label>病人年龄：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.age }"
							id="age" name="age" data-validate="required:请输入病人年龄" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人住址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.address }"
							name="address" data-validate="required:请输入病人住址" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>人群类型：</label>
					</div>
					<div class="person_type">
						<c:if test="${patient.personType == '正常'}">
							<input type="radio" name="personType" checked="checked"
								value="正常">正常
						<input type="radio" name="personType" value="残障人士">残障人士 
						<input type="radio" name="personType" value="孤寡老人">孤寡老人
						</c:if>
						<c:if test="${patient.personType == '残障人士'}">
							<input type="radio" name="personType" value="正常">正常
						<input type="radio" name="personType" checked="checked"
								value="残障人士">残障人士 
						<input type="radio" name="personType" value="孤寡老人">孤寡老人
						</c:if>
						<c:if test="${patient.personType == '孤寡老人'}">
							<input type="radio" name="personType" value="正常">正常
						<input type="radio" name="personType" value="残障人士">残障人士 
						<input type="radio" name="personType" checked="checked"
								value="孤寡老人">孤寡老人
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>联系方式：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.phone }"
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
									<c:if test="${patient.mcName == medicalcoursesName}">selected="selected"</c:if>>${medicalcoursesName}</option>
							</c:forEach>

						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>是否处理：</label>
					</div>
					<div class="field">
						<select name="isFinished" class="input"
							style="width: 85px; line-height: 17px; display: inline-block">
							<option value="选择"
							<c:if test="${patient.isFinished == ''}">>selected="selected"</c:if>>选择</option>
							<option value="是"
								<c:if test="${patient.isFinished == '是'}">selected="selected"</c:if>>是</option>
							<option value="否"
								<c:if test="${patient.isFinished == '否'}">selected="selected"</c:if>>否</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>病人登录账号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.loginName }"
							name="loginName" data-validate="required:请输入病人登录账号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人登录密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50"
							value="${patient.loginPassword }" name="loginPassword"
							data-validate="required:请输入病人登录密码" />
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
			$
					.post(
							"${pageContext.request.contextPath }/patient/update",
							$("#patientUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/patient/list";
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