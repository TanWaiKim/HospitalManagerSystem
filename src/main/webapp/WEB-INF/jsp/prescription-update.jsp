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
			<strong><span class="icon-pencil-square-o"></span>修改处方信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x prescriptionAddForm"
				id="prescriptionUpdateForm" onsubmit="return false;">
				<input type="hidden" id="id" name="id" value="${prescription.id }" />
				<input type="hidden" id="prescriptionId" name="prescriptionId"
					value="${prescription.prescriptionId }" /> <input type="hidden"
					id="patientId" name="patientId" value="${prescription.patientId }" />
				<input type="hidden" id="doctorId" name="doctorId"
					value="${prescription.doctorId }" />
				<div class="form-group">
					<div class="label">
						<label>病人名字：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50"
							value="${prescription.patient.name } " readonly="readonly"
							name="name" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group" name="drug-group-div">
					<div class="label">
						<label>处方数据：</label>
					</div>
					<button class="button border-sub icon-plus-square-o addGroup">添加药品数据</button>
					<c:forEach items="${drugDataList}" var="drugData">
						<ul name="drug-ul">
							<div name="drug-name-div"
								style="margin-left: 87px; margin-top: 10px;">
								药品名：<input type="text" name="drug-name"
									style="width: 400px; height: 30px;"
									value="${drugData.drugName }">
							</div>
							<div name="drug-num-div"
								style="margin-left: 100px; margin-top: 10px;">
								数量：<input type="text" name="drug-num"
									style="width: 400px; height: 30px;"
									value="${drugData.drugNum }"> <select name="unit"
									class="input" id="unit"
									style="width: 38px; line-height: 17px; display: inline-block">
									<option value="瓶">瓶</option>
									<option value="盒">盒</option>
									<option value="板">板</option>

									<option value="袋">袋</option>
									<option value="支">支</option>
									<option value="片">片</option>
									<option value="粒">粒</option>

								</select>
							</div>
							<div name="drug-usage-div"
								style="margin-left: 100px; margin-top: 10px;">
								用量：<input type="text" name="drug-usage"
									style="width: 400px; height: 30px;"
									value="${drugData.drugUsage }">
							</div>
							<a href="javascript:void()"
								class="button border-red icon-trash-o delParam"
								style="padding: 5px 15px; margin-left: 600px;"> 删除药品数据</a>
						</ul>
					</c:forEach>
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
		$(function() {
			$(".addGroup").click(function() {
				var temple = $(".prescriptionAddForm ul").eq(0).clone();
				temple.find("input").val("");
				$(this).parent().append(temple);
				temple.find(".delParam").css("visibility", "visible");
				temple.find(".delParam").click(function() {
					$(this).parent().remove();
				});
			});

			var druguls = document.getElementsByName("drug-ul");
			if (druguls.length == 1) {
				$(".prescriptionAddForm ul").find(".delParam").remove();
			}

			$(".prescriptionAddForm ul").find(".delParam").click(function() {
				if (druguls.length > 1) {
					$(this).parent().remove();
				}
			});

		});

		function addForm() {
			var drugnames = document.getElementsByName("drug-name");
			var drugnums = document.getElementsByName("drug-num");
			var drugusages = document.getElementsByName("drug-usage");

			var params = [];
			for (var i = 0; i < drugnames.length; i++) {
				for (var j = 0; j < drugnums.length; j++) {
					for (var k = 0; k < drugusages.length; k++) {
						if (i == j) {
							if (i == k) {
								params.push({
									"drugName" : drugnames[i].value,
									"drugNum" : drugnums[i].value,
									"drugUsage" : drugusages[i].value
								});
							}

						}

					}
				}
			}

			//ajax的post方式提交表单
			$
					.post(
							"${pageContext.request.contextPath }/prescription/update/"
									+ $("#prescriptionUpdateForm [name=id]")
											.val()
									+ "/"
									+ $(
											"#prescriptionUpdateForm [name=prescriptionId]")
											.val()
									+ "/"
									+ $(
											"#prescriptionUpdateForm [name=patientId]")
											.val()
									+ "/"
									+ $(
											"#prescriptionUpdateForm [name=doctorId]")
											.val(),
							{
								"paramData" : JSON.stringify(params)
							},
							function(data) {
								if (data.status == 200) {
									alert("修改成功!");
									location.href = "${pageContext.request.contextPath }/prescription/list";
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