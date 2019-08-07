layui.use('form', function() {
    var form = layui.form;
    var $ = layui.$;
    form.render();

    $('#save').on('click',function(){
        var account = {};
        account.accountId = $('#accountId').val();
        if($('#accountName').val() != ""){
            account.accountName = $('#accountName').val();
            if($('#accountPassword').val() != ""){
                account.accountPassword = $('#accountPassword').val();
            }else{
                layer.msg("密码不能为空");
            }
        }else{
            layer.msg("用户名不能为空");
        }

        $.ajax({
            type: 'POST',
            url: '/updateAccount',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(account),
            success: function (data) {
                if(data.statusCode === 1){
                    layer.msg("修改成功");
                    parent.location.reload();
                }else{
                    layer.msg(data.msg);
                }
            }
        });
    });
});