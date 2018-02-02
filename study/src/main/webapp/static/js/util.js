function dateFormat(fmt,date)   
{ 
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

//fromTime 为时间戳
function tranTime(fromTime){
	var nowTime=Math.round(new Date()/1000);    
    var tempTime = nowTime - fromTime/1000;    
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
        str = dateFormat('yyyy-MM-dd hh:mm:ss',new Date(fromTime));
    }
    return str;
}

function websocketConnect(){
	$.post('/study/user/message/count',{},function(res){ $("#messageCount").html(res.data);});
	
	var ws = new WebSocket("ws://localhost:8080/study/myHandler")
	
	ws.onopen = function () {
	   console.log("websocket连接成功");
	   ws.send("{}");
	}
	ws.onclose = function () {
	   console.log("websocket连接断开");
	}

	ws.onmessage = function (msg) {	
      var count=$("#messageCount").html()|0;
      $("#messageCount").html(++count);
	  console.log("服务器传来消息："+msg.data);
	}
}