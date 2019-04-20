layui.config({
	version: "3.0.0",
	base: '/static/res/mods/'
}).extend({
	 fly: 'index'
}).use(['flow','form','layer','jquery'],function(){
    //流加载图片
    var pageSize = 10;  //单页显示图片数量
    var flow = layui.flow,form = layui.form,$=layui.jquery;
    flow.load({
        elem: '#Images', //流加载容器
        done: function(pageNo, next){ //加载下一页
        	$.ajax({
        		url:'/user/getPictures',
        		type:'post',
        		data:{pageSize:pageSize,pageNo:pageNo},
        		dataType:'json',
        		success:function(res){
        			if(res.code=='200'){
        				var data=res.data.list;
        				var total=res.data.total;
                        var imgList = [];
                        var hasData = pageSize*pageNo<total;
                        setTimeout(function(){
                            for(var i=0; i<data.length; i++){
                            	var img='<li>'+
                            			  '<div style="height:242px;overflow:hidden;">'+
                            			    '<img src="'+ data[i].pictureUrl +'">'+
                            			  '</div>'+
                            			  '<div class="operate">'+
                            			    '<div class="check">'+
                            			      '<input type="checkbox" name="belle" lay-filter="choose" lay-skin="primary"  pictureid="'+data[i].id+'">'+
                            			      '<span>图片'+data[i].id+'</span>'+
                            			    '</div>'+
                            			    '<i class="layui-icon img_del">&#xe640;</i>'+
                            			  '</div>'+
                            			'</li>';
                                imgList.push(img);
                            }
                            next(imgList.join(''), hasData);
                            form.render();
                            layui.use(['fly'],function(){})
                        }, 500);
        			}
        		}	
        	})
        }
    });
    
    //删除单张图片
    $("body").on("click",".img_del",function(){
        var _this = $(this);
        layer.confirm('确定删除图片"'+_this.siblings().find("input").attr("pictureid")+'"吗？',{icon:3, title:'提示信息'},function(index){
            var pictureIds=_this.siblings().find("input").attr("pictureid");
        	$.ajax({
            	url:'/user/deletePicture',
            	type:'post',
            	dataType:'json',
            	data:{pictureIds:pictureIds},
            	success:function(res){
            		if(res.code=='200'){
            			_this.parents("li").hide(1000);
                        setTimeout(function(){
                        	_this.parents("li").remove();
                        },950);
                        layer.close(index);
                        layer.msg("删除成功");
            		}else{
            			layer.alert(res.msg);
            		}
            	}
            })
        });
    })
    
    //全选
    form.on('checkbox(selectAll)', function(data){
        var child = $("#Images li input[type='checkbox']");
        child.each(function(index, item){
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });
    
    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)",function(data){
        var child = $(data.elem).parents('#Images').find('li input[type="checkbox"]');
        var childChecked = $(data.elem).parents('#Images').find('li input[type="checkbox"]:checked');
        if(childChecked.length == child.length){
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = true;
        }else{
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = false;
        }
        form.render('checkbox');
    })
    
    //批量删除
    $(".batchDel").click(function(){
        var $checkbox = $('#Images li input[type="checkbox"]:checked');
        if($checkbox.is(":checked")){
            layer.confirm('确定删除选中的图片？',{icon:3, title:'提示信息'},function(index){
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                var arr=[];
                $checkbox.each(function(){
                	arr.push($(this).attr('pictureid'));
                });
                var pictureIds=arr.join(',');
                $.ajax({
                	url:'/user/deletePicture',
                	type:'post',
                	dataType:'json',
                	data:{pictureIds:pictureIds},
                	success:function(res){
                		if(res.code=='200'){
                			setTimeout(function(){
                                //删除数据
                            	$checkbox.each(function(){
                                     $(this).parents("li").hide(1000);
                                     setTimeout(function(){
                                     	$(this).parents("li").remove();
                                     },950);
                                })
                                $('#Images li input[type="checkbox"]').prop("checked",false);
                                form.render();
                                layer.close(index);
                                layer.msg("删除成功");
                            },2000);
                		}else{
                			layer.alert(res.msg);
                		}
                	}
                })  
            })
        }else{
            layer.msg("请选择需要删除的图片");
        }
    })
})
