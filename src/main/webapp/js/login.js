var LOGIN = {
			checkInput:function() {
				if ($("#username").val() == "") {
					
					alert("用户名不能为空");
					$("#username").focus();
					return false;
				}
				if ($("#password").val() == "") {
					
					alert("密码不能为空");
					$("#password").focus();
					return false;
				}
				return true;
			},
			doLogin:function() {
				$.post("login", $("#loginAllForm").serialize(),function(data){
					if (data.status == 200) {
						if(data.msg == "医生登录成功"){						
							alert("登录成功！");
							location.href = "mainDoctor";
						}
						if(data.msg == "护士登录成功"){
							alert("登录成功！");
							location.href = "mainNurse";
						}
						if(data.msg == "管理员登录成功"){
							alert("登录成功！");
							location.href = "main";
						}
						if(data.msg == "药品管理员登录成功"){						
							alert("登录成功！");
							location.href = "mainDoctor";
						}
					} else{
						alert("登录失败，原因是：" + data.msg);
						$("#username").select();
					}
				});
			},
			login:function() {
				if (this.checkInput()) {
					this.doLogin();
				}
			}
		
	};

$(function(){
	$("#loginsubmit").click(function(){
			LOGIN.login();
	});
});

