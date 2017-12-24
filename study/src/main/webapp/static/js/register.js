var registerEvent = {
	// 注册
	regist : function() {
		$('#registForm').bootstrapValidator({
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
						}
					}
				},
				"nickname" : {
					message : '昵称不能为空',
					validators : {
						notEmpty : {
							message : '请输入昵称'
						},
						stringLength : {
							min : 2,
							max : 10,
							message : '用户名长度必须在2-10之间'
						},
						regexp : {
							regexp : /^[\u4e00-\u9fff\w]{2,10}$/,
							message : '昵称只能包含中文,字母，数字，点和下划线'
						}
					}
				},
				// "email" : {
				// validators : {
				// notEmpty : {
				// message : '请输入邮箱地址'
				// },
				// emailAddress : {
				// message : '输入的email地址格式不正确'
				// }
				// }
				// },
				"password" : {
					validators : {
						notEmpty : {
							message : '请输入密码'
						},
						identical : {
							field : 'confirmPassword',
							message : '密码输入不一致'
						},
						different : {
							field : 'username',
							message : '密码不能与用户名一样'
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
				},
				// 确认密码
				confirmPassword : {
					validators : {
						notEmpty : {
							message : '确认密码不能为空'
						},
						identical : {
							field : 'password',
							message : '密码输入不一致'
						},
						different : {
							field : 'username',
							message : '密码不能与用户名一样'
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
	registerEvent.regist();
	$('#register').click(function() {
		var form = $("#registForm");
		var flag = form.data("bootstrapValidator").isValid();
		if (!flag) {
			return;
		}
		var user = $("#registForm").serialize();
		$.ajax({
			type : 'post',
			url : 'doRegister',
			dataType : 'json',
			data : user,
			success : function(data) {
				if (data.code == "200") {
					alert(data.msg);
					location.href = "index";
				} else {
					alert(data.msg);
				}
			}
		});
	});
});