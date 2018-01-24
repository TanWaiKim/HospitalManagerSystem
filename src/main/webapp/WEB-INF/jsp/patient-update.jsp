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
			<strong><span class="icon-pencil-square-o"></span>修改病人信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="patientUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${patient.id }" />
				<input type="hidden" id="created" name="created" value="${patient.created }" />
				
				<div class="form-group">
					<div class="label">
						<label>病人ID：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.patientId }"
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
							name="age" data-validate="required:请输入病人年龄" />
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
						<input type="text" class="input w50" value="${patient.phone }" maxlength="11"
							name="phone" data-validate="required:请输入联系方式" />
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
							<option value=""
								<c:if test="${patient.mcName == ''}">selected="selected"</c:if>>选择</option>
							<option value="大内科"
								<c:if test="${patient.mcName == '大内科'}">selected="selected"</c:if>>大内科</option>
							<option value="大外科"
								<c:if test="${patient.mcName == '大外科'}">selected="selected"</c:if>>大外科</option>
							<option value="妇儿科室"
								<c:if test="${patient.mcName == '妇儿科室'}">selected="selected"</c:if>>妇儿科室</option>
							<option value="五官科室"
								<c:if test="${patient.mcName == '五官科室'}">selected="selected"</c:if>>五官科室</option>
							<option value="急诊科"
								<c:if test="${patient.mcName == '急诊科'}">selected="selected"</c:if>>急诊科</option>
							<option value="病案、中医"
								<c:if test="${patient.mcName == '病案、中医'}">selected="selected"</c:if>>病案、中医</option>

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
							<c:if test="${patient.isFinished == ''}">selected="selected"</c:if>>选择</option>
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
						<input type="text" class="input w50"
							value="${patient.loginName }" name="loginName"
							data-validate="required:请输入病人登录账号" />
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
			$.post("${pageContext.request.contextPath }/patient/update",
							$("#patientUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/patient/list";
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