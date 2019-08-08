layui.use(['form', 'table', 'layer'], function () {
    var form = layui.form
        , layer = layui.layer
        , $ = layui.$
        , table = layui.table
    ;
    form.render();

    table.render({
        elem: '#accounts'
        , url: '/getAccountInfo'
        , where: {accountId: $("#accountId").val()}
        , response: {
            statusName: 'statusCode' //规定数据状态的字段名称，默认：code
            , statusCode: 1 //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
        }
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "statusCode": res.statusCode,
                "count": getJsonLength(res.accounts), //解析数据长度
                "data": res.accounts,
                "msg": res.msg
            }

        }
        , cols: [[
            {field: 'accountId', title: '账户ID'}
            , {field: 'accountName', title: '账户名称'}
            , {field: 'balance', title: '余额'}
            , {title: '操作', align: 'center', width: 250, toolbar: '#operation'}
        ]]
        , id: 'accounts'
        , page: true //是否显示分页
        , limit: 10 //每页默认显示的数量
    });


    var active = {
        reload: function () {
            table.reload('accounts', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    accountId: $("#accountId").val()
                }
            });
        }
    };

    $('#search').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $('#reset').on('click', function () {
        $('#accountId').val("");
    });

    $('#add').on('click', function () {
        layer.open({
            type: 2,
            content: '/add',
            area: ['800px', '520px'],
            closeBtn: 2,
            shadeClose: true,
            title: '新增账户'
        });
    });

    table.on('tool(type)', function (obj) {
        var accountId = obj.data.accountId;
        var layEvent = obj.event;
        if (layEvent === 'edit') { //编辑
            layer.open({
                type: 2,
                content: '/edit?accountId=' + accountId,
                area: ['800px', '520px'],
                closeBtn: 2,
                shadeClose: true,
                title: '编辑账户信息'
            });
        } else if (layEvent === 'del') {//删除
            layer.confirm('删除该账户,确定删除?'
                , {icon: 0, title: '删除'}, function (index) {
                    var action = '/deleteAccount?accountId=' + accountId;
                    $.ajax({
                        type: 'GET',
                        url: action,
                        success: function (data) {
                            if (data.statusCode === 1) {
                                layer.msg("删除成功");
                                layer.close(index);
                                $('#search').click();
                            } else {
                                layer.msg("删除失败");
                            }
                        }
                    });
                    layer.close(index);
                });
        }
    });


    function getJsonLength(json) {
        var jsonLength = 0;
        for (var i in json) {
            jsonLength++;
        }
        return jsonLength;
    }
});