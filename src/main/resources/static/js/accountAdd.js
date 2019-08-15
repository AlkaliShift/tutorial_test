layui.use('form', function() {
    var form = layui.form;
    var $ = layui.$;
    form.render();

    $('#save').on('click',function(){
        var account = {};
        account.accountName = $('#accountName').val();
        account.accountPassword = $('#accountPassword').val();
        account.captcha = $('#captcha').val();
        $.ajax({
            type: 'POST',
            url: '/createAccount',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(account),
            success: function (data) {
                if(data.statusCode === 1){
                    layer.msg("创建成功");
                    parent.location.reload();
                }else{
                    layer.msg(data.msg);
                    $('#captcha').val("");
                    var timestamp = new Date().getTime();
                    $('#captchaImg').attr('src','/images/captcha?timestamp=' + timestamp);
                }
            }
        });
    });

    $('#captchaImg').on('click',function(){
        var timestamp = new Date().getTime();
        $('#captchaImg').attr('src','/images/captcha?timestamp=' + timestamp);
    });
});