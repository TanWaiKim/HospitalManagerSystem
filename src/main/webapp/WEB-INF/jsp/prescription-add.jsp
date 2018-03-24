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
 <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>添加处方信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x prescriptionAddForm"
				id="prescriptionAddForm" onsubmit="return false;">
				<div class="form-group">
					<div class="label">
						<label>病人编号：</label>
					</div>
					<c:choose>
						<c:when test="${pId != null}">
							<div class="field">
								<select name="patientId" class="input"
									style="width: 155px; line-height: 17px; display: inline-block">
									<option value="${pId }">${pId }</option>

								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="field">
								<select name="patientId" class="input"
									style="width: 155px; line-height: 17px; display: inline-block">
									<option value="">选择</option>
									<c:forEach items="${patientIds}" var="patientId">
										<option value="${patientId }">${patientId }</option>
									</c:forEach>
								</select>
							</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="form-group" name="drug-group-div">
					<div class="label">
						<label>处方数据：</label>
					</div>
					<button class="button border-sub icon-plus-square-o addGroup">添加药品数据</button>

					<ul>
						<div name="drug-name-div"
							style="margin-left: 87px; margin-top: 10px;">
							药品名：<input type="text" name="drug-name" id="drug-name" class="drug-name"
								style="width: 400px; height: 35px;">
						</div>
						<div name="drug-num-div"
							style="margin-left: 100px; margin-top: 10px;">
							数量：<input type="text" name="drug-num"
								style="width: 400px; height: 35px;"> <select name="unit"
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
								style="width: 400px; height: 35px;">
						</div>
						<a href="javascript:void()"
							class="button border-red icon-trash-o delParam"
							style="padding: 5px 15px; margin-left: 600px; visibility: hidden;">
							删除药品数据</a>
					</ul>
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
		           	
		$("#drug-name").autocomplete({
						source:"<c:url value="/prescription/auto"/>",  
	       		 		minLength:1
	           			});
		
		$(".addGroup").click(function() {
				var temple = $(".prescriptionAddForm ul").eq(0).clone();
				temple.find("input").val("");
				temple.find("input[name=drug-name]").autocomplete({
					source:"<c:url value="/prescription/auto"/>",  
       		 		minLength:1
       			});
				$(this).parent().append(temple);
				
				
				temple.find(".delParam").css("visibility", "visible");
				temple.find(".delParam").click(function() {
					$(this).parent().remove();
				});
		});
			         		
		
							
		function addForm() {
			var drugnames = document.getElementsByName("drug-name");
			var drugnums = document.getElementsByName("drug-num");
			var units = document.getElementsByName("unit");
			var drugusages = document.getElementsByName("drug-usage");

			var params = [];
			for (var i = 0; i < drugnames.length; i++) {
				for (var j = 0; j < drugnums.length; j++) {
					for (var k = 0; k < drugusages.length; k++) {
						if (i == j) {
							if (i == k) {
								params.push({
									"drugName" : drugnames[i].value,
									"drugNum" : drugnums[i].value
											+ units[i].value,
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
							"${pageContext.request.contextPath }/prescription/add/"
									+ $("#prescriptionAddForm [name=patientId]")
											.val(),
							{
								"paramData" : JSON.stringify(params)
							},
							function(data) {
								if (data.status == 200) {
									alert("添加成功!");
									location.href = "${pageContext.request.contextPath }/prescription/list";
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