<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
			<strong><span class="icon-pencil-square-o"></span>添加诊断信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="diagnosisAddForm" onsubmit="return false;">
				<div class="form-group">
					<div class="label">
						<label>病人名字：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="name"
							data-validate="required:请输入病人名字" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人性别：</label>
					</div>

					<div class="xingbie">
						<input type="radio" name="sex" checked="checked" value="男">男
						<input type="radio" name="sex" value="女">女
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人年龄：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="age"  
							data-validate="required:请输入病人年龄" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人住址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="address" data-validate="required:请输入病人住址"/>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>人群类型：</label>
					</div>
					<div class="person_type">
						<input type="radio" name="personType" checked="checked" value="正常">正常
						<input type="radio" name="personType" value="残障人士">残障人士
						<input type="radio" name="personType" value="孤寡老人">孤寡老人
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>联系方式：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" maxlength="11"  value="" name="phone" data-validate="required:请输入联系方式(11位阿拉伯数字)"/>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所属科别：</label>
					</div>
					<div class="field">
						<select name="mcName" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block">
							<option value="">选择</option>
							<option value="大内科">大内科</option>
							<option value="大外科">大外科</option>
							<option value="妇儿科室">妇儿科室</option>
							<option value="五官科室">五官科室</option>
							<option value="急诊科">急诊科</option>
							<option value="病案、中医">病案、中医</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>是否处理：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${patient.isFinished }"
							name="isFinished" data-validate="required:请输入0(未处理)或1(已处理)" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人登录账号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="loginName"
							data-validate="required:请输入病人登录账号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>病人登录密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="loginPassword"
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
							id="addSubmit" onclick="addForm()">提交</button>
					</div>
		</div>
		</form>
	</div>
	</div>
	<script type="text/javascript">
		function addForm() {
			//ajax的post方式提交表单
			//$("#patientAddForm").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/patient/add",
							$("#diagnosisAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("添加成功!");
									location.href = "${pageContext.request.contextPath }/patient/list";
								} else if (data.status == 500) {
									alert("添加失败!");
								} else if(data.status == 505){
									alert(data.msg);
								}
							});
		}
	</script>
</body>
</html>