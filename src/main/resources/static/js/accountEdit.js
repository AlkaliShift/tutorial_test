layui.use('form', function() {
    var form = layui.form;
    var $ = layui.$;
    form.render();

    $('#save').on('click',function(){
        var account = {};
        account.accountId = $('#accountId').val();
        account.accountName = $('#accountName').val();
        account.accountPassword = $('#accountPassword').val();
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