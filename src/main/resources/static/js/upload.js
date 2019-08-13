layui.use('upload', function () {
    var $ = layui.$
        , upload = layui.upload;

    upload.render({
        elem: '.upload' //绑定元素
        , url: '/uploadImage' //上传接口
        , data:{
            id: function(){
                return $('#accountId').val();
            }
        }
        , done: function (res) {
            //上传完毕回调
            layer.msg(res.msg);
        }
        , error: function () {
            //请求异常回调
            console.log("Error");
        }
        , accept: 'images'
        , exts: 'bmp|jpg|png'
        , size: 5120 //KB
    });
});