var $,tab,skyconsWeather;
layui.config({
	base : "/static/res/layui-admin/js/"
}).use(['bodyTab','form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element;
		$ = layui.jquery;
		tab = layui.bodyTab();

	//锁屏
	function lockPage(){
		layer.open({
			title : false,
			type : 1,
			content : $("#lock-box"),
			closeBtn : 0,
			shade : 0.9
		})
	}
	$(".lockcms").on("click",function(){
		window.sessionStorage.setItem("lockcms",true);
		lockPage();
	})
	// 判断是否显示锁屏
	if(window.sessionStorage.getItem("lockcms") == "true"){
		lockPage();
	}
	// 解锁
	$("#unlock").on("click",function(){
		if($(this).siblings(".admin-header-lock-input").val() == ''){
			layer.msg("请输入解锁密码！");
		}else{
			if($(this).siblings(".admin-header-lock-input").val() == "123456"){
				window.sessionStorage.setItem("lockcms",false);
				$(this).siblings(".admin-header-lock-input").val('');
				layer.closeAll("page");
			}else{
				layer.msg("密码错误，请重新输入！");
			}
		}
	});
	$(document).on('keydown', function() {
		if(event.keyCode == 13) {
			$("#unlock").click();
		}
	});

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
		shadeMobile = $('.site-mobile-shade')

	treeMobile.on('click', function(){
		$('body').addClass('site-mobile');
	});

	shadeMobile.on('click', function(){
		$('body').removeClass('site-mobile');
	});

	// 添加新窗口
	$(".layui-nav .layui-nav-item a").on("click",function(){
		addTab($(this));
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
	})

	//判断是否处于锁屏状态(如果关闭以后则未关闭浏览器之前不再显示)
	if(window.sessionStorage.getItem("lockcms") != "true" && window.sessionStorage.getItem("showNotice") != "true"){
		var message=window.sessionStorage.getItem("message");
		showNotice(message);
	}
	
	$(".showNotice").on("click",function(){
		var message=window.sessionStorage.getItem("message");
		showNotice(message);
	})
	
	//if(window.sessionStorage.getItem("menu") == null||window.sessionStorage.getItem("curmenu") == '"undefined"'){
		var params=getRequest();
		var div=params['div'];
		if(div){
			var title='';
			switch(div){
				case 'message':title='我的消息';break;
				case 'collection':title='我的收藏';div='article/mine#collection';break;
				case 'mine':div="article/mine";title='我的文章';break;
				case 'addPage':div="article/addPage";title='发表新帖';break;
				case 'set':title='基本设置';break;
				default:break;
			}
			if(!title){
				return;
			}
			var layId=new Date().getTime();
			var curMenu={'icon':'&#xe705;','title':title,'href':'/user/'+div,'layId':layId};
			var temp=window.sessionStorage.getItem("menu");
			var arr=new Array();
			if(temp){
				var menus=JSON.parse(temp);
				if(menus.length>0){					
					arr=menus;
				}
				var flag=false;
				for(var i=0;i<menus.length;i++){
					var one=menus[i];
					if(one.title==curMenu.title){
						flag=true;
						break;
					}
					
				}
				if(!flag){	
					arr.push(curMenu);				
				}
			}else{
				arr.push(curMenu);
			}
			window.sessionStorage.setItem('curmenu',JSON.stringify(curMenu));
			window.sessionStorage.setItem('menu',JSON.stringify(arr));
		}
	//}

	
	//刷新后还原打开的窗口
	if(window.sessionStorage.getItem("menu") != null){
		menu = JSON.parse(window.sessionStorage.getItem("menu"));
		curmenu = window.sessionStorage.getItem("curmenu");
		var openTitle = '';
		for(var i=0;i<menu.length;i++){
			openTitle = '';
			if(menu[i].icon.split("-")[0] == 'icon'){
				openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
			}else{
				openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
			}
			openTitle += '<cite>'+menu[i].title+'</cite>';
			openTitle += '<i class="layui-icon layui-unselect layui-tab-reload" data-id="'+menu[i].layId+'">&#xe669;</i>';
			openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
			element.tabAdd("bodyTab",{
				title : openTitle,
		        content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
		        id : menu[i].layId
			})
			//定位到刷新前的窗口
			if(curmenu != "undefined"){
				if(curmenu == '' || curmenu == "null"){  //定位到后台首页
					element.tabChange("bodyTab",'');
				}else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
					element.tabChange("bodyTab",menu[i].layId);
				}
			}else{
				element.tabChange("bodyTab",menu[menu.length-1].layId);
			}
		}
	}

})

function getRequest() {   
   var url = location.search; //获取url中"?"符后的字串   
   var theRequest = new Object();   
   if (url.indexOf("?") != -1) {   
      var str = url.substr(1);   
      strs = str.split("&");   
      for(var i = 0; i < strs.length; i ++) {   
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
      }   
   }   
   return theRequest;   
} 

//打开新窗口
function addTab(_this){
	tab.tabAdd(_this);
}

//捐赠弹窗
function donation(){
	layer.tab({
		area : ['260px', '367px'],
		tab : [{
			title : "微信",
			content : "<div style='padding:30px;overflow:hidden;background:#d2d0d0;'><img style='width:100%' src='/static/images/wechat.jpg'></div>"
		},{
			title : "支付宝",
			content : "<div style='padding:30px;overflow:hidden;background:#d2d0d0;'><img style='width:100%' src='/static/images/alipay.jpg'></div>"
		}]
	})
}

//公告层
function showNotice(myContent){
	layer.open({
        type: 1,
        title: "系统公告", //不显示标题栏
        closeBtn: false,
        area: '310px',
        shade: 0.8,
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        btn: ['我知道了'],
        moveType: 1, //拖拽模式，0或者1
        content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;">'+myContent+'</div>',
        success: function(layero){
			var btn = layero.find('.layui-layer-btn');
			btn.css('text-align', 'center');
			btn.on("click",function(){
				window.sessionStorage.setItem("showNotice","true");
			})
			if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
				btn.on("click",function(){
					layer.tips('系统公告躲在了这里', '#showNotice', {
						tips: 3
					});
				})
			}
        }
    });
}