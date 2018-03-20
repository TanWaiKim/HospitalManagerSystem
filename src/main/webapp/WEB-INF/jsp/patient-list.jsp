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
</head>
<body>
	<form method="post"
		action="${pageContext.request.contextPath }/patient/pageByCondition"
		id="listform">
		<input type="hidden" id="currentPage" name="currentPage"
			value="${page.currentPage }" />
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 病人列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li><a class="button border-main icon-plus-square-o"
						href="${pageContext.request.contextPath }/patient/skipToAdd">
							添加病人</a></li>
					<li>搜索：</li>
					<li>病人编号 <input type="text" name="patientId"
						value="${patientId }" /> &nbsp;&nbsp; 病人姓名 <input type="text"
						name="patientName" value="${patientName }" /> &nbsp;&nbsp; 所属科别 <select
						name="mcName" class="input" id="mcname"
						style="width: 95px; line-height: 17px; display: inline-block">
							<option value=""
								<c:if test="${mcName == ''}">selected="selected"</c:if>>选择</option>
							<option value="大内科"
								<c:if test="${mcName == '大内科'}">selected="selected"</c:if>>大内科</option>
							<option value="大外科"
								<c:if test="${mcName == '大外科'}">selected="selected"</c:if>>大外科</option>
							<option value="妇儿科室"
								<c:if test="${mcName == '妇儿科室'}">selected="selected"</c:if>>妇儿科室</option>
							<option value="五官科室"
								<c:if test="${mcName == '五官科室'}">selected="selected"</c:if>>五官科室</option>
							<option value="急诊科"
								<c:if test="${mcName == '急诊科'}">selected="selected"</c:if>>急诊科</option>
							<option value="病案、中医"
								<c:if test="${mcName == '病案、中医'}">selected="selected"</c:if>>病案、中医</option>

					</select>
					</li>

					<li><input type="text" placeholder="请输入搜索关键字" name="keywords"
						class="input" value="${keywords }"
						style="width: 180px; line-height: 17px; display: inline-block" />
						<a href="javascript:void(0)"
						class="button border-main icon-search" name="keySearch"
						onclick="changesearch()"> 搜索</a></li>
				</ul>

			</div>
			<table class="table table-hover text-center table-bordered">
				<tr>
					<th width="90" style="text-align: left; padding-left: 20px;">序号</th>
					<th width="60" >姓名</th>
					<th width="60" >性别</th>
					<th width="60" >年龄</th>
					<th width="250" >住址</th>
					<th width="90" >人群类型</th>
					<th width="30" >联系方式</th>
					<th>所属科别</th>
					<th>是否处理</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th width="180">操作</th>
				</tr>
				<c:forEach
					items="${patientList}" var="patient"  varStatus="status">
					<tr>
						<td style="text-align: left; padding-left: 20px;"><input
							type="checkbox" name="id[]" value="${patient.id}" /><span>${(page.currentPage-1)*3+status.count}</span>
						</td>

						<td>${patient.name }</td>
						<td>${patient.sex }</td>
						<td>${patient.age }</td>
						<td>${patient.address }</td>
						<td>${patient.personType }</td>
						<td>${patient.phone }</td>
						<td>${patient.mcName }</td>
						<td>${patient.isFinished }</td>
						<td><fmt:formatDate type="date" value="${patient.created }"/></td>
						<td><fmt:formatDate type="date" value="${patient.updated }"/></td>
						
						<td><div class="button-group">
								<a class="button border-main"
									href="${pageContext.request.contextPath }/patient/findById?patientId=${patient.patientId }"><span
									class="icon-edit"></span> 修改</a> <a class="button border-red"
									href="javascript:judgeDelete(${patient.id })"><span
									class="icon-trash-o"></span> 删除</a>
							</div></td>
					</tr>
				</c:forEach>
				<tr>
					<td style="text-align: left; padding: 19px 0; padding-left: 20px;border-style:none;"><input
						type="checkbox" id="checkall" /> 全选</td>
					<td colspan="5" style="text-align: left; padding-left: 20px;border-style:none;"><a
						href="javascript:deleteBatch()"
						class="button border-red icon-trash-o" style="padding: 5px 15px;" >
							删除</a></td>
					<td colspan="3" style="text-align: left; padding-left: 20px;border-style:none;">
							<input type="file" class="input"  name="uploadFile" id="uploadFile"/>
					</td>
					<td colspan="7" style="text-align: left; padding-left: 20px;border-style:none;">
					<a
						href="javascript:ajaxFileUploadForType()"
						class="button border-blue icon-sign-in" style="padding: 5px 15px;" >
							导入Excel</a>
							<a
						href="javascript:exportByBatch()"
						class="button border-blue icon-sign-out" style="padding: 5px 15px;" >
							导出Excel</a>
							</td>
				</tr>
				<tr>
					<td colspan="8" style="border-style:none;">
						<div class='page fix'>
							共 <b>${page.totalNumber}</b> 条
							<c:if test="${page.currentPage != 1}">
								<a href="javascript:changeCurrentPage('1')" class='first'>首页</a>
								<a href="javascript:changeCurrentPage('${page.currentPage-1}')"
									class='pre'>上一页</a>
							</c:if>
							当前第<span>${page.currentPage}/${page.totalPage}</span>页
							<c:if test="${page.currentPage != page.totalPage}">
								<a href="javascript:changeCurrentPage('${page.currentPage+1}')"
									class='next'>下一页</a>
								<a href="javascript:changeCurrentPage('${page.totalPage}')"
									class='last'>末页</a>
							</c:if>
							跳至&nbsp;<input id="currentPageText" type='text'
								value='${page.currentPage}' class='allInput w28' />&nbsp;页&nbsp;
							<a
								href="javascript:changeCurrentPage($('#currentPageText').val())"
								class='go'>GO</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
	 	function getSelectionsIds(){
	 		var obj=document.getElementsByName('id[]'); //选择所有name="'id[]'"的对象，返回数组 
	 		//取到对象数组后，我们来循环检测它是不是被选中 
	 		var s=''; 
	 		for(var i=0; i<obj.length; i++){ 
	 		if(obj[i].checked){
	 			s+=obj[i].value+','; //如果选中，将value添加到变量s中 
	 			} 	
	 		} 
	 		//那么现在来检测s的值就知道选中的复选框的值了 
	 		//alert(s==''?'你还没有选择任何内容！':s); 
	 		return s;
	    }
	 	
		function judgeDelete(id) {
			
			if (confirm("确定要删除id为" + id + "的记录吗？")) {
				var params = {"id":id};
				$.post("${pageContext.request.contextPath }/patient/deleteOne",params ,function(data){
        			if(data.status == 200){
        				alert('删除成功!');
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}
        			if(data.status == 500){
        				alert(data.msg);
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}
        		});
				//window.location.href = "${pageContext.request.contextPath }/patient/deleteOne?id="+ id;
			}
		}

		//搜索
		function changesearch() {
			$('#currentPage').val('1');
			$('#listform').submit();
		}

		//全选
		$("#checkall").click(function() {
			$("input[name='id[]']").each(function() {
				if (this.checked) {
					this.checked = false;
				} else {
					this.checked = true;
				}
			});
		});

		//批量删除
		function deleteBatch() {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {
				var t = confirm("您确认要删除选中的内容吗？");
				if (t == false){
					return false;
				}
				var ids = getSelectionsIds();
				var params = {"ids":ids};
				$.post("${pageContext.request.contextPath }/patient/deleteBatch",params ,function(data){
        			if(data.status == 200){
        				alert('删除病人记录成功!');
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}
        			if(data.status == 500){
        				alert(data.msg);
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}
        		});
				//$('#listform').attr("action","${pageContext.request.contextPath }/patient/deleteBatch");
				//$("#listform").submit();
			} else {
				alert("请选择您要删除的内容!");
				return false;
			}
		}
		
		//批量导出数据
		function exportByBatch(){
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {
				var t = confirm("您确认要导出选中的内容吗？");
				if (t == false){
					return false;
				}
				var ids = getSelectionsIds();
				var params = {"ids":ids};
				$.post("${pageContext.request.contextPath }/patient/export",params ,function(data){
        			if(data.status == 200){
        				alert('导出病人记录成功!');
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}if(data.status == 500){
        				alert(data.msg);
        				location.href = "${pageContext.request.contextPath }/patient/list";
        			}
        		});
				//$('#listform').attr("action","${pageContext.request.contextPath }/patient/exportByBatch");
				//$("#listform").submit();
			} else {
				alert("请选择您要导出内容的ID!");
				return false;
			}
		}
		
		function ajaxFileUploadForType(){
		    if($('input[type="file"]').val()!=""){
		         var extend=$('input[type="file"]').val().substr($('input[type="file"]').val().lastIndexOf(".")+1);
		         if("xls|xlsx".indexOf(extend+"|")==-1){
		             flagPic=false;
		             alert("选择的文件必须是EXCEL文件,请确认！");
		         }else{
		             $.ajaxFileUpload
		                (
		                    {
		                        url: '${pageContext.request.contextPath }/patient/import', //用于文件上传的服务器端请求地址
		                        secureuri: false, //是否需要安全协议，一般设置为false
		                        fileElementId: 'uploadFile', //文件上传域的ID
		                        dataType: 'json', //返回值类型 一般设置为json
		                        success: function (data)  //服务器成功响应处理函数
		                        {   
		                            if(data.status == "200"){
		                                alert('导入成功！');
		                                location.href = "${pageContext.request.contextPath }/patient/list";
		                            }else if(data.status == "500"){
		                                alert('导入失败！');
		                            }
		                        },
		                        error: function (data, status, e)//服务器响应失败处理函数
		                        {
		                           alert('导入失败！');
		                        }
		                    }
		                )
		         }
		    }else{
		         alert("请选EXCEL文件！");
		    }
		}
	</script>
</body>
</html>