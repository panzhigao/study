Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function minusDays(date,days){
    date.setDate(date.getDate()-5);//设置天数 -5 天
    return date.format("yyyy-MM-dd");
}

//格式化时间
function dateFormat(fmt,date){ 
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 

//fromTime 为时间戳,format为时间格式化，默认为yyyy-MM-dd hh:mm:ss
function tranTime(fromTime,format){
	var nowTime=Math.round(new Date()/1000);    
    var tempTime = nowTime - Math.round(new Date(fromTime)/1000);    
    var str="";	
    if (tempTime < 60){
        str = '刚刚';
    }else if(tempTime <60*60)
	{
        var min = Math.floor(tempTime/60);
        str = min+'分钟前';
    }else if(tempTime < 60 * 60 * 24)
	{
        var h = Math.floor(tempTime/(60*60));
        str = h+'小时前 ';
    }
	else if(tempTime < 60 * 60 * 24 * 3)
	{
        var d = Math.floor(tempTime/(60*60*24));
        var time=dateFormat('hh:mm:ss',new Date(fromTime));
        if(d==1){
        	str = '昨天 '+time;        	
        }else{        	
        	str = '前天 '+time;
        }
    }else{
    	if(!format){
    		format='yyyy-MM-dd hh:mm:ss';
    	}
        str = dateFormat(format,new Date(fromTime));
    }
    return str;
}

var interval;

/**
 * 获取未读消息
 */
function websocketConnect(){
	$.post('/user/message/count',{},function(res){ $("#messageCount").html(res.data);});
	
	var ws = new WebSocket("ws://www.panzhigao.vip/myHandler")
	
	ws.onopen = function () {
	   console.log("websocket连接成功");
	   ws.send("{}");
	   interval=setInterval(function(){
			ws.send("{}");
	   },5000);
	}
	ws.onclose = function () {
	   console.log("websocket连接断开");
	   clearInterval(interval);
	}

	ws.onmessage = function (msg) {	
	  console.log("服务器传来消息："+msg.data);
	  var message=JSON.parse(msg.data);
	  //系统公告
	  if(message&&message.messageType==0){
		  //showMessage(message);
		  showNotice(message);
		  window.sessionStorage.setItem('message',JSON.stringify(message));
	  }else{//个人消息树增加
		  var count=$("#messageCount").html()|0;
	      $("#messageCount").html(++count);
	  }
	}
}

function showNotice(message){
	var obj;
	if(!message){
		return;
	}
	if(!message||typeof(message)=='string'){
		obj=new Object();
		obj['contentName']='消息';
		obj['commentContent']='暂无消息';
		message=obj;
	}
	//示范一个公告层
	layer.open({
	  type: 1
	  ,title: false //不显示标题栏
	  ,closeBtn: false
	  ,area: '400px;'
	  ,shade: 0.8
	  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	  ,resize: false
	  ,btn: ['朕知道了']
	  ,btnAlign: 'c'
	  ,moveType: 1 //拖拽模式，0或者1
	  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;"><h3>'+message.contentName+'</h3><br>'+message.commentContent+'</div>'
	});
}