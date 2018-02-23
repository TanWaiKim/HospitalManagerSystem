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

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加药品项目</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" id="purchaseItemAddForm"
				onsubmit="return false;">
				<input type="hidden" id="purchaseNo" name="purchaseNo" value="${purchaseDto.purchaseNo }" />
				<input type="hidden" id="providerId" name="providerId" value="${purchaseDto.providerId }" />
				<input type="hidden" id="warehouseNo" name="warehouseNo" value="${purchaseDto.warehouseNo }" />
				<input type="hidden" id="totalQuantity" name="totalQuantity" value="${purchaseDto.totalQuantity }" />
				<input type="hidden" id="totalPrice" name="totalPrice" value="${purchaseDto.totalPrice }" />
				<input type="hidden" id="operator" name="operator" value="${purchaseDto.operator }" />
				<input type="hidden" id="remarks" name="remarks" value="${purchaseDto.remarks }" />

				<div class="form-group">
					<div class="label">
						<label>医药名称：</label>
					</div>
					<div class="field">
						<select name="drugId" class="input" 
							style="width: 155px; line-height: 17px; display: inline-block" data-validate="required:请输入医药名称" >
							<option value="">选择</option>
							<c:forEach
							items="${drugList}" var="drug" >
							<option value="${drug.id }">${drug.drugName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>进药价格：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="purchasePrice" data-validate="required:请输入进药价格" />元
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>销药价格：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="salePrice" data-validate="required:请输入销药价格" />元
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>生产日期：</label>
					</div>
					<div class="field">
						<input type="text" class="Wdate" value="" name="produceTime"  
						 data-validate="required:请输入生产日期"
						 onClick="WdatePicker({lang:'zh-cn'})" />  
						 <div class="tips"></div>
					</div>
					
				</div>						
	
				<div class="form-group">
					<div class="label">
						<label>有效期至：</label>
					</div>
					<div class="field">
						<input type="text" class="Wdate" value="" name="validTime" 
						 data-validate="required:请输入有效期至"
						 onClick="WdatePicker({lang:'zh-cn'})" />  
						 <div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>产品批号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="batchNo" data-validate="required:请输入产品批号" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>数量：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value=""
							name="quantity" data-validate="required:请输入数量" />
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
			$.post("${pageContext.request.contextPath }/purchaseItem/add",
							$("#purchaseItemAddForm").serialize(),
							function(data) {
								if (data.status == 200) {
									alert("添加成功!");
									location.href = "${pageContext.request.contextPath }/purchase/findByPurchaseNo?purchaseNo=${purchaseDto.purchaseNo }";
								} else if (data.status == 500) {
									alert("添加失败!");
								} else if (data.status == 505) {
									alert(data.msg);
								}
							});
		}
	</script>
</body>
</html>