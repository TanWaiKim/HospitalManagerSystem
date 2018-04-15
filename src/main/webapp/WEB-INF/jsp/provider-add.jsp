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
			<strong><span class="icon-pencil-square-o"></span>添加供药商信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="providerAddForm" onsubmit="return false;">
				<div class="form-group">
					<div class="label">
						<label>供药商名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="providerName"
							data-validate="required:请输入供药商名称" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>公司地址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="address" data-validate="required:请输入公司地址"/>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>联络人：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="contact" data-validate="required:请输入联络人"/>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>联络人电话：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="phone" data-validate="required:请输入联络电话"/>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>备注：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="remarks" data-validate="required:请输入备注信息"/>
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
			$.post("${pageContext.request.contextPath }/provider/add",
							$("#providerAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/provider/list";
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