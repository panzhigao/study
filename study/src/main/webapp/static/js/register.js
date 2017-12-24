var registerEvent = {
	// 注册
	regist : function() {
		var user = $("#registForm").serialize();
		$.ajax({
			type:'post',
			url:'/register',
			dataType:'json',
			data:user,
			success:function(data){
				
			}
		});
	}
}