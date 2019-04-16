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
                    operateType: $('#operateType').val(),
                    username:username,
                    beginDateTime:beginDateTime,
                    endDateTime:endDateTime
                }
            });
        }
    };

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
        url: '/user/operateLog/getPageData', //数据接口
        page: true ,//开启分页
        cols: [[ //表头
            {type: 'numbers',title:'序号'},
            {field: 'username', title: '用户名', width:100},
            {field: 'operateTypeName', title: '操作类型', width:120},
            {field: 'content', title: '操作内容', width:800},
            {field: 'ipStr', title: 'ip地址', width:100},
            {field: 'createTime', title: '操作时间', width:160}
        ]]
    });
});