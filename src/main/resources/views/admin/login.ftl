<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>乐商城管理后台</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.validate.min.js"></script>
    <script src="/static/js/layer/layer.js"></script>
    <script src="/static/js/messages_zh.min.js"></script>
    <script src="/static/js/sha1.js"></script>
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control {
            position: relative;
            height: auto;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            padding: 10px;
            font-size: 16px;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body>
<div class="container">

    <form id="loginForm" class="form-signin">
        <h2 class="form-signin-heading">欢迎登录乐商后台</h2>
        <label for="loginName" class="sr-only">loginName</label>
        <input type="text" id="loginName" class="form-control" name="loginName" placeholder="管理员登录名" required minlength:10 autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="密码" required	 minlength:6>
        <label for="inputPassword" class="sr-only">randomCode</label>
        <input type="text" id="randomCode" class="form-control" style="width:150px;position:absolute" placeholder="验证码" required><img id="code" onclick="changeCode()" style="position:relative;left:150px;margin-left:30px;" src="" alt="验证码">
        <div class="checkbox">
            &nbsp;
            <label>
                <input type="checkbox" value="remember-me"> 记住账号
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="subForm()">登录</button>
    </form>
</div>
<script>
    $("#loginForm").validate();
    $(function () {
       changeCode();
    })

    function changeCode() {
        $('#code').attr('src','/randomCaptcha?'+new Date().getTime());
    }
    function subForm() {
        var loginName = $('#loginName').val();
        if(!loginName){
            layer.msg('请输入用户名');
            return false;
        }
        var inputPassword = $('#inputPassword').val();
        if(!inputPassword ||inputPassword.trim().length<6){
            layer.msg('密码至少6位');
            return false;
        }
        var randomCode = $('#randomCode').val();
        if(!randomCode ||randomCode.trim().length!==5){
            layer.msg('验证码错误');
            return false;
        }
        var params = {
            loginName:loginName,
            password:CryptoJS.SHA1(inputPassword).toString(),
            code:randomCode
        }
        $.ajax({
            url:'/admin/sa/api/login',
            type:'POST',
            dataType:'JSON',
            data:params,
            success:function(data){
                console.log(data)
                if(data === 'success'){
                    layer.msg('登录成功');
                    location.href = '/admin/sa/index';
                }
            },
            error:function(e){
                console.log(e)
                layer.msg(e.responseJSON.error || '网络错误')
            }
        })
    }
</script>
</body>
</html>