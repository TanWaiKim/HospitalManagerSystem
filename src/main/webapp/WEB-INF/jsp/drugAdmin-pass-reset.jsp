<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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
  <div class="panel-head"><strong><span class="icon-key"></span> 修改密码</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" onsubmit="return false;" id="passlist">
      <input type="hidden" id="id" name="id" value="${currentUser.id }" />
      <div class="form-group">
        <div class="label">
          <label for="sitename">用户帐号：</label>
        </div>
        <div class="field">
          <label style="line-height:33px;">
           ${currentUser.username }
          </label>
        </div>
      </div>      
      <div class="form-group">
        <div class="label">
          <label for="sitename">原始密码：</label>
        </div>
        <div class="field">
          <input type="password" class="input w50" id="oldPassword" name="oldPassword" size="50" placeholder="请输入原始密码" data-validate="required:请输入原始密码" />       
        </div>
      </div>      
      <div class="form-group">
        <div class="label">
          <label for="sitename">新密码：</label>
        </div>
        <div class="field">
          <input type="password" class="input w50" name="newPassword" size="50" placeholder="请输入新密码" data-validate="required:请输入新密码,length#>=6:新密码不能小于6位" />         
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label for="sitename">确认密码：</label>
        </div>
        <div class="field">
          <input type="password" class="input w50" name="rePassword" size="50" placeholder="请再次输入新密码" data-validate="required:请再次输入新密码,repeat#newPassword:两次输入的密码不一致" />          
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button class="button bg-main icon-check-square-o" onclick="updatePass()" > 提交</button>   
        </div>
      </div>      
    </form>
  </div>
</div>
<script type="text/javascript">
		function updatePass() {
			//ajax的post方式提交表单
			//$("#passlist").serialize()将表单序列号为key-value形式的字符串
			$.post("${pageContext.request.contextPath }/drugAdmin/updatePassword",
				 $("#passlist").serialize(),
				 function(data) {
					if (data.status == 200) {
						var d = dialog({
							okValue: '确定',
							title: '温馨提示',
							content: '恭喜您，密码修改成功!',

							width: 200,
							height: 50,
							ok: function () {
								location.href = "${pageContext.request.contextPath }/drug-index.jsp";
							}
						});
						d.showModal();
					} else if (data.status == 500) {
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
</body></html>