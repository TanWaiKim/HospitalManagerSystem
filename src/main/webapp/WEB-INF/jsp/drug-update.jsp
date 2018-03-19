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
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改医药信息</strong>
		</div>
		<div class="body-content">
			<form method="post" id="drugUpdateForm" class="form-x"
				onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${drug.id }" />
				<input type="hidden" id="createTime" name="createTime" value="${drug.createTime }" />
				
				<div class="form-group">
					<div class="label">
						<label>医药种类：</label>
					</div>
					<div class="field">
						<select name="drugtypeId" class="input"
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入医药种类" >
							<option value="${drug.drugtypeId }">${drug.drugtype.drugtypeName }</option>
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
						<input type="text" class="input w50" value="${drug.drugName }"
							name="drugName" data-validate="required:请输入病医药名称" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>功能主治：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drug.purpose }"
							name="purpose" data-validate="required:请输入功能主治" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>单位：</label>
					</div>
					<div class="field">
						<select name="unit" class="input" 
							style="width: 95px; line-height: 17px; display: inline-block" data-validate="required:请输入单位">
							<option value="${drug.unit }">${drug.unit }</option>
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
						<label>规格：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drug.spec }"
							name="spec" data-validate="required:请输入规格" />
						<div class="tips"></div>
					</div>
				</div>				
			
				<div class="form-group">
					<div class="label">
						<label>用法用量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drug.howuse }"
							name="howuse" data-validate="required:请输入用法用量" />
						<div class="tips"></div>
					</div>
				</div>		
				
				<div class="form-group">
					<div class="label">
						<label>批准文号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drug.drugNo }"
							name="drugNo" data-validate="required:请输入批准文号" />
						<div class="tips"></div>
					</div>
				</div>	

				<div class="form-group">
					<div class="label">
						<label>不良反应：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${drug.uneffect }"
							name="uneffect" data-validate="required:请输入不良反应" />
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
			$.post("${pageContext.request.contextPath }/drug/update",
							$("#drugUpdateForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/drug/list";
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