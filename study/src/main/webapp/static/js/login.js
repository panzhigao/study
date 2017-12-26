var loginEvent = {
	// 登陆
	login : function() {
		$('#loginForm').bootstrapValidator({
			message : '表单不能为空',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				"username" : {
					message : '用户名不能为空',
					validators : {
						notEmpty : {
							message : '请输入用户名'
						},
						stringLength : {
							min : 5,
							max : 15,
							message : '用户名长度必须在5-15之间'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9_\.]+$/,
							message : '用户名只能包含字母，数字，点和下划线'
						},
					}
				},
				"password" : {
					validators : {
						notEmpty : {
							message : '请输入密码'
						},
						stringLength : {
							min : 6,
							max : 16,
							message : '密码长度必须在6-16之间'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9_\.]+$/,
							message : '用户名只能包含字母，数字，点和下划线'
						}
					}
				}
			}
		});
	}
};
$(function() {
	loginEvent.login();
	$('#doLogin').click(function() {
		var form = $("#loginForm");
		var flag = form.data("bootstrapValidator").isValid();
		if (!flag) {
			return;
		}
		var user = $("#loginForm").serialize();
		$.ajax({
			type : 'post',
			url : 'doLogin',
			dataType : 'json',
			data : user,
			success : function(data) {
				if (data.code == "200") {
					alert(data.msg);
					location.href = "user/index";
				} else {
					alert(data.msg);
				}
			}
		});
	});
});