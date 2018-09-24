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

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加药品员信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="drugAdminAddForm" onsubmit="return false;">
				<div class="form-group">
					<div class="label">
						<label>用户名：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入用户名" class="input w50" value="" name="username"
							data-validate="required:请输入用户名" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>密码：</label>
					</div>
					<div class="field">
						<input type="password" placeholder="请输入密码"  class="input w50" value="" name="password"
							data-validate="required:请输入密码" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>性别：</label>
					</div>

					<div class="field">
						<input type="radio" name="sex" checked="checked" value="男">男
						<input type="radio" name="sex" value="女">女
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>邮箱：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入用邮箱号码"  class="input w50" value="" name="email" data-validate="required:请输入邮箱号码"/>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>手机号码：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入手机号码"  class="input w50" value="" name="phone" data-validate="required:请输入手机号码（11位）"/>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>住址：</label>
					</div>
					<div class="field">
						<input type="text" placeholder="请输入住址"  class="input w50" value="" name="address" data-validate="required:请输入住址"/>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>简介：</label>
					</div>
					<div class="field">
						<textarea rows="3" placeholder="请输入简介（不超过30字）"  class="input" name="intro" style="height:100px;width: 500px;" data-validate="required:请输入简介"></textarea>
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
			$.post("${pageContext.request.contextPath }/drugAdmin/add",
							$("#drugAdminAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/drugAdmin/list";
										}
									});
									d.showModal();
								} else if (data.status == 500) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '很抱歉，添加失败!',

										width: 200,
										height: 50,
										ok: function () {
											
										}
									});
									d.showModal();
								} else if(data.status == 505){
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