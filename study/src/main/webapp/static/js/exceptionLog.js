//table
layui.use(['table','jquery','form','laydate'], function(){
    var laydate = layui.laydate;
    var beginDateTime,endDateTime;
    laydate.render({
        elem: '#beginDateTime'
        ,type: 'datetime'
        ,done: function(value, date){
            beginDateTime=value;
        }
    });
    laydate.render({
        elem: '#endDateTime'
        ,type: 'datetime'
        ,done: function(value, date){
            endDateTime=value;
        }
    });


    var table = layui.table;
    var $ = layui.$ , active = {
        reload: function(){
            var username=$('#userNameInput').val()?$('#userNameInput').val():null;
            //执行重载
            table.reload('tab', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    username:username,
                    beginDateTime:beginDateTime,
                    endDateTime:endDateTime
                }
            });
        }
    };

    table.on('tool(logTab)', function(obj) {
        $(obj.tr.selector).find('span').text('已读').css('color','#666');
        var data = obj.data;
        if (obj.event === 'detail') {
            view(data.id);
        }
    });

    $('#search').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //第一个实例
    table.render({
        id: 'tab',
        method: 'post',
        elem: '#logTable',
        height: 500,
        request: {
            pageName: 'pageNo', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        response: {
            statusCode: 200, //成功的状态码，默认：0
            countName: 'total', //数据总数的字段名称，默认：count
            dataName: 'data' //数据列表的字段名称，默认：data
        },
        url: '/user/exceptionLog/getPageData', //数据接口
        page: true ,//开启分页
        cols: [[ //表头
            {type: 'numbers',title:'序号'},
            {field: 'username', title: '用户名', width:100},
            {field: 'className', title: '类名', width:300},
            {field: 'methodName', title: '方法名', width:200},
            {field: 'isView', title: '是否已读', width:100,templet:function(d){
                if(d.isView==0){
                    return '<span style="color: red">未读</span>';
                }
                return '已读';
            }},
            {field: 'ipStr', title: 'ip地址', width:160},
            {field: 'createTime', title: '创建时间', width:160},
            {title:'操作',width:100,templet:function(row){
                return'<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail" >查看</a>';
            }}
        ]]
    });
});

function view(id){
    var index=layer.open({
        type: 2,
        title: '查看',
        content: '/user/exceptionLog/detail?id='+id,
        btn: ['关闭'],
        maxmin: true,
        shade: false,
        offset: ['50px', '10%'],
        area: ['1000px', '600px']
    });
    layer.full(index);
}