var LOGIN = {
			checkInput:function() {
				if ($("#username").val() == "") {
					var d = dialog({
						okValue: '确定',
						title: '温馨提示',
						content: '用户名不能为空！',

						width: 200,
						height: 50,
						ok: function () {
							
						}
					});
					d.showModal();
					$("#username").focus();	
					return false;
				}
				if ($("#password").val() == "") {
					var d = dialog({
						okValue: '确定',
						title: '温馨提示',
						content: '密码不能为空！',

						width: 200,
						height: 50,
						ok: function () {
							
						}
					});
					d.showModal();
					$("#password").focus();
					return false;
				}
				return true;
			},
			doLogin:function() {
				$.post("login", $("#loginAllForm").serialize(),function(data){
					if (data.status == 200) {
						if(data.msg == "医生登录成功"){	
							location.href = "mainDoctor";
						}
						if(data.msg == "管理员登录成功"){
							location.href = "mainAdmin";
						}
						if(data.msg == "药品员登录成功"){						
							location.href = "mainDrug";
						}
						
					}if(data.status == 500){ 
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
					if(data.status == 505){
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

