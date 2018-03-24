<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/all.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
<script src="${pageContext.request.contextPath }/js/list.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script> 
</head>
<body>
	<%   
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
          java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
          String str_date = formatter.format(currentTime); //将日期时间格式化 
 	%> 
	<form method="post"
		action="${pageContext.request.contextPath }/patientStatistics/monthByCondition"
		id="listform">
		<div class="panel admin-panel">
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li>
						开始时间
						<input type="text" class="Wdate" value="${beginTime }" name="beginTime"  
								 placeholder="请输入开始时间"
								 onClick="WdatePicker({lang:'zh-cn'})" />  
						
					</li>
					<li>
						结束日期
						<c:if test="${endTime == null}">
							<c:set var='endTime' value="<%=str_date%>" scope="page"></c:set>
						</c:if>
						<input type="text" class="Wdate" value="${endTime }" name="endTime"  
								 placeholder="请输入结束时间"
								 onClick="WdatePicker({lang:'zh-cn'})" />  
					</li>

					<li>
					
						<a href="javascript:void(0)" class="button border-main icon-search" name="keySearch" onclick="changesearch()"> 
							搜索
						</a>
					</li>
				</ul>

			</div>
			
	      <div  style="text-align:center">  
	         <img src="${chartURL}"  width=1100 height=400  border=0  color=gray>  
	      </div>  			
		</div>
	</form>
	<script type="text/javascript">
		//搜索
		function changesearch() {
			$('#listform').submit();
		}

	</script>
</body>
</html>