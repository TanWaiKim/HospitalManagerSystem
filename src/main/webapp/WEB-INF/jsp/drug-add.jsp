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
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script>  

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加医药信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="drugAddForm"
				onsubmit="return false;">
		
				<div class="form-group">
					<div class="label">
						<label>医药种类：</label>
					</div>
					<div class="field">
						<select name="drugtypeId" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入医药种类" >
							<option value="">选择医药种类</option>
							<c:forEach
							items="${drugtypeList}" var="drugtype" >
							<option value="${drugtype.id }">${drugtype.drugtypeName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="drugName" data-validate="required:请输入病医药名称" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>功能主治：</label>
					</div>
					
					<div class="field">
						
						<textarea rows="2" class="input" name="purpose"  data-validate="required:请输入功能主治"></textarea>
						<div class="tips"></div>
					</div>
					
				</div>

				<div class="form-group">
					<div class="label">
						<label>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</label>
					</div>
					<div class="field">
						<select name="unit" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入单位">
							<option value="">选择计量单位</option>
							<option value="盒">盒</option>
							<option value="板">板</option>
							<option value="袋">袋</option>
							<option value="瓶">瓶</option>
							<option value="支">支</option>
							<option value="片">片</option>
							<option value="粒">粒</option>
							<option value="丸">丸</option>
							<option value="剂">剂</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="label">
						<label>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="spec" data-validate="required:请输入规格" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>用法用量：</label>
					</div>

					<div class="field">
						
						<textarea rows="2" class="input" name="howuse"  data-validate="required:请输入用法用量"></textarea>
						<div class="tips"></div>
					</div>
				</div>	

				<div class="form-group">
					<div class="label">
						<label>批准文号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="drugNo" data-validate="required:请输入批准文号" />
						<div class="tips"></div>
					</div>
				</div>	
				
				<div class="form-group">
					<div class="label">
						<label>不良反应：</label>
					</div>

					<div class="field">
						
						<textarea rows="2" class="input" name="uneffect"  data-validate="required:请输入不良反应"></textarea>
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
		function addForm() {
			//ajax的post方式提交表单
			//$("#patientAddForm").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/drug/add",
							$("#drugAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									var d = dialog({
										okValue: '确定',
										title: '温馨提示',
										content: '恭喜您，添加成功!',

										width: 200,
										height: 50,
										ok: function () {
											location.href = "${pageContext.request.contextPath }/drug/list";
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
								} else if (data.status == 505) {
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